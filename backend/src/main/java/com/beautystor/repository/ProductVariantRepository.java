package com.beautystor.repository;

import com.beautystor.entity.ProductVariant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    @EntityGraph(attributePaths = {"product"})
    Optional<ProductVariant> findWithProductById(long id);

    boolean existsBySku(String sku);

    boolean existsBySkuAndIdNot(String sku, long id);

    long countByStockQuantityLessThanEqual(int stockQuantity);

    @Query("select coalesce(sum(pv.stockQuantity), 0) from ProductVariant pv")
    Long sumStockQuantity();
}
