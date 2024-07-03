package com.project.shopApp.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponse {
    @JsonProperty("message")
    protected String message;
    @JsonProperty("token")
    protected String token;
}
