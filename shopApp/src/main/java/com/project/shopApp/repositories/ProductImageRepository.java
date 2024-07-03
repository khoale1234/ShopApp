package com.project.shopApp.repositories;

import com.project.shopApp.models.productImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<productImage, Long> {
    List<productImage> findByProductId(Long productId);
}
