package com.post_hub.refreshing_knowledge_of_SpringBoot.utils;

import java.util.ArrayList;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class PageBuilder {

    public static Pageable getPageable(Integer page, Integer limit, ArrayList<String> sortsBy) {
        ArrayList<Order> orders = new ArrayList<>();

        if (sortsBy == null || sortsBy.isEmpty()) {
            orders.add(Sort.Order.asc("id"));
        } else {
            for (var fieldName : sortsBy) {
                Order order;
                if (fieldName.startsWith("-")) {
                    order = Sort.Order.desc(fieldName.substring(1));
                } else {
                    order = Sort.Order.asc(fieldName);
                }
                orders.add(order);
            }
        }
        return PageRequest.of(page, limit, Sort.by(orders));
    }
}
