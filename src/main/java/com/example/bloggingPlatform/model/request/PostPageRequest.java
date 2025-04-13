package com.example.bloggingPlatform.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostPageRequest {
    private int postPage;
    private int postSize;
    private String term;
}
