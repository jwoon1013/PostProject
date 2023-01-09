package com.hannunpalzi.postproject.controller;

import com.hannunpalzi.postproject.dto.CategoryRequestDto;
import com.hannunpalzi.postproject.dto.CategoryListResponseDto;
import com.hannunpalzi.postproject.dto.CategoryResponseDto;
import com.hannunpalzi.postproject.dto.StatusResponseDto;
import com.hannunpalzi.postproject.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"카테고리 CRUD api"})
public class CategoryController {
    private final CategoryService categoryService;
    @ApiOperation(value = "카테고리 조회", notes = "카테고리 목록을 조회한다.")
    @GetMapping("/categories")
    public List<CategoryListResponseDto> getCategoryList() {
        return categoryService.getCategoryList();
    }

    @GetMapping("/categories/{categoryId}")
    public List<CategoryResponseDto> getChildrenCategories(@PathVariable Long categoryId) {
        return categoryService.getChildrenCategory(categoryId);
    }


    @ApiOperation(value = "카테고리 생성", notes = "카테고리를 생성한다.")
    @PostMapping("/categories")
    public ResponseEntity<StatusResponseDto> createCategory(@RequestBody CategoryRequestDto requestDto) {
        StatusResponseDto responseDto = new StatusResponseDto(HttpStatus.CREATED.value(), "카테고리 등록 완료");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        categoryService.createCategory(requestDto);
        return new ResponseEntity<>(responseDto, headers, HttpStatus.CREATED);
    }


    @ApiOperation(value = "하위 카테고리 생성", notes = "하위 카테고리를 생성한다.")
    @ApiImplicitParam(name = "categoryId", value = "카테고리 id", dataTypeClass = Integer.class,example="1")
    @PostMapping("/categories/{categoryId}")
    public ResponseEntity<StatusResponseDto> createChildrenCategory(@PathVariable Long categoryId, @RequestBody CategoryRequestDto requestDto) {
        StatusResponseDto responseDto = new StatusResponseDto(HttpStatus.CREATED.value(), "카테고리 등록 완료");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        categoryService.createChildrenCategory(categoryId, requestDto);
        return new ResponseEntity<>(responseDto, headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "카테고리 수정", notes = "카테고리를 수정한다.")
    @ApiImplicitParam(name = "categoryId", value = "카테고리 id", dataTypeClass = Integer.class,example="1")
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<StatusResponseDto> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequestDto requestDto) {
        StatusResponseDto responseDto = new StatusResponseDto(HttpStatus.OK.value(), "카테고리 수정 완료");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        categoryService.updateCategory(categoryId, requestDto);
        return new ResponseEntity<>(responseDto, headers, HttpStatus.OK);
    }


    @ApiOperation(value = "카테고리 삭제", notes = "카테고리를 삭제한다.")
    @ApiImplicitParam(name = "categoryId", value = "카테고리 id", dataTypeClass = Integer.class,example="1")
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<StatusResponseDto> deleteCategory(@PathVariable Long categoryId) {
        StatusResponseDto responseDto = new StatusResponseDto(HttpStatus.OK.value(), "카테고리 삭제 완료");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(responseDto, headers, HttpStatus.OK);
    }
}
