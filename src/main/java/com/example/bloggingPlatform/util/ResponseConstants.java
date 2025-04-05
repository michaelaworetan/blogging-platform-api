package com.example.bloggingPlatform.util;

import com.example.bloggingPlatform.model.response.BaseResponse;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ResponseConstants {
    public static final BaseResponse SUCCESS = new BaseResponse("00", "Success");
    public static final BaseResponse ERROR = new BaseResponse("01", "Error processing request");
    public static final BaseResponse NOT_FOUND = new BaseResponse("04", "Resource not found");
    public static final BaseResponse VALIDATION_ERROR = new BaseResponse("10", "Validation failed");

    public static final String ERROR_CODE = "106";

}
