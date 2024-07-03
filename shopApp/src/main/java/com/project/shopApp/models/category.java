package com.project.shopApp.models;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "categories")
@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name" ,nullable = false)
    private String name;

}
