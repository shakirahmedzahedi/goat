package com.saz.se.goat.admin;

import com.saz.se.goat.discountCoupon.DiscountCouponDTO;
import com.saz.se.goat.model.ErrorModel;
import com.saz.se.goat.model.ResponseWrapper;
import com.saz.se.goat.order.OrderDTO;
import com.saz.se.goat.product.ProductDTO;
import com.saz.se.goat.product.ProductService;
import com.saz.se.goat.requestModel.DiscountCouponRequest;
import com.saz.se.goat.requestModel.OrderUpdateRequest;
import com.saz.se.goat.requestModel.ProductRequest;
import com.saz.se.goat.user.UserDTO;
import com.saz.se.goat.utils.CommonDTO;
import com.saz.se.goat.utils.HeaderProperties;
import com.saz.se.goat.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/whoIsBoss/admin")
public class AdminController
{

    @Autowired
    AdminService adminService;
    @Autowired
    JsonUtils jsonUtils;
    CommonDTO commonDTO = new CommonDTO();

    @CrossOrigin
    @PostMapping("/product/addProduct")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest request, @RequestHeader HttpHeaders header) {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper<ProductDTO> response = new ResponseWrapper<>();
        ProductDTO productDTO = adminService.addProduct(request)
                .map(commonDTO::toProductDTO)
                .orElse(null);

        response.setData(productDTO);

        return jsonUtils.responseAsJsonWithToken(response, headerProperties.getEmail());
    }

    @CrossOrigin
    @PostMapping("/product/updateProduct/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateProductById(@RequestParam long id, @RequestBody ProductRequest request, @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper<ProductDTO> response = new ResponseWrapper<>();
        ProductDTO productDTO = adminService.updateProductById(id, request)
                .map(commonDTO ::toProductDTO)
                .orElse(null);

        response.setData(productDTO);
        return jsonUtils.responseAsJsonWithToken(response, headerProperties.getEmail());
    }

    @CrossOrigin
    @DeleteMapping ("/product/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteProductById(@RequestParam long id, @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper<ProductDTO> response = new ResponseWrapper<>();
        ProductDTO productDTO = adminService.deleteProductById(id)
                .map(commonDTO ::toProductDTO)
                .orElse(null);

        response.setData(productDTO);
        return jsonUtils.responseAsJsonWithToken(response, headerProperties.getEmail());
    }

    /*
    **** This part for USER
     */

    @CrossOrigin
    @PostMapping("/user/addRole")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addRoleByUserId (@RequestParam long id, @RequestParam String role, @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper<Optional<UserDTO>> response = new ResponseWrapper<>();
        Optional<UserDTO> userDTO = adminService.addRoleByUserId(id, role);

        if (userDTO.isPresent())
        {
            response.setData(userDTO);
        }
        else
        {
            response.addError( new ErrorModel("14470","Role is already Assign"));
        }

        return jsonUtils.responseAsJsonWithToken(response, headerProperties.getEmail());
    }

    @CrossOrigin
    @GetMapping("/allUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllUsers (@RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper<List<UserDTO>> response = new ResponseWrapper<>();
        List<UserDTO> userDTOList = adminService.allusers();
        response.setData(userDTOList);

        return jsonUtils.responseAsJsonWithToken(response, headerProperties.getEmail());
    }

    @CrossOrigin
    @PatchMapping ("/order")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateOrderById(@RequestParam long id, @RequestBody OrderUpdateRequest request, @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper<OrderDTO> response = new ResponseWrapper<>();
        OrderDTO orderDTO = adminService.updateOrder(id, request);
        response.setData(orderDTO);
        return jsonUtils.responseAsJsonWithToken(response, headerProperties.getEmail());
    }

    @CrossOrigin
    @DeleteMapping("/order")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteOrderById(@RequestParam long id, @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper<OrderDTO> response = new ResponseWrapper<>();
        OrderDTO orderDTO = adminService.deleteOrderById(id);
        response.setData(orderDTO);
        return jsonUtils.responseAsJsonWithToken(response, headerProperties.getEmail());
    }

    @CrossOrigin
    @GetMapping ("/order/allOrder")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllOrder( @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper<List<OrderDTO>> response = new ResponseWrapper<>();
        List<OrderDTO> orderDTOs = adminService.getAllOrder();
        response.setData(orderDTOs);
        return jsonUtils.responseAsJsonWithToken(response, headerProperties.getEmail());
    }

    @CrossOrigin
    @PostMapping("/discountCoupon/addNew")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addNewCoupon(@RequestBody DiscountCouponRequest request, @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper<Optional<DiscountCouponDTO>> response = new ResponseWrapper<>();
        Optional<DiscountCouponDTO> discountCouponDTO = adminService.addNewCoupon(request);
        if (discountCouponDTO.isPresent())
        {
            response.setData(discountCouponDTO);
        }
        return jsonUtils.responseAsJsonWithToken(response,headerProperties.getEmail());
    }

    @CrossOrigin
    @GetMapping("/discountCoupon/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllCoupons( @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper<List<Optional<DiscountCouponDTO>>> response = new ResponseWrapper<>();
        List<Optional<DiscountCouponDTO>> discountCouponDTOs = adminService.getAllCoupons();
        response.setData(discountCouponDTOs);

        return jsonUtils.responseAsJsonWithToken(response,headerProperties.getEmail());
    }

}
