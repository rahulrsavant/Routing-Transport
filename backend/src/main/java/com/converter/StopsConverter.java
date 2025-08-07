package com.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Collections;
import java.util.List;

@Converter
public class StopsConverter implements AttributeConverter<List<String>, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> stops) {
        try {
            return mapper.writeValueAsString(stops);
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, new TypeReference<List<String>>() {
            });
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
