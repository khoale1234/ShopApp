package com.project.shopApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopApp.models.product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductImageDTO {
    @Min(value = 1,message = "product's id must be >0")
    @JsonProperty("product_id")
    private Long productId;
    @Size(min = 5,max = 200,message = "image'name")
    @JsonProperty("image_url")
    private String imageUrl;


}
