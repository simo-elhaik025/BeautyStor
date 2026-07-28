package com.beautystor.service.impl;

import com.beautystor.dto.product.CreateProductRequest;
import com.beautystor.dto.product.ProductDetailsResponse;
import com.beautystor.dto.product.UpdateProductRequest;
import com.beautystor.dto.product.ProductResponse;
import com.beautystor.dto.product.ProductSummaryResponse;
import com.beautystor.entity.Product;
import com.beautystor.mapper.ProductPublicMapper;
import com.beautystor.repository.BrandRepository;
import com.beautystor.repository.CategoryRepository;
import com.beautystor.repository.ProductRepository;
import com.beautystor.specification.ProductSpecifications;
import com.beautystor.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductPublicMapper productPublicMapper;

    @Override
    public ProductResponse create(CreateProductRequest request) {
        validateBrand(request.getBrandId());
        validateCategory(request.getCategoryId());

        Product product = new Product();
        product.setName(request.getName());
        product.setSlug(request.getSlug());
        product.setDescription(request.getDescription());
        product.setBrandId(request.getBrandId());
        product.setCategoryId(request.getCategoryId());
        product.setBasePrice(request.getBasePrice());
        product.setAvailable(request.getAvailable() != null && request.getAvailable());

        Product savedProduct = productRepository.save(product);

        return mapToProductResponse(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductSummaryResponse> getAll(String search, Long categoryId, Long brandId, Boolean available, Pageable pageable) {
        boolean effectiveAvailable = available == null ? true : available;

        Specification<Product> specification = ProductSpecifications.allProducts()
                .and(ProductSpecifications.nameContains(search))
                .and(ProductSpecifications.categoryIdEquals(categoryId))
                .and(ProductSpecifications.brandIdEquals(brandId))
                .and(ProductSpecifications.availableEquals(effectiveAvailable));

        return productRepository.findAll(specification, pageable)
                .map(productPublicMapper::toSummaryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetailsResponse getBySlug(String slug) {
        Product product = productRepository.findBySlugAndIsAvailableTrue(slug)
                .orElseThrow(() -> new IllegalArgumentException("Product with slug '" + slug + "' not found"));

        return productPublicMapper.toDetailsResponse(product);
    }

    @Override
    public List<ProductResponse> getAllForAdmin() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToProductResponse)
                .toList();
    }

    @Override
    public ProductResponse getByIdForAdmin(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found"));

        return mapToProductResponse(product);
    }

    @Override
    public ProductResponse update(long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found"));

        validateBrand(request.getBrandId());
        validateCategory(request.getCategoryId());

        product.setName(request.getName());
        product.setSlug(request.getSlug());
        product.setDescription(request.getDescription());
        product.setBrandId(request.getBrandId());
        product.setCategoryId(request.getCategoryId());
        product.setBasePrice(request.getBasePrice());
        product.setAvailable(request.getAvailable() != null ? request.getAvailable() : false);

        Product updatedProduct = productRepository.save(product);

        return mapToProductResponse(updatedProduct);
    }

    @Override
    public void delete(long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product with ID " + id + " not found");
        }
        productRepository.deleteById(id);
    }

    private void validateBrand(Long brandId) {
        if (brandId != null) {
            boolean brandExists = brandRepository.existsById(brandId);
            if (!brandExists) {
                throw new IllegalArgumentException("Brand with ID " + brandId + " does not exist");
            }
        }
    }

    private void validateCategory(Long categoryId) {
        if (categoryId != null) {
            boolean categoryExists = categoryRepository.existsById(categoryId);
            if (!categoryExists) {
                throw new IllegalArgumentException("Category with ID " + categoryId + " does not exist");
            }
        }
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setSlug(product.getSlug());
        response.setDescription(product.getDescription());
        response.setBrandId(product.getBrandId());
        response.setCategoryId(product.getCategoryId());
        response.setBasePrice(product.getBasePrice());
        response.setAvailable(product.isAvailable());
        return response;
    }
}
