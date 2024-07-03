package com.project.shopApp.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopApp.models.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterResponse {
    @JsonProperty("message")
    protected String message;
    @JsonProperty("user")
    protected User user;
}
