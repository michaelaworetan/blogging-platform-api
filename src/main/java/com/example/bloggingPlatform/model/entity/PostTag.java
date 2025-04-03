package com.example.bloggingPlatform.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostTag {
    private Long postTagId;
    private Long postId;
    private String postTag;

}
