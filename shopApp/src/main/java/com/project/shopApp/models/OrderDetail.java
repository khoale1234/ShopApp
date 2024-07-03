
package com.project.shopApp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
@Builder
@Table(name = "order_details")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private product product;
    @Column(name = "price", nullable = false)
    private Float price;
    @Column(name = "number_of_products",nullable = false)
    private int numberOfProducts;
    @Column(name = "total_price",nullable = false)
    private Float totalPrice;
    @Column(name = "color")
    private String color;

}
