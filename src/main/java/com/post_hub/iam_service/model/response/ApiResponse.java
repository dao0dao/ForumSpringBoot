package com.post_hub.iam_service.model.response;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.post_hub.iam_service.model.constans.ApiMessages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<P extends Serializable> implements Serializable {

    private String message;
    private P payload;
    private Boolean success;

    public static <P extends Serializable> ApiResponse<P> createSuccessful(P payload) {
        return new ApiResponse<>(StringUtils.EMPTY, payload, true);
    }

    public static <P extends Serializable> ApiResponse<P> tokenCreateUpdated(P payload) {
        return new ApiResponse<>(ApiMessages.TOKEN_CREATED_OR_UPDATED.getMessage(), payload, true);
    }

    public static <P extends Serializable> ApiResponse<P> unauthorized() {
        return new ApiResponse<>(ApiMessages.TOKEN_CREATED_OR_UPDATED.getMessage(), null, true);
    }
}
