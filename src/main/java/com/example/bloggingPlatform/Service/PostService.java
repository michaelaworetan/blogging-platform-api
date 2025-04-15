package com.example.bloggingPlatform.Service;

import com.example.bloggingPlatform.model.entity.Post;
import com.example.bloggingPlatform.model.request.PostCreateRequest;
import com.example.bloggingPlatform.model.request.PostPageRequest;
import com.example.bloggingPlatform.model.request.PostUpdateRequest;
import com.example.bloggingPlatform.model.response.PostListResponse;
import com.example.bloggingPlatform.model.response.PostResponse;
import com.example.bloggingPlatform.repository.implementation.PostRepositoryImpl;
import com.example.bloggingPlatform.util.ResponseConstants;
import com.google.gson.Gson;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

        Post post = gson.fromJson(gson.toJson(request), Post.class);

        post.setPostStatus("ACTIVE");
        post.setPostCreatedAt(now);
        post.setPostUpdatedAt(now);

        Long postId = postRepository.createPost(post);

        postRepository.addPostTags(postId, request.getPostTags());

        post.setPostId(postId);

        return PostResponse.builder()
                .responseCode(ResponseConstants.SUCCESS.getResponseCode())
                .responseMessage(ResponseConstants.SUCCESS.getResponseMessage())
                .post(post)
                .build();
    }

    public PostListResponse getAllPosts(PostPageRequest pageRequest) {
        int page = pageRequest.getPostPage();
        int size = pageRequest.getPostSize();

        String searchTerm = pageRequest.getTerm();

        List<Post> posts;
        long totalPosts;

        if (searchTerm != null && !searchTerm.isEmpty()) {
            posts = postRepository.searchPosts(searchTerm, page, size);
            totalPosts = postRepository.countSearchPosts(searchTerm);
        } else {
            posts = postRepository.getAllPosts(page, size);
            totalPosts = postRepository.countPosts();
        }

        int totalPages = (int) Math.ceil((double) totalPosts / size);

        return PostListResponse.builder()
                .responseCode(ResponseConstants.SUCCESS.getResponseCode())
                .responseMessage(ResponseConstants.SUCCESS.getResponseMessage())
                .posts(posts)
                .currentPage(page)
                .totalPages(totalPages)
                .totalElements(totalPosts)
                .build();
    }

    public PostResponse getPostById(Long postId) {
        Optional<Post> post = postRepository.getPostById(postId);

        if (post.isPresent()) {
            return PostResponse.builder()
                    .responseCode(ResponseConstants.SUCCESS.getResponseCode())
                    .responseMessage(ResponseConstants.SUCCESS.getResponseMessage())
                    .post(post.get())
                    .build();
        } else {
            return PostResponse.builder()
                    .responseCode(ResponseConstants.NOT_FOUND.getResponseCode())
                    .responseMessage(ResponseConstants.NOT_FOUND.getResponseMessage())
                    .build();
        }
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostUpdateRequest request) {
        Optional<Post> optionalPost = postRepository.getPostById(postId);

        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();

            Post updatedPost = gson.fromJson(gson.toJson(request), Post.class);

            updatedPost.setPostId(postId);
            updatedPost.setPostStatus(existingPost.getPostStatus());
            updatedPost.setPostCreatedAt(existingPost.getPostCreatedAt());
            updatedPost.setPostUpdatedAt(new Date());

            Long result = postRepository.updatePost(postId, updatedPost);

            if (result > 0) {
                postRepository.updatePostTags(postId, request.getPostTags());

                return PostResponse.builder()
                        .responseCode(ResponseConstants.SUCCESS.getResponseCode())
                        .responseMessage(ResponseConstants.SUCCESS.getResponseMessage())
                        .post(updatedPost)
                        .build();
            } else {
                return PostResponse.builder()
                        .responseCode(ResponseConstants.ERROR.getResponseCode())
                        .responseMessage("Failed to update post")
                        .build();
            }
        } else {
            return PostResponse.builder()
                    .responseCode(ResponseConstants.NOT_FOUND.getResponseCode())
                    .responseMessage(ResponseConstants.NOT_FOUND.getResponseMessage())
                    .build();
        }

    }
}
