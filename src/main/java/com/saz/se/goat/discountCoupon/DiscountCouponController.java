package com.saz.se.goat.discountCoupon;

import com.saz.se.goat.model.ErrorModel;
import com.saz.se.goat.model.ResponseWrapper;
import com.saz.se.goat.requestModel.DiscountCouponRequest;
import com.saz.se.goat.utils.HeaderProperties;
import com.saz.se.goat.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/discountCoupon")
public class DiscountCouponController
{
    @Autowired
    DiscountCouponRepository discountCouponRepository;
    @Autowired
    DiscountCouponService discountCouponService;
    @Autowired
    JsonUtils jsonUtils;



    @CrossOrigin
    @GetMapping("/findCoupon/")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?> getCouponByNumber(@RequestParam String couponNumber, @RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper<DiscountCouponDTO> response = new ResponseWrapper<>();
        DiscountCouponDTO discountCouponDTO = discountCouponService.getCoupon(couponNumber);
        if ( discountCouponDTO == null)
        {
            response.getErrors().add(new ErrorModel("6789","Invalid Coupon"));
        }
        else
        {
            response.setData(discountCouponDTO);
        }

        return jsonUtils.responseAsJsonWithToken(response, headerProperties.getEmail());
    }
}
