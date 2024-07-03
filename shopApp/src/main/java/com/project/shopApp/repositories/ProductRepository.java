package com.project.shopApp.repositories;

import com.project.shopApp.models.product;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository  extends JpaRepository<product, Long> {
    boolean existsByName(String name);
    Page <product> findAll(Pageable pageable);
    @Query("SELECT p FROM product p WHERE" +
        "(:categoryId IS NULL OR :categoryId=0 OR p.category.id=:categoryId)" +
         "AND (:keyword IS NULL OR :keyword='' OR p.name LIKE %:keyword% OR p.description LIKE %:keyword%)"
    )
    Page<product> searchProduct(Long categoryId, String keyword, Pageable pageable);
    @Query("SELECT p FROM product p LEFT JOIN p.productImages WHERE p.id =:productId")
    Optional<product> findProductById(@Param("productId") Long productId);
    @Query("select p from product p where p.id in :productIds")
    List<product> findProductsByIds(@Param("productIds") List<Long> productIds);
}
