package com.example.bloggingPlatform.model.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class Post {
    private Long postId;
    private String postTitle;
    private String postContent;
    private String postCategory;
    private List<String> postTags;
    @Builder.Default
    private String postStatus = "ACTIVE";
    private Date postCreatedAt;
    private Date postUpdatedAt;

}
