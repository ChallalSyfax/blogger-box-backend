package com.dauphine.blogger.services;

import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.exceptions.PostNotFoundByIdException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;

    public PostServiceImpl(PostRepository postRepository, CategoryService categoryService) {
        this.postRepository = postRepository;
        this.categoryService = categoryService;
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    @Override
    public List<Post> getAllByCategoryId(UUID categoryId) {
        return postRepository.findAllByCategoryIdOrderByCreatedDateDesc(categoryId);
    }

    @Override
    public List<Post> getAllByTitleOrContent(String value) {
        return postRepository.findAllByTitleOrContent(value);
    }

    @Override
    public Post getById(UUID id) throws PostNotFoundByIdException {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundByIdException(id));
    }

    @Override
    public Post create(String title, String content, UUID categoryId) throws CategoryNotFoundByIdException {
        Category category = categoryService.getById(categoryId);
        Post post = new Post(title, content, new Date(), category);
        return postRepository.save(post);
    }

    @Override
    public Post update(UUID id, String title, String content, UUID categoryId) throws PostNotFoundByIdException, CategoryNotFoundByIdException {
        Post post = getById(id);
        post.setTitle(title);
        post.setContent(content);
        if (categoryId != null) {
            Category category = categoryService.getById(categoryId);
            post.setCategory(category);
        }
        return postRepository.save(post);
    }

    @Override
    public void deleteById(UUID id) throws PostNotFoundByIdException {
        getById(id);
        postRepository.deleteById(id);
    }
}
