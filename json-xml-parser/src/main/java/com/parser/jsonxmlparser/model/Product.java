package com.parser.jsonxmlparser.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Builder
@Getter
@AllArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String productId;
    private String name;
    private Double price;
    private Integer quantity;

}