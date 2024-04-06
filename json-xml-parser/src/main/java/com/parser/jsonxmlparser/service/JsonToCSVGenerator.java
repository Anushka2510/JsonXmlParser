package com.parser.jsonxmlparser.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
public class JsonToCSVGenerator {


//    public void generateCsv(String json,String csvFilePath) {
//
//
//            try {
//                ObjectMapper objectMapper = new ObjectMapper();
//                // Parse the JSON string into a single object
//                Object obj = objectMapper.readValue(json, Object.class);
//
//                // Create a list and add the object to it
//                List<Object> list = new ArrayList<>();
//                list.add(obj);
//                // Parse JSON response
//                JSONArray jsonArray = new JSONArray(list);
//
//                // Open CSV file for writing
//
//                FileWriter csvWriter = new FileWriter(csvFilePath);
//
//                // Write column headers
//                JSONObject firstObject = jsonArray.getJSONObject(0);
//                Iterator<String> keys = firstObject.keys();
//                while (keys.hasNext()) {
//                    String key = keys.next();
//                    csvWriter.append("\"" + key + "\"");
//                    if (keys.hasNext()) {
//                        csvWriter.append(",");
//                    }
//                }
//                csvWriter.append("\n");
//
//                // Write data to CSV file
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    Iterator<String> iterator = jsonObject.keys();
//                    while (iterator.hasNext()) {
//                        String key = iterator.next();
//                        csvWriter.append("\"" + jsonObject.getString(key) + "\"");
//                        if (iterator.hasNext()) {
//                            csvWriter.append(",");
//                        }
//                    }
//                    csvWriter.append("\n");
//                }
//
//                // Close CSV writer
//                csvWriter.flush();
//                csvWriter.close();
//
//                System.out.println("CSV file '" + csvFilePath + "' has been created successfully.");
//
//            } catch (JSONException | IOException e) {
//                e.printStackTrace();
//                System.out.println("Failed to convert JSON to CSV.");
//            }
//        }


    public void generateCsv(String json, String csvFilePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Parse the JSON string into a JSON object
            JSONObject jsonObject = new JSONObject(json);

            // Open CSV file for writing
            FileWriter csvWriter = new FileWriter(csvFilePath);

            // Write column headers
            writeHeaders(jsonObject, csvWriter);

            // Write data to CSV file
            writeData(jsonObject, csvWriter);

            // Close CSV writer
            csvWriter.flush();
            csvWriter.close();

            System.out.println("CSV file '" + csvFilePath + "' has been created successfully.");
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            System.out.println("Failed to convert JSON to CSV.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeHeaders(JSONObject jsonObject, FileWriter csvWriter) throws IOException {
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                // Recursively write nested object keys
                JSONObject nestedObject = (JSONObject) value;
                writeHeaders(nestedObject, csvWriter);
            } else if (value instanceof JSONArray) {
                // Handle nested arrays
                JSONArray nestedArray = (JSONArray) value;
                if (nestedArray.length() > 0 && nestedArray.get(0) instanceof JSONObject) {
                    JSONObject nestedObject = nestedArray.getJSONObject(0);
                    writeHeaders(nestedObject, csvWriter);
                }
            } else {
                // Write column header
                csvWriter.append("\"" + key + "\"");
                if (keys.hasNext()) {
                    csvWriter.append(",");
                }
            }
        }
        csvWriter.append("\n");
    }

    private void writeData(JSONObject jsonObject, FileWriter csvWriter) throws IOException {
        List<String> rowData = new ArrayList<>();
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                // Recursively write nested object values
                JSONObject nestedObject = (JSONObject) value;
                writeData(nestedObject, csvWriter);
            } else if (value instanceof JSONArray) {
                // Handle nested arrays
                JSONArray nestedArray = (JSONArray) value;
                for (int i = 0; i < nestedArray.length(); i++) {
                    Object nestedValue = nestedArray.get(i);
                    if (nestedValue instanceof JSONObject) {
                        JSONObject nestedObject = (JSONObject) nestedValue;
                        writeData(nestedObject, csvWriter);
                    }
                }
            } else {
                // Write data to CSV file
                rowData.add("\"" + value.toString() + "\"");
            }
        }
        csvWriter.append(String.join(",", rowData));
        csvWriter.append("\n");
    }


}
