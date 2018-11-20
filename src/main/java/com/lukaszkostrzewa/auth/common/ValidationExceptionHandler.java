package com.lukaszkostrzewa.auth.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

/**
 * @author Lukasz Kostrzewa (SG0221165)
 * @since Nov 17, 2018
 */
@ControllerAdvice
@RequiredArgsConstructor
public class ValidationExceptionHandler {

    private static final String VALIDATION_FAILED_KEY = "validationFailed";

    private final MessageSource messageSource;
    private final HttpServletRequest request;

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ErrorResponse.builder()
            .message(messageSource.getMessage(VALIDATION_FAILED_KEY, null, request.getLocale()))
            .details(ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList()))
            .build();
    }
}
