package com.parser.jsonxmlparser.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parser.jsonxmlparser.configuration.MetaDataConfig;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JsonParser {
    private final ObjectMapper objectMapper;
    private final MetaDataConfig metadataConfig;


    public JsonParser(ObjectMapper objectMapper, MetaDataConfig metadataConfig) {
        this.objectMapper = objectMapper;
        this.metadataConfig = metadataConfig;
    }

    public Map<String, Object> parseJson(String jsonString) throws IOException {
        Map<String, Object> jsonMap = objectMapper.readValue(jsonString, Map.class);
        Map<String, Object> parsedData = new HashMap<>();
        List<Map<String,Object>> fieldNamesList = metadataConfig.getFieldConfig();
        for(Map<String,Object> fieldNames:fieldNamesList){
//            Map<String,Object> fieldName= objectMapper.readValue(fieldNames.toString(),new TypeReference<Map<Object,Object>>() {});
//            System.out.println(fieldName);
        }

//        for (String fieldName : fieldNames) {
//            if (jsonMap.containsKey(fieldName)) {
//                parsedData.put(fieldName, jsonMap.get(fieldName));
//            }
//        }

       return parsedData;
    }
}
