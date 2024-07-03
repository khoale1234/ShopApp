package com.project.shopApp.response;

import com.project.shopApp.models.Order;
import com.project.shopApp.models.OrderDetail;
import com.project.shopApp.models.product;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetailResponse {
    private Long id;
    private Long orderId;
    private Long productId;
    private Float price;
    private int numberOfProducts;
    private Float totalPrice;
    private String color;
    public static OrderDetailResponse fromOrder(OrderDetail orderDetail) {
        OrderDetailResponse orderDetailResponse = OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .price(orderDetail.getPrice())
                .numberOfProducts(orderDetail.getNumberOfProducts())
                .totalPrice(orderDetail.getTotalPrice())
                .color(orderDetail.getColor()).build();
        return orderDetailResponse;

    }
}
