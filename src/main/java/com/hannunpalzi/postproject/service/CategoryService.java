package com.hannunpalzi.postproject.service;

import com.hannunpalzi.postproject.dto.CategoryRequestDto;
import com.hannunpalzi.postproject.dto.CategoryListResponseDto;
import com.hannunpalzi.postproject.dto.CategoryResponseDto;
import com.hannunpalzi.postproject.entity.Category;
import com.hannunpalzi.postproject.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryListResponseDto> getCategoryList() {
        List<Category> categoryList = categoryRepository.getAllByOrderByNameAsc();
        List<Category> parentCategoryList = categoryRepository.getCategoriesByLayer(0);
        List<CategoryListResponseDto> responseDtoList = parentCategoryList.stream().map(category -> new CategoryListResponseDto(category, categoryList)).collect(Collectors.toList());
        return responseDtoList;
    }

    public List<CategoryResponseDto> getChildrenCategory(Long categoryId) {
        Category category = categoryRepository.findByCategoryId(categoryId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 카테고리입니다.")
        );
        List<Category> childrenCategoryList = categoryRepository.getCategoriesByParent(category.getName());
        return childrenCategoryList.stream().map(childrenCategory -> new CategoryResponseDto(childrenCategory)).collect(Collectors.toList());
    }

    public CategoryResponseDto createCategory(CategoryRequestDto requestDto) {
        Optional<Category> found = categoryRepository.findByName(requestDto.getName());
        if (found.isPresent()) throw new IllegalArgumentException("중복된 카테고리명이 존재합니다.");
        Category category = new Category(requestDto);
        categoryRepository.save(category);
        return new CategoryResponseDto(category);
    }

    public void createChildrenCategory(Long categoryId, CategoryRequestDto requestDto) {
        Category parentCategory = categoryRepository.findByCategoryId(categoryId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 카테고리입니다.")
        );
        Optional<Category> found = categoryRepository.findByName(requestDto.getName());
        if (found.isPresent()) throw new IllegalArgumentException("중복된 카테고리명이 존재합니다.");

        int layer = parentCategory.getLayer() + 1;
        String parent = parentCategory.getName();
        Category category = new Category(requestDto, layer, parent);
        categoryRepository.save(category);
//        return new CategoryResponseDto(category);
    }

    @Transactional
    public void updateCategory(Long categoryId, CategoryRequestDto requestDto) {
        Category category = categoryRepository.findByCategoryId(categoryId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 카테고리입니다.")
        );
        Optional<Category> found = categoryRepository.findByName(requestDto.getName());
        if (found.isPresent()) throw new IllegalArgumentException("중복된 카테고리명이 존재합니다.");

        String parent = category.getName();
        List<Category> childrenCategoryList = categoryRepository.getCategoriesByParent(parent);
        for (Category childrenCategory : childrenCategoryList) {
            childrenCategory.updateChildrenCategoryParent(requestDto.getName());
        }

        category.updateCategoryName(requestDto);
//        return new CategoryResponseDto(category);
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findByCategoryId(categoryId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 카테고리입니다.")
        );
        String parent = category.getName();
        categoryRepository.deleteById(categoryId);
        categoryRepository.deleteByParent(parent);
    }
}
