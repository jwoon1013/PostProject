package com.hannunpalzi.postproject.dto;

import com.hannunpalzi.postproject.entity.Category;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CategoryListResponseDto {
    private Long categoryId;
    private String name;
    private int layer;
    private List<CategoryListResponseDto> childrenCategoryList;

    public CategoryListResponseDto(Category category, List<Category> categoryList) {
        this.categoryId = category.getCategoryId();
        this.name = category.getName();
        this.layer = category.getLayer();

        List<CategoryListResponseDto> categoryListResponseDtoList = new ArrayList<>();
        for (Category c : categoryList) {
            if (category.getLayer() + 1 == c.getLayer() && category.getName().equals(c.getParent())) {
                categoryListResponseDtoList.add(new CategoryListResponseDto(c, categoryList));
            }
        }
        this.childrenCategoryList = categoryListResponseDtoList;
    }
}
