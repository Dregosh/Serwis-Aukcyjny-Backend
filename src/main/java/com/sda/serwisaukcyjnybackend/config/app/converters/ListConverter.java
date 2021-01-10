package com.sda.serwisaukcyjnybackend.config.app.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.serwisaukcyjnybackend.config.app.DefaultObjectMapper;

import javax.persistence.AttributeConverter;
import java.util.List;

public class ListConverter implements AttributeConverter<List<String>, String> {
    private static final ObjectMapper objectMapper = DefaultObjectMapper.getInstance();
    private static final TypeReference<List<String>> typeReference = new TypeReference<>() {};

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}
