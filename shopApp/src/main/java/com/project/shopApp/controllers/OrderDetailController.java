package com.project.shopApp.controllers;
import com.project.shopApp.components.LocalizationUtils;
import com.project.shopApp.dtos.OrderDTO;
import com.project.shopApp.dtos.OrderDetailDTO;
import com.project.shopApp.exceptions.DataNotFoundException;
import com.project.shopApp.models.OrderDetail;
import com.project.shopApp.response.OrderDetailResponse;
import com.project.shopApp.services.OrderDetailService;
import com.project.shopApp.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    private final LocalizationUtils localizationUtils;
    @PostMapping("")
    public ResponseEntity<?> createOrderDettail(@Valid @RequestBody OrderDetailDTO orderDetailDTO){
      try {

          return ResponseEntity.ok().body(OrderDetailResponse.fromOrder(orderDetailService.createOrderDetail(orderDetailDTO)));
      }
      catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
      }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id) throws DataNotFoundException {

        return  ResponseEntity.ok(OrderDetailResponse.fromOrder(orderDetailService.getOrderDetail(id)));
    }
    //Lay ra danh sach cac order_details cua 1 order nao do
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<List<OrderDetailResponse>> getOrderDetails(@Valid @PathVariable("orderId") Long orderId){
        List<OrderDetail> orderDetails=orderDetailService.findByOrderId(orderId);
        List<OrderDetailResponse> list= orderDetails.stream().map(OrderDetailResponse::fromOrder).toList();
        return ResponseEntity.ok(list);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO,
                                               @PathVariable("id") Long id){
        try {
            OrderDetail orderDetail=orderDetailService.updateOrderDetail(id,orderDetailDTO);
            return ResponseEntity.ok(orderDetail);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderDetail(@Valid @PathVariable("id") Long id){
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.ok().body(localizationUtils
                .getLocalizeMessage(MessageKeys.DELETE_ORDER_DETAIL_SUCCESSFULLY));
    }
}
