package com.post_hub.refreshing_knowledge_of_SpringBoot.model.constans;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiLogMessage {
    NAME_OF_CURRENT_METHOD("Current method is: {}");

    private final String value;

}
