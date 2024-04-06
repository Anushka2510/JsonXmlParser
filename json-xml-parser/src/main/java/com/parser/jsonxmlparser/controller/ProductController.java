package com.parser.jsonxmlparser.controller;

import com.parser.jsonxmlparser.repository.ProductRepository;
import com.parser.jsonxmlparser.service.JsonParser;
import com.parser.jsonxmlparser.service.JsonToCSVGenerator;
import com.parser.jsonxmlparser.service.JsonToDDLConverter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j
public class ProductController {
    @Autowired
    private JsonParser jsonParser;

    @Autowired
    private JsonToDDLConverter jsonToDDLConverter;
    @Autowired
    private JsonToCSVGenerator jsonToCSVGenerator;


    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/jsonToCsv")
    public String createProduct(@RequestBody String json)  {
        JSONObject jsonData= new JSONObject(json);
        Map<String,Object> product= jsonParser.parseJson(jsonData);
        productRepository.save(product);
        return "Product created successfully";
    }
    @PostMapping("/ddlconverter")
    public String createDDL(@RequestBody String jsonData) throws IOException {
        Map<String,Object> product= jsonToDDLConverter.convertToDDL(jsonData);
//        productRepository.save(product);
        return "Product created successfully";
    }
    @PostMapping("/csvgenerator")
    public String generateCsvGenerator(@RequestBody String json) throws IOException {
       jsonToCSVGenerator.generateCsv(json,"D:\\Users\\Anushka\\output.csv");
//        productRepository.save(product);
        return "csv generated successfully in downloads";
    }

}
