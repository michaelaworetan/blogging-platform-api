package com.example.bloggingPlatform.Service;

import com.example.bloggingPlatform.model.entity.Post;
import com.example.bloggingPlatform.model.request.PostCreateRequest;
import com.example.bloggingPlatform.model.request.PostPageRequest;
import com.example.bloggingPlatform.model.response.PostListResponse;
import com.example.bloggingPlatform.model.response.PostResponse;
import com.example.bloggingPlatform.repository.Interface.PostRepository;
import com.example.bloggingPlatform.repository.implementation.PostRepositoryImpl;
import com.example.bloggingPlatform.util.ResponseConstants;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
}
