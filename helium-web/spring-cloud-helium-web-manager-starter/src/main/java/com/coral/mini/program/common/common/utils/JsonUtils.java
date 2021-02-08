package com.coral.mini.program.common.common.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.text.SimpleDateFormat;

public class JsonUtils {

    private static ObjectMapper toJSONMapper = new ObjectMapper();

    private static ObjectMapper fromJSONMapper = new ObjectMapper();
    static {
        fromJSONMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        fromJSONMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        fromJSONMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        toJSONMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public static String toJSON(Object obj) {
        try {
            return toJSONMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toPrettyJSON(Object obj) {
        try {
            return toJSONMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return fromJSONMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(InputStream inputStream, Class<T> clazz) {
        try {
            return fromJSONMapper.readValue(inputStream, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
