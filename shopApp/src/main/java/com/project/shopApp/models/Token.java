package com.project.shopApp.models;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@Builder
@Table(name = "tokens")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token",length = 255)
    private String token;
    @Column(name = "token_type",length = 50)
    private String tokenType;
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    private boolean revoked;
    private boolean expired;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
