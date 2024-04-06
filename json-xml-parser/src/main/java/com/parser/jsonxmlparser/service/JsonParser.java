package com.parser.jsonxmlparser.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import com.parser.jsonxmlparser.configuration.MetaDataConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JsonParser {
    private final ObjectMapper objectMapper;
    private final MetaDataConfig metadataConfig;


    public JsonParser(ObjectMapper objectMapper, MetaDataConfig metadataConfig) {
        this.objectMapper = objectMapper;
        this.metadataConfig = metadataConfig;
    }

    public Map<String, Object> parseJson(JSONObject json) {
        Map<String, Object> flattenedMap = flattenJson(json);
        // Now you can save 'flattenedMap' to the database
        System.out.println(flattenedMap);
        return  flattenedMap;
    }
    public static Map<String, Object> flattenJson(JSONObject json) {
        Map<String, Object> flattenedMap = new HashMap<>();
        flattenJsonHelper(json, "");
        return flattenedMap;
    }

    private static Map<String,Object> flattenJsonHelper(JSONObject json, String parentKey) {
        Map<String,Object> flattenedMap=new HashMap<>();
        for (String key : json.keySet()) {
            Object value = json.get(key);
            String newKey = parentKey.isEmpty() ? key : parentKey + "_" + key;

            if (value instanceof JSONObject) {
                flattenedMap.put(newKey+"_id","Integer");
                flattenJsonHelper((JSONObject) value, newKey);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                flattenedMap.put(newKey+"_id","Integer");
                for (int i = 0; i < jsonArray.length(); i++) {
                    flattenJsonHelper(jsonArray.getJSONObject(i), newKey);
                }
            } else {
                flattenedMap.put(newKey, value.getClass());
            }
        }
        String arr = "";
        if (parentKey != null && !parentKey.isEmpty()) {
            String[] keys = parentKey.split("_");
            arr = keys[keys.length - 1];
            flattenedMap.put(arr+"_id","Integer");
        }
        System.out.println(flattenedMap);
        String fileName = "output_" + arr + ".csv";

        String csvFileName = "D:\\Users\\Anushka\\Downloads\\"+fileName;

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFileName))) {
            for (Map.Entry<String, Object> entry : flattenedMap.entrySet()) {
                String[] line = {entry.getKey(), entry.getValue().toString()};
                writer.writeNext(line);
            }
            System.out.println("CSV file '" + csvFileName + "' has been created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to write to CSV file.");
        }
        return flattenedMap;
    }


    }



