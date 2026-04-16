package com.dauphine.blogger.controllers;

import com.dauphine.blogger.dto.PostRequest;
import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.exceptions.PostNotFoundByIdException;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/posts")
@Tag(name = "Post API", description = "Post endpoints")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @Operation(summary = "Retrieve all posts ordered by creation date, or filter by title/content")
    public ResponseEntity<List<Post>> getAll(
            @RequestParam(required = false) String value) {
        List<Post> posts = value == null || value.isBlank()
                ? postService.getAll()
                : postService.getAllByTitleOrContent(value);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a post by id")
    public ResponseEntity<Post> getById(@PathVariable UUID id) throws PostNotFoundByIdException {
        Post post = postService.getById(id);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    @Operation(summary = "Create a new post")
    public ResponseEntity<Post> create(@Valid @RequestBody PostRequest request) throws CategoryNotFoundByIdException {
        Post post = postService.create(
                request.getTitle(),
                request.getContent(),
                request.getCategoryId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing post")
    public ResponseEntity<Post> update(
            @PathVariable UUID id,
            @Valid @RequestBody PostRequest request) throws PostNotFoundByIdException, CategoryNotFoundByIdException {
        Post post = postService.update(
                id,
                request.getTitle(),
                request.getContent(),
                request.getCategoryId()
        );
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an existing post")
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws PostNotFoundByIdException {
        postService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
