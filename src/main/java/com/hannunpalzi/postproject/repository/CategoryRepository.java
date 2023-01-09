package com.hannunpalzi.postproject.repository;

import com.hannunpalzi.postproject.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> getAllByOrderByNameAsc();
    List<Category> getCategoriesByLayer(int layer);
    List<Category> getCategoriesByParent(String parent);
    Optional<Category> findByName(String name);
    Optional<Category> findByCategoryId(Long categoryId);
    void deleteByParent(String parent);
}
