package com.parser.jsonxmlparser.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class MetaDataConfig {

    @Value("file:src/main/java/com/parser/jsonxmlparser/json/metadata.json")
    private Resource jsonMetadata;

    private final ObjectMapper objectMapper;

    public MetaDataConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Map<String,Object>> getFieldConfig() throws IOException {
        Map<String, List<Map<String,Object>>> metadata =objectMapper.readValue(jsonMetadata.getInputStream(), new TypeReference<Map<String, List<Map<String,Object>>>>() {});
        return metadata.get("fields");
    }
}
