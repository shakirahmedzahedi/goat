package com.saz.se.goat.order;

import com.saz.se.goat.model.ResponseWrapper;
import com.saz.se.goat.product.ProductService;
import com.saz.se.goat.requestModel.OrderRequest;
import com.saz.se.goat.requestModel.OrderUpdateRequest;
import com.saz.se.goat.requestModel.ProductRequest;
import com.saz.se.goat.user.UserDTO;
import com.saz.se.goat.utils.HeaderProperties;
import com.saz.se.goat.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController
{
    @Autowired
    OrderService orderService;
    @Autowired
    JsonUtils jsonUtils;

    @CrossOrigin
    @PostMapping("/addOrder")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request, @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper<OrderDTO> response = new ResponseWrapper<>();
        OrderDTO orderDTO = orderService.createOrder(request);
        response.setData(orderDTO);
        return jsonUtils.responseAsJson(response);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?> getOrderById(@RequestParam long id, @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper<OrderDTO> response = new ResponseWrapper<>();
        OrderDTO orderDTO = orderService.getOrderById(id);
        response.setData(orderDTO);
        return jsonUtils.responseAsJson(response);
    }

}
