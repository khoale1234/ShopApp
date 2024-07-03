package com.project.shopApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetailDTO {
    @Min(value = 1,message = "Order's ID must be greater than 0")
    @JsonProperty("order_id")
    private Long orderId;
    @Min(value = 1,message = "Product's ID must be greater than 0")
    @JsonProperty("product_id")
    private Long productId;
    @Min(value = 0,message = "Price must be greater than or equal to 0")
    private Float price;
    @Min(value = 1,message = "Number of products must be greater than or equal to 1")
    @JsonProperty("number_of_product")
    private int numberOfProduct;
    @Min(value = 0,message = "Total price must be greater than or equal to 0")
    @JsonProperty("total_price")
    private Float totalPrice;
    private String color;
}
