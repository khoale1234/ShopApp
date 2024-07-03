package com.project.shopApp.services;

import com.project.shopApp.dtos.CategoryDTO;
import com.project.shopApp.models.category;
import com.project.shopApp.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;
    @Transactional
    @Override
    public category createCategory(CategoryDTO categoryDTO) {
        category newCategory= category.builder().name(categoryDTO.getName()).build();
        return categoryRepository.save(newCategory);
    }

    @Override
    public category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<category> getAllCategories() {
        return categoryRepository.findAll();
    }
    @Transactional
    @Override
    public category updateCategory(long categoryId,@Valid CategoryDTO categoryDTO) {
        category existingCategory = categoryRepository.findById(categoryId).orElse(null);
        existingCategory.setName(categoryDTO.getName());
        categoryRepository.save(existingCategory);
        return existingCategory;
    }
    @Transactional
    @Override
    public void deleteCategory(Long id) {
        categoryRepository .deleteById(id);
    }
}
