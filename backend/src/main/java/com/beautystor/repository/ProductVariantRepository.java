package com.beautystor.repository;

import com.beautystor.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    boolean existsBySku(String sku);

    boolean existsBySkuAndIdNot(String sku, long id);
}
