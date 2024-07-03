package com.project.shopApp.services;

import com.project.shopApp.dtos.CartItemDTO;
import com.project.shopApp.dtos.OrderDTO;
import com.project.shopApp.exceptions.DataNotFoundException;
import com.project.shopApp.models.*;
import com.project.shopApp.repositories.OrderDetailRepository;
import com.project.shopApp.repositories.OrderRepository;
import com.project.shopApp.repositories.ProductRepository;
import com.project.shopApp.repositories.UserRepository;
import com.project.shopApp.response.OrderResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        //check whether user existing
        User user = userRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(()-> new DataNotFoundException("User "+orderDTO.getUserId()+"Not Found"));
        //map orderDTO->Order
        modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(mapper-> mapper.skip(Order::setId));
        Order order= new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        LocalDate shippingDate= order.getShippingDate()==null? LocalDate.now() :order.getShippingDate();
        if(shippingDate.isBefore(LocalDate.now())){
            throw new DataNotFoundException("Date must be at least now");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItemDTO cartItemDTO : orderDTO.getCartItems()) {
            // Tạo một đối tượng OrderDetail từ CartItemDTO
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);

            // Lấy thông tin sản phẩm từ cartItemDTO
            Long productId = cartItemDTO.getProductId();
            int quantity = cartItemDTO.getQuantity();

            // Tìm thông tin sản phẩm từ cơ sở dữ liệu (hoặc sử dụng cache nếu cần)
            product product = productRepository.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("Product not found with id: " + productId));

            // Đặt thông tin cho OrderDetail
            orderDetail.setProduct(product);
            orderDetail.setNumberOfProducts(quantity);
            // Các trường khác của OrderDetail nếu cần
            orderDetail.setPrice(product.getPrice());

            // Thêm OrderDetail vào danh sách
            orderDetails.add(orderDetail);
            orderDetailRepository.saveAll(orderDetails);
    }return order;}

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        Order newOrder= orderRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Can not find order with id : "+id));
        User user= userRepository.findById(orderDTO.getUserId()).orElseThrow(()-> new DataNotFoundException("Can not find user with id : "+id));
        modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(mapper-> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, newOrder);
        newOrder.setUser(user);
        return  orderRepository.save(newOrder);
    }
    @Transactional
    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if(order != null){
            order.setActive(false);
            orderRepository.save(order);
        }

    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
