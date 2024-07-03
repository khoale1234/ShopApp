package com.project.shopApp.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Builder
@Table(name = "roles")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",length = 20)
    private String name;
    public static String ADMIN="ADMIN";
    public static String USER="USER";
}
