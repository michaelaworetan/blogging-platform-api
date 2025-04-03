package com.example.bloggingPlatform.model.response;

import com.example.bloggingPlatform.model.entity.Post;

import java.util.List;

public class PostListResponse {
    private String responseCode;
    private String responseMessage;
    private List<Post> posts;
    private int currentPage;
    private int totalPages;
    private long totalElements;
}
