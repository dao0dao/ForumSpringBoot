package com.post_hub.iam_service.advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.post_hub.iam_service.model.constans.ApiConstans;
import com.post_hub.iam_service.model.constans.ApiErrorMessage;
import com.post_hub.iam_service.model.exception.DataExistException;
import com.post_hub.iam_service.model.exception.NoAuthorizationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler
    protected ResponseEntity<String> handleNotFoundException(Exception ex) {
        this.logStackTrace(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DataExistException.class)
    protected ResponseEntity<String> handleDataExistException(DataExistException ex) {
        logStackTrace(ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<String> handleTypeMismatchEntity(MethodArgumentTypeMismatchException ex) {
        logStackTrace(ex);
        var requiredType = ex.getRequiredType();
        String message = (ApiErrorMessage.TYPE_MISMATCH.getMessage(ex.getName(),
                requiredType != null ? requiredType.getSimpleName() : ApiConstans.UNDEFINED));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler()
    protected ResponseEntity<Map<String, List<String>>> handleNotValidException(MethodArgumentNotValidException ex) {
        logStackTrace(ex);
        Map<String, List<String>> errors = new HashMap<>();
        for (var error : ex.getBindingResult().getFieldErrors()) {
            errors.computeIfAbsent(error.getField(), key -> new ArrayList<>())
                    .add(error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(NoAuthorizationException.class)
    protected ResponseEntity<String> handleNoAuthorisationException(NoAuthorizationException ex) {
        logStackTrace(ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
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
