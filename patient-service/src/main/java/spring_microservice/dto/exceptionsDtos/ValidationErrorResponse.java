package spring_microservice.dto.exceptionsDtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude (JsonInclude.Include.NON_NULL) //--> For ignoring null values
public class ValidationErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Map<String,String> fieldErrors ;
}
