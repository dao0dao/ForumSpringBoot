package com.dao0dao.forumapiservice.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.dao0dao.forumapiservice.model.constans.ApiErrorMessage;
import com.dao0dao.forumapiservice.model.exception.InvalidDataException;

public class PageBuilder {

    @SuppressWarnings("null")
    public static Pageable getPageable(Integer page, Integer limit, List<String> sortsBy, Set<String> allowedFields) {
        ArrayList<Order> orders = new ArrayList<>();

        if (sortsBy == null || sortsBy.isEmpty()) {
            orders.add(Sort.Order.asc("id"));
        } else {
            for (var sortBy : sortsBy) {
                boolean desc = sortBy.startsWith("-");
                String fieldName = desc ? sortBy.substring(1) : sortBy;
                if (!allowedFields.contains(fieldName)) {
                    throw new InvalidDataException(ApiErrorMessage.SORT_FIELD_NOT_ALLOWED.getMessage(fieldName));
                }
                orders.add(desc ? Sort.Order.desc(fieldName) : Sort.Order.asc(fieldName));
            }
        }
        return PageRequest.of(page, limit, Sort.by(orders));
    }
}
