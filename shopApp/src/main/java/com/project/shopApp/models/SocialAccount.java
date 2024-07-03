package com.project.shopApp.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Builder
@Table(name = "social_accounts")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "provider",length = 20,nullable = false)
    private String provider;
    @Column(name = "provider_id",length =50,nullable = false)
    private String providerId;
    @Column(name = "email",length = 150)
    private String email;
    @Column(name = "name",length = 100)
    private String name;


}
