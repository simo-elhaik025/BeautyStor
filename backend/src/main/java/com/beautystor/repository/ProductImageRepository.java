package com.beautystor.repository;

import com.beautystor.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    @Query("SELECT CASE WHEN COUNT(pi) > 0 THEN true ELSE false END " +
           "FROM ProductImage pi WHERE pi.productId = ?1 AND pi.isPrimary = true")
    boolean existsPrimaryImageForProduct(long productId);

    @Query("SELECT CASE WHEN COUNT(pi) > 0 THEN true ELSE false END " +
           "FROM ProductImage pi WHERE pi.productId = ?1 AND pi.sortOrder = ?2")
    boolean existsSortOrderForProduct(long productId, int sortOrder);

    @Query("SELECT CASE WHEN COUNT(pi) > 0 THEN true ELSE false END " +
           "FROM ProductImage pi WHERE pi.productId = ?1 AND pi.sortOrder = ?2 AND pi.id != ?3")
    boolean existsSortOrderForProductExcludingId(long productId, int sortOrder, long imageId);
}

