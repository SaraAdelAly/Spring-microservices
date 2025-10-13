package spring_microservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
