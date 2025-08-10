package com.post_hub.iam_service.model.response;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<P extends Serializable> implements Serializable {

    private String message;
    private P payload;
    private Boolean success;

    public static <P extends Serializable> ApiResponse<P> createSuccessful (P payload) {
        return new ApiResponse<>(StringUtils.EMPTY, payload, true);
    }
}
