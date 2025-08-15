package com.post_hub.iam_service.advice;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.post_hub.iam_service.model.constans.ApiConstans;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CommonControllerAdvice {
 
    @ExceptionHandler
    @ResponseBody
    protected ResponseEntity<String> handleNotFoundException(Exception ex) {
        this.logStackTrace(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    private void logStackTrace(Exception ex) {
        StringBuilder stackTrace = new StringBuilder();

        stackTrace.append(ApiConstans.ANSI_RED);
        stackTrace.append(ex.getMessage()).append(ApiConstans.BREAK_LINE);

        if (Objects.nonNull(ex.getCause())) {
            stackTrace.append(ex.getCause().getMessage()).append(ApiConstans.BREAK_LINE);
        }

        Arrays.stream(ex.getStackTrace())
                .filter(st -> st.getClassName().startsWith(ApiConstans.TIME_ZONE_PACKA_NAME))
                .forEach(st -> stackTrace
                        .append(st.getClassName())
                        .append(".")
                        .append(st.getMethodName())
                        .append(" (")
                        .append(st.getLineNumber())
                        .append(")"));

        log.error(stackTrace.append(ApiConstans.ANSI_WHITE).toString());

    }
}
