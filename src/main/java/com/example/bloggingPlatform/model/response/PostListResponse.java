package com.example.bloggingPlatform.model.response;

import com.example.bloggingPlatform.model.entity.Post;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostListResponse {
    private String responseCode;
    private String responseMessage;
    private List<Post> posts;
    private int currentPage;
    private int totalPages;
    private long totalElements;
}
