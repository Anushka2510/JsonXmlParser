package com.parser.jsonxmlparser.controller;

import com.parser.jsonxmlparser.model.Product;
import com.parser.jsonxmlparser.repository.ProductRepository;
import com.parser.jsonxmlparser.service.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.Map;

@RestController
public class ProductController {
    @Autowired
    private JsonParser jsonParser;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/products")
    public String createProduct(@RequestBody String jsonData) throws IOException {
        Map<String,Object> product= jsonParser.parseJson(jsonData);
        productRepository.save(product);
        return "Product created successfully";
    }
}
