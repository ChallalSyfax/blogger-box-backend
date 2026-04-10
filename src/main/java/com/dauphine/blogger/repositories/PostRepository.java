package com.dauphine.blogger.repositories;

import com.dauphine.blogger.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findAllByCategoryIdOrderByCreatedDateDesc(UUID categoryId);

    List<Post> findAllByOrderByCreatedDateDesc();

    @Query("SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :value, '%')) OR LOWER(p.content) LIKE LOWER(CONCAT('%', :value, '%'))")
    List<Post> findAllByTitleOrContent(@Param("value") String value);
}
