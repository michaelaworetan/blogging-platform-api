package com.example.bloggingPlatform.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostUpdateRequest {
    @NotBlank(message = "Post title is required")
    private String postTitle;
    @NotBlank(message = "Post content is required")
    private String postContent;
    @NotBlank(message = "Post category is required")
    private String postCategory;
    private List<String> postTags;
}
