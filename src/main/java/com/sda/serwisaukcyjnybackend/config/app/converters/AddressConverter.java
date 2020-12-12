package com.sda.serwisaukcyjnybackend.config.app.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.serwisaukcyjnybackend.config.app.DefaultObjectMapper;
import com.sda.serwisaukcyjnybackend.domain.shared.Address;

import javax.persistence.AttributeConverter;

public class AddressConverter implements AttributeConverter<Address, String> {

    private static final ObjectMapper objectMapper = DefaultObjectMapper.getInstance();

    @Override
    public String convertToDatabaseColumn(Address attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        }  catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Address convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, Address.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}
