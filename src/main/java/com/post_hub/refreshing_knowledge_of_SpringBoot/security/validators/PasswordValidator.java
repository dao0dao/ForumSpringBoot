package com.post_hub.refreshing_knowledge_of_SpringBoot.security.validators;

import com.post_hub.refreshing_knowledge_of_SpringBoot.model.constans.ApiConstans;

public class PasswordValidator {

    public static boolean isValid(String password) {
        if (password.isEmpty()) {
            return false;
        }
        if (password.length() < ApiConstans.PASSWORD_MIN_LENGTH ||
                password.length() > ApiConstans.PASSWORD_MAX_LENGTH ||
                !password.chars().anyMatch(singleChar -> ApiConstans.PASSWORD_SPECIAL_CHARS.indexOf(singleChar) >= 0) ||
                !password.chars().anyMatch(singleChar -> Character.isLowerCase(singleChar)) ||
                !password.chars().anyMatch(singleChar -> Character.isUpperCase(singleChar))) {
            return false;
        }
        return true;
    }

    public static boolean isSamePasswords(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
