package com.beautystor.repository;

import com.beautystor.entity.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @EntityGraph(attributePaths = {"items", "items.variant", "items.variant.product"})
    Optional<Cart> findFirstByUserIdAndStatusOrderByIdDesc(long userId, String status);
}
