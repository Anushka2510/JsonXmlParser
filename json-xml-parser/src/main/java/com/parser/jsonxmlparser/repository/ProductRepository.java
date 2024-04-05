package com.parser.jsonxmlparser.repository;

import com.parser.jsonxmlparser.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ProductRepository extends MongoRepository<Map<String,Object>,String> {
}
