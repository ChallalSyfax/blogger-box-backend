package com.dauphine.blogger.controllers;

import com.dauphine.blogger.dto.CategoryRequest;
import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/categories")
@Tag(name = "Category API", description = "Category endpoints")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "Retrieve all categories or filter by name")
    public ResponseEntity<List<Category>> getAll(
            @RequestParam(required = false) String name) {
        List<Category> categories = name == null || name.isBlank()
                ? categoryService.getAll()
                : categoryService.getAllByName(name);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a category by id")
    public ResponseEntity<Category> getById(@PathVariable UUID id) throws CategoryNotFoundByIdException {
        Category category = categoryService.getById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    @Operation(summary = "Create a new category")
    public ResponseEntity<Category> create(@Valid @RequestBody CategoryRequest request) {
        Category category = categoryService.create(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing category")
    public ResponseEntity<Category> update(
            @PathVariable UUID id,
            @Valid @RequestBody CategoryRequest request) throws CategoryNotFoundByIdException {
        Category category = categoryService.update(id, request.getName());
        return ResponseEntity.ok(category);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update the name of an existing category")
    public ResponseEntity<Category> updateName(
            @PathVariable UUID id,
            @Valid @RequestBody CategoryRequest request) throws CategoryNotFoundByIdException {
        Category category = categoryService.update(id, request.getName());
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an existing category")
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws CategoryNotFoundByIdException {
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
