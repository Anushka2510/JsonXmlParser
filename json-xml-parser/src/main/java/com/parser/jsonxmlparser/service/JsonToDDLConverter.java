package com.parser.jsonxmlparser.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.parser.jsonxmlparser.configuration.MetaDataConfig;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JsonToDDLConverter {

    private final ObjectMapper objectMapper;
    private final MetaDataConfig metadataConfig;

    public JsonToDDLConverter(ObjectMapper objectMapper, MetaDataConfig metadataConfig) {
        this.objectMapper = objectMapper;
        this.metadataConfig = metadataConfig;
    }

    public Map<String, Object> convertToDDL(String json) throws JsonProcessingException {
        // Parse JSON into Jackson object
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonData = (ObjectNode) mapper.readTree(json);

        // Get table name and fields
        String tableName = "i1";
        ObjectNode fields = (ObjectNode) jsonData.get("fields");

        // Generate DDL statements
        StringBuilder ddlBuilder = new StringBuilder();
        ddlBuilder.append("CREATE TABLE ").append(tableName).append(" (\n");
        fields.fields().forEachRemaining(entry -> {
            String fieldName = entry.getKey();
            String fieldType = entry.getValue().get("data_type").asText();
            ddlBuilder.append("    ").append(fieldName).append(" ").append(fieldType).append(",\n");
        });

        ddlBuilder.append(");");

        // Print the generated DDL statements
        System.out.println("Generated DDL:");
        System.out.println(ddlBuilder.toString());
        return new HashMap<>();
    }

}