package com.example.bloggingPlatform.Service;

import com.example.bloggingPlatform.model.entity.Post;
import com.example.bloggingPlatform.model.request.PostCreateRequest;
import com.example.bloggingPlatform.model.response.PostResponse;
import com.example.bloggingPlatform.repository.Interface.PostRepository;
import com.example.bloggingPlatform.repository.implementation.PostRepositoryImpl;
import com.example.bloggingPlatform.util.ResponseConstants;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class PostService {

    private final PostRepositoryImpl postRepository;

    private static final Gson gson = new Gson();

    public PostService(PostRepositoryImpl postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public PostResponse createPost(PostCreateRequest request) {
        Date now = new Date();

        // Convert request to Post entity using Gson
        Post post = gson.fromJson(gson.toJson(request), Post.class);

        // Sets additional fields not included in the request
        post.setPostStatus("ACTIVE");
        post.setPostCreatedAt(now);
        post.setPostUpdatedAt(now);

        // Persist the post and get generated ID
        Long postId = postRepository.createPost(post);

        // Adds tags for the post
        postRepository.addPostTags(postId, request.getPostTags());

        // Sets the generated ID on the post object
        post.setPostId(postId);

        // Creates a response object
        return PostResponse.builder()
                .responseCode(ResponseConstants.SUCCESS.getResponseCode())
                .responseMessage(ResponseConstants.SUCCESS.getResponseMessage())
                .post(post)
                .build();

    }
}
