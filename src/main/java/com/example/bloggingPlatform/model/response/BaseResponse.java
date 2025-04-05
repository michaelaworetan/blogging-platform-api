package com.example.bloggingPlatform.model.response;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class BaseResponse {
    private String responseCode;
    private String responseMessage;

    public BaseResponse(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }
}
