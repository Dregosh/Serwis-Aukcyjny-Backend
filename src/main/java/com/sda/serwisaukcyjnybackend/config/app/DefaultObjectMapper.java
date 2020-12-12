package com.sda.serwisaukcyjnybackend.config.app;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultObjectMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getInstance() {
        return objectMapper;
    }
}
