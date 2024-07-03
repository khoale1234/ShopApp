package com.project.shopApp.repositories;

import com.project.shopApp.models.category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<category, Long> {
    
}
