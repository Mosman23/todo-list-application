package net.mhabib.todo.exceptions;

import javax.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        final ApiError apiError = ApiError.builder()
                                          .status(BAD_REQUEST)
                                          .message(ex.getParameterName() + " parameter is missing")
                                          .debugMessage(ex.getLocalizedMessage())
                                          .build();
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ApiError apiError = ApiError.builder().status(BAD_REQUEST).message("Validation error").build();
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        return buildResponseEntity(ApiError.builder()
                                           .status(HttpStatus.BAD_REQUEST)
                                           .message("Malformed JSON request")
                                           .debugMessage(ex.getLocalizedMessage())
                                           .build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        final ApiError apiError = ApiError.builder()
                                          .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                                          .message(builder.substring(0, builder.length() - 2))
                                          .debugMessage(ex.getLocalizedMessage())
                                          .build();
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        ApiError apiError = ApiError.builder().status(BAD_REQUEST).message("Validation error").build();
        apiError.addValidationErrors(ex.getConstraintViolations());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError apiError = ApiError.builder().status(NOT_FOUND).message(ex.getMessage()).build();
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        String error = "Error writing JSON output";
        return buildResponseEntity(ApiError.builder()
                                           .status(INTERNAL_SERVER_ERROR)
                                           .message("Error writing JSON output")
                                           .debugMessage(ex.getLocalizedMessage())
                                           .build());
    }

    @ExceptionHandler(javax.persistence.EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(javax.persistence.EntityNotFoundException ex) {
        return buildResponseEntity(ApiError.builder().status(INTERNAL_SERVER_ERROR).message(ex.getMessage()).build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        final ApiError apiError;
        if (ex.getCause() instanceof ConstraintViolationException) {
            apiError = ApiError.builder().status(CONFLICT).message("Database error").debugMessage(ex.getCause().getLocalizedMessage()).build();
        } else {
            apiError = ApiError.builder().status(INTERNAL_SERVER_ERROR).message("Database error").debugMessage(ex.getLocalizedMessage()).build();
        }
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        ApiError apiError = ApiError.builder()
                                    .status(BAD_REQUEST)
                                    .message(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                                                           ex.getName(),
                                                           ex.getValue(),
                                                           ex.getRequiredType().getSimpleName()))
                                    .debugMessage(ex.getMessage())
                                    .build();
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(final ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
