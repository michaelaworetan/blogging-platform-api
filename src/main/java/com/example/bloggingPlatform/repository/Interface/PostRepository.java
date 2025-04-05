package com.example.bloggingPlatform.repository.Interface;

import com.example.bloggingPlatform.model.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Long createPost(Post post);

    void addPostTags(Long postId, List<String> postTags);

    List<Post> getAllPosts(int page, int size);

    Optional<Post> getPostById(Long postId);

    int updatePost(Long postId, Post post);

    int deletePostById(Long postId);

    List<Post> searchPosts(String searchTerm, int page, int size);

    Long countPosts();

    Long countSearchPosts(String searchTerm);

}
