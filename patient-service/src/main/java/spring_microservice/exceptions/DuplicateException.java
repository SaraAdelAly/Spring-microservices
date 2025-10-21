package spring_microservice.exceptions;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DuplicateException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public DuplicateException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s already exists with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    public DuplicateException(String message) {
        super(message);
    }
}
