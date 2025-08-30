package com.post_hub.iam_service.model.response.payloads;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationPayload<T> implements Serializable {
    
    private List<T> content;
    private Pagination pagination;
    
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pagination {
        private Long total;
        private Integer limit;
        private Integer page;
        private Integer pages;
    }
}
