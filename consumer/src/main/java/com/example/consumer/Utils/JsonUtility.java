package com.example.consumer.Utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtility {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtility.class);

    private JsonUtility() {
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T jsonToObject(String in, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(in, clazz);
        } catch (Exception e) {
            logger.error(clazz.getName() + " form json fail", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <T> List<T> jsonToList(String in, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(in, new TypeReference<List<T>>() {
            });
        } catch (Exception e) {
            logger.error(clazz.getName() + " form json fail", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <T> String objectToJson(T in) {
        try {
            return OBJECT_MAPPER.writeValueAsString(in);
        } catch (Exception e) {
            logger.error(in.getClass() + " to json fail", e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
