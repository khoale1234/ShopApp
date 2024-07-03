package com.project.shopApp.services;

import com.project.shopApp.dtos.OrderDetailDTO;
import com.project.shopApp.exceptions.DataNotFoundException;
import com.project.shopApp.models.Order;
import com.project.shopApp.models.OrderDetail;
import com.project.shopApp.models.product;
import com.project.shopApp.repositories.OrderDetailRepository;
import com.project.shopApp.repositories.OrderRepository;
import com.project.shopApp.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    @Override
    @Transactional
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(()-> new DataNotFoundException("Order not found with id :"+orderDetailDTO.getOrderId()));
        product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(()-> new DataNotFoundException("Product not found with id :"+orderDetailDTO.getProductId()));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDTO.getNumberOfProduct())
                .totalPrice(orderDetailDTO.getTotalPrice())
                .price(orderDetailDTO.getPrice())
                .color(orderDetailDTO.getColor()).build();
       return orderDetailRepository.save(orderDetail);


    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
       return orderDetailRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Cannot find Order Detail with id: "+id));
    }
    @Transactional
    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        OrderDetail existingOrderDetail= orderDetailRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Cannot find OrderDetail with id: "+id));
        Order existingOrder= orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find OrderDetail with id: "+orderDetailDTO.getOrderId()));
        product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(()-> new DataNotFoundException("Product not found with id :"+orderDetailDTO.getProductId()));
        existingOrderDetail.setPrice(orderDetailDTO.getPrice());
        existingOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProduct());
        existingOrderDetail.setColor(orderDetailDTO.getColor());
        existingOrderDetail.setTotalPrice(orderDetailDTO.getTotalPrice());
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(product);
        return orderDetailRepository.save(existingOrderDetail);
    }
    @Transactional
    @Override
    public void deleteOrderDetail(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
