package com.project.shopApp.services;

import com.project.shopApp.dtos.CategoryDTO;
import com.project.shopApp.models.category;

import java.util.List;

public interface ICategoryService {
    category createCategory(CategoryDTO category);
    category getCategoryById(Long id);
    List<category> getAllCategories();
    category updateCategory(long categoryId, CategoryDTO category);
    void deleteCategory(Long  id);
}
