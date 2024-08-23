package com.tenten.damoa.common.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_LOG_MESSAGE = "[ERROR] {} : {}";
    private static final String WARN_LOG_MESSAGE = "[WARN] {} : {}";


    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse<Void>> handleBusinessException(
            final BusinessException e) {
        log.warn(WARN_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage());
        e.printStackTrace();

        ErrorCode errorCode = e.getErrorCode();
        return ErrorResponse.toResponseEntity(errorCode);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse<Void>> handleException(final Exception e) {
        log.error(ERROR_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage());
        e.printStackTrace();

        return ErrorResponse.toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse<List<String>>> handleBindException(
            final BindException e) {
        log.warn(ERROR_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage());

        List<String> details = getBindingResultErrorMessages(e.getBindingResult());

        return ErrorResponse.toResponseEntity(ErrorCode.BAD_REQUEST, details);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse<List<String>>> handleBindException(
            final MethodArgumentNotValidException e) {
        log.warn(ERROR_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage());

        List<String> details = getBindingResultErrorMessages(e.getBindingResult());

        return ErrorResponse.toResponseEntity(ErrorCode.BAD_REQUEST, details);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse<List<String>>> handleConstraintViolationException(
            final ConstraintViolationException e) {
        log.warn(ERROR_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage());

        List<String> details = getConstraintViolationErrorMessages(e.getConstraintViolations());

        return ErrorResponse.toResponseEntity(ErrorCode.BAD_REQUEST, details);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse<Void>> handleMethodArgumentTypeMismatchException(
            final MethodArgumentTypeMismatchException e) {
        log.warn(ERROR_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage());

        return ErrorResponse.toResponseEntity(ErrorCode.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            InvalidFormatException.class,
            ServletRequestBindingException.class,
            MissingServletRequestParameterException.class,
            HandlerMethodValidationException.class
    })
    protected ResponseEntity<ErrorResponse<Void>> handleInvalidFormatException(final Exception e) {
        log.warn(ERROR_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage());

        return ErrorResponse.toResponseEntity(ErrorCode.INVALID_FORMAT_EXCEPTION);
    }

    @ExceptionHandler({
            ServletRequestBindingException.class,
            MissingServletRequestParameterException.class,
    })
    protected ResponseEntity<ErrorResponse<Void>> handleMissingParameterException(final Exception e) {
        log.warn(ERROR_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage());

        return ErrorResponse.toResponseEntity(ErrorCode.MISSING_PARAMETER_EXCEPTION);
    }

    private List<String> getBindingResultErrorMessages(BindingResult bindingResult) {
        return bindingResult
                .getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }

    private List<String> getConstraintViolationErrorMessages(Set<ConstraintViolation<?>> constraintViolations) {
        return constraintViolations.stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .toList();
    }
}