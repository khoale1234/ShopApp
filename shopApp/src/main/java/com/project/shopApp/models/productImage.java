package com.project.shopApp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "product_images")
public class productImage {
    public static final int MAXIMUM_IMAGES_PER_PRODUCT=5;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonBackReference
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private product product;
    @JsonProperty("image_url")
    @Column(name="image_url")
    private String imageUrl;
}
