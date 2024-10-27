package com.saz.se.goat.product;

import com.saz.se.goat.model.ResponseWrapper;
import com.saz.se.goat.requestModel.ProductRequest;
import com.saz.se.goat.utils.CommonDTO;
import com.saz.se.goat.utils.HeaderProperties;
import com.saz.se.goat.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController
{
    @Autowired
    ProductService productService;
    @Autowired
    JsonUtils jsonUtils;
    CommonDTO commonDTO = new CommonDTO();

    @CrossOrigin
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?> getProductById(@RequestParam long id, @RequestHeader HttpHeaders header)
    {
        ResponseWrapper<ProductDTO> response = new ResponseWrapper<>();
        ProductDTO productDTO  =  productService.getProductById(id)
                .map(commonDTO::toProductDTO)
                .orElse(null);

        response.setData(productDTO);
        return jsonUtils.responseAsJson(response);
    }

    @CrossOrigin
    @GetMapping("/allProduct")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?> getAllProduct(@RequestHeader HttpHeaders header)
    {
        HeaderProperties headerProperties = new HeaderProperties(header);
        ResponseWrapper<List<ProductDTO>> response = new ResponseWrapper<>();
        List<ProductDTO> productDTOList = productService.getAllProducts()
                .stream()
                .map(commonDTO ::toProductDTO)
                .toList();

        response.setData(productDTOList);
        return jsonUtils.responseAsJson(response);
    }

}
