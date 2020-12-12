package com.sda.serwisaukcyjnybackend.config.app.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.serwisaukcyjnybackend.config.app.DefaultObjectMapper;

import javax.persistence.AttributeConverter;
import java.util.HashMap;

public class MapConverter implements AttributeConverter<HashMap<String, Object>, String> {
    private static final ObjectMapper objectMapper = DefaultObjectMapper.getInstance();
    private static final TypeReference<HashMap<String, Object>> typeReference = new TypeReference<>() {};

    @Override
    public String convertToDatabaseColumn(HashMap<String, Object> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public HashMap<String, Object> convertToEntityAttribute(String dbData) {
       try {
           return objectMapper.readValue(dbData, typeReference);
       } catch (JsonProcessingException e) {
           throw new RuntimeException();
       }
    }
}
