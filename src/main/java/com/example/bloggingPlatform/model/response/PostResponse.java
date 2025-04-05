package com.example.bloggingPlatform.model.response;

import com.example.bloggingPlatform.model.entity.Post;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponse {
    private String responseCode;
    private String responseMessage;
    private Post post;
}
