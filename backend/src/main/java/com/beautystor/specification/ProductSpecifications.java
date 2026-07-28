package com.beautystor.specification;

import com.beautystor.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.Locale;

public final class ProductSpecifications {

    private ProductSpecifications() {
    }

    public static Specification<Product> allProducts() {
        return (root, query, cb) -> cb.conjunction();
    }

    public static Specification<Product> nameContains(String search) {
        if (!StringUtils.hasText(search)) {
            return allProducts();
        }

        String pattern = "%" + search.trim().toLowerCase(Locale.ROOT) + "%";
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), pattern);
    }

    public static Specification<Product> categoryIdEquals(Long categoryId) {
        if (categoryId == null) {
            return allProducts();
        }

        return (root, query, cb) -> cb.equal(root.get("categoryId"), categoryId);
    }

    public static Specification<Product> brandIdEquals(Long brandId) {
        if (brandId == null) {
            return allProducts();
        }

        return (root, query, cb) -> cb.equal(root.get("brandId"), brandId);
    }

    public static Specification<Product> availableEquals(Boolean available) {
        if (available == null) {
            return allProducts();
        }

        return (root, query, cb) -> cb.equal(root.get("isAvailable"), available);
    }
}
