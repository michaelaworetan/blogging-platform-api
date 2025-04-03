package com.example.bloggingPlatform.model.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class postUpdateRequest {
    @NotBlank(message = "Post title is required")
    private String postTitle;
    @NotBlank(message = "Post content is required")
    private String postContent;
    @NotBlank(message = "Post category is required")
    private String postCategory;
    private List<String> postTags;
}
