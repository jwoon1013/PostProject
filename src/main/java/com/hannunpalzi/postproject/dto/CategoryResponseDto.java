package com.hannunpalzi.postproject.dto;

import com.hannunpalzi.postproject.entity.Category;
import lombok.Getter;

@Getter
public class CategoryResponseDto {
    private Long categoryId;
    private String name;
    private int layer;

    public CategoryResponseDto(Category category) {
        this.categoryId = category.getCategoryId();
        this.name = category.getName();
        this.layer = category.getLayer();
    }
}
