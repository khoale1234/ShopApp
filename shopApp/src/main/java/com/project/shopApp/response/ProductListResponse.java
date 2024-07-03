package com.project.shopApp.response;

import com.project.shopApp.models.product;
import lombok.*;

import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductListResponse {
    private List<ProductResponse> products;
    private int totalPages;
}
