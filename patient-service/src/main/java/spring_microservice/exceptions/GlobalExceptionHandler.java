package spring_microservice.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spring_microservice.dto.exceptionsDtos.ErrorResponse;
import spring_microservice.dto.exceptionsDtos.ValidationErrorResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest httpServletRequest) {
        log.warn("Validation failed: {}", ex.getMessage());
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(
                error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
        return ValidationErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation failed")
                .message("Input validation failed")
                .fieldErrors(fieldErrors)
                .path(httpServletRequest.getRequestURI())
                .build();
    }

    //ErrorResponse
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(ResourceNotFoundException resourceNotFoundException,
                                                 HttpServletRequest httpServletRequest) {
        log.warn("Resource not found: {}", resourceNotFoundException.getMessage());
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(resourceNotFoundException.getMessage())
                .path(httpServletRequest.getRequestURI())
                .build();
    }
    //ResponseEntity

//    @ExceptionHandler(BusinessException.class)
//    public ResponseEntity<ErrorResponse> handleBusiness (BusinessException ex,
//                                                         HttpServletRequest request){
//        HttpStatus httpStatus;
//        if(ex.getMessage().contains("not found")) {
//            httpStatus = HttpStatus.NOT_FOUND;
//        }else if (ex.getMessage().contains("duplicate")){
//            httpStatus = HttpStatus.CONFLICT;
//        }else {
//            httpStatus = HttpStatus.BAD_REQUEST;
//        }
//
//        ErrorResponse errorResponse = ErrorResponse.builder()
//                .timestamp(LocalDateTime.now())
//                .status(httpStatus.value())
//                .error(httpStatus.getReasonPhrase())
//                .message(ex.getMessage())
//                .path(request.getRequestURI())
//                .build();
//
//        return ResponseEntity
//                .status(httpStatus)
//                .header( "X-Error-Code",String.valueOf(httpStatus.value()))
//                .body(errorResponse);
//
//    }
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedResource(DuplicateException duplicateException, HttpServletRequest httpServletRequest) {
        log.warn("Duplicate resource: {}", duplicateException.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("Conflict")
                .message(duplicateException.getMessage())
                .path(httpServletRequest.getRequestURI())
                .build();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .header("X-Error-Code", "DUPLICATE_RESOURCE")
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unexpected error at {}", request.getRequestURI(), ex);

        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("An unexpected error occurred")
                .path(request.getRequestURI())
                .build();
    }
}
