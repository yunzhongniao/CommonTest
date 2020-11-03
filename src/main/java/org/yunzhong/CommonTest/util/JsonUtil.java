package org.yunzhong.CommonTest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author yunzhong
 *
 */
public class JsonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> T fromJson(String json, Class<T> clazz) throws JsonMappingException, JsonProcessingException {
        return mapper.readValue(json, clazz);
    }

    public static <T> String toJson(T obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

}
