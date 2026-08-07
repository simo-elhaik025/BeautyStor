package com.beautystor.service.impl;

import com.beautystor.dto.order.CreateOrderRequest;
import com.beautystor.dto.order.OrderResponse;
import com.beautystor.dto.order.OrderSummaryResponse;
import com.beautystor.entity.Cart;
import com.beautystor.entity.CartItem;
import com.beautystor.entity.Order;
import com.beautystor.entity.OrderItem;
import com.beautystor.entity.ProductVariant;
import com.beautystor.enm.OrderStatus;
import com.beautystor.mapper.OrderMapper;
import com.beautystor.repository.CartItemRepository;
import com.beautystor.repository.CartRepository;
import com.beautystor.repository.OrderItemRepository;
import com.beautystor.repository.OrderRepository;
import com.beautystor.repository.ProductImageRepository;
import com.beautystor.repository.ProductVariantRepository;
import com.beautystor.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final String ACTIVE_CART_STATUS = "ACTIVE";

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductImageRepository productImageRepository;
    private final OrderMapper orderMapper;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public OrderResponse createOrder(long userId, CreateOrderRequest request) {
        Cart cart = cartRepository.findFirstByUserIdAndStatusOrderByIdDesc(userId, ACTIVE_CART_STATUS)
                .orElseThrow(() -> new IllegalArgumentException("Cart is empty"));

        List<CartItem> cartItems = cart.getItems() == null ? new ArrayList<>() : cart.getItems();
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        List<ValidatedCartLine> validatedLines = validateCartLines(cartItems);
        BigDecimal totalPrice = computeTotalPrice(validatedLines);

        Order order = new Order();
        order.setOrderNumber(generateOrderNumber(userId));
        order.setUserId(userId);
        order.setStatus(OrderStatus.PENDING);
        order.setShippingAddressSnapshot(serializeShippingAddressSnapshot(request));
        order.setTotalAmount(totalPrice);
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = buildOrderItems(savedOrder.getId(), validatedLines);
        orderItemRepository.saveAll(orderItems);

        decrementStock(validatedLines);
        cartItemRepository.deleteByCartId(cart.getId());

        Order createdOrder = orderRepository.findByIdAndUserId(savedOrder.getId(), userId)
                .orElseThrow(() -> new IllegalArgumentException("Order with ID " + savedOrder.getId() + " not found"));

        return orderMapper.toResponse(createdOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderSummaryResponse> getAll(long userId, Pageable pageable) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(orderMapper::toSummaryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getById(long userId, long orderId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + orderId + " not found"));

        return orderMapper.toResponse(order);
    }

    private List<ValidatedCartLine> validateCartLines(List<CartItem> cartItems) {
        List<ValidatedCartLine> lines = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            ProductVariant variant = productVariantRepository.findWithProductById(cartItem.getProductVariantId())
                    .orElseThrow(() -> new EntityNotFoundException("Product variant with ID " + cartItem.getProductVariantId() + " not found"));

            if (variant.getStockQuantity() < cartItem.getQuantity()) {
                throw new IllegalArgumentException(
                        "Insufficient stock for product variant ID " + variant.getId() +
                                ". Requested: " + cartItem.getQuantity() +
                                ", available: " + variant.getStockQuantity());
            }

            lines.add(new ValidatedCartLine(cartItem, variant));
        }

        return lines;
    }

    private List<OrderItem> buildOrderItems(long orderId, List<ValidatedCartLine> validatedLines) {
        List<OrderItem> items = new ArrayList<>();

        for (ValidatedCartLine line : validatedLines) {
            BigDecimal priceAtPurchase = line.cartItem().getSnapshotPrice() != null
                    ? line.cartItem().getSnapshotPrice()
                    : line.variant().getPrice();

            ProductVariant variant = line.variant();
            String imageUrl = productImageRepository.findPrimaryByProductId(variant.getProductId())
                    .map(image -> image.getUrl())
                    .orElse(null);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setProductVariantId(variant.getId());
            orderItem.setProductNameSnapshot(variant.getProduct().getName());
            orderItem.setProductVariantSku(variant.getSku());
            orderItem.setVariantDisplayNameSnapshot(variant.getDisplayName());
            orderItem.setProductDescriptionSnapshot(variant.getProduct().getDescription());
            orderItem.setProductImageUrlSnapshot(imageUrl);
            orderItem.setQuantity(line.cartItem().getQuantity());
            orderItem.setPriceAtPurchase(priceAtPurchase);
            items.add(orderItem);
        }

        return items;
    }

    private void decrementStock(List<ValidatedCartLine> validatedLines) {
        for (ValidatedCartLine line : validatedLines) {
            ProductVariant variant = line.variant();
            variant.setStockQuantity(variant.getStockQuantity() - line.cartItem().getQuantity());
            productVariantRepository.save(variant);
        }
    }

    private BigDecimal computeTotalPrice(List<ValidatedCartLine> lines) {
        return lines.stream()
                .map(line -> {
                    BigDecimal unitPrice = line.cartItem().getSnapshotPrice() != null
                            ? line.cartItem().getSnapshotPrice()
                            : line.variant().getPrice();
                    return unitPrice.multiply(BigDecimal.valueOf(line.cartItem().getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String serializeShippingAddressSnapshot(CreateOrderRequest request) {
        try {
            return objectMapper.writeValueAsString(new com.beautystor.dto.order.ShippingAddressSnapshot(
                    request.getFullAddress(),
                    request.getCity(),
                    request.getPhone()));
        } catch (JacksonException ex) {
            throw new IllegalStateException("Unable to serialize shipping address snapshot", ex);
        }
    }

    private String generateOrderNumber(long userId) {
        return "ORD-" + userId + "-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }

    private record ValidatedCartLine(CartItem cartItem, ProductVariant variant) {
    }
}
