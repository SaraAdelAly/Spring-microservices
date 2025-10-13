package spring_microservice.dto.exceptionsDtos;

import lombok.*;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
//@NoArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}
