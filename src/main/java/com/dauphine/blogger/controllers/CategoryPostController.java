package com.dauphine.blogger.controllers;

import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.services.CategoryService;
import com.dauphine.blogger.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/categories")
@Tag(name = "Category API", description = "Category sub-resource endpoints")
public class CategoryPostController {

    private final PostService postService;
    private final CategoryService categoryService;

    public CategoryPostController(PostService postService, CategoryService categoryService) {
        this.postService = postService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}/posts")
    @Operation(summary = "Retrieve all posts of a category")
    public ResponseEntity<List<Post>> getPostsByCategoryId(@PathVariable UUID id) throws CategoryNotFoundByIdException {
        categoryService.getById(id);
        List<Post> posts = postService.getAllByCategoryId(id);
        return ResponseEntity.ok(posts);
    }
}
