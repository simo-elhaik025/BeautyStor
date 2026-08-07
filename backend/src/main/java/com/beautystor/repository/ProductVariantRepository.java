package com.beautystor.repository;

import com.beautystor.entity.ProductVariant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    @EntityGraph(attributePaths = {"product"})
    Optional<ProductVariant> findWithProductById(long id);

    boolean existsBySku(String sku);

    boolean existsBySkuAndIdNot(String sku, long id);
}
