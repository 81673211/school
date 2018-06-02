package com.school.util.core.utils;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class RequestParseUtil {
    /**
     * parse json string to Class<T> object
     * @param req
     * @return List<T>>
     * @throws Exception
     */
    public static <T> T parseRequestJsonDataToObject(String jsonData, Class<T> objectType) throws Exception {
        T object = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            object = objectMapper.readValue(jsonData, objectType);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return object;
    }

    /**
     * parse json string to Class<T> object
     * @param req
     * @return List<T>>
     * @throws Exception
     */
    public static <T> List<T> parseRequestJsonDataToList(String jsonData, Class<T[]> arrayType) throws Exception {
        List<T> objectList = new ArrayList<T>(10);
        T[] array = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            array = (T[]) objectMapper.readValue(jsonData, arrayType);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        for (int i = 0; i < array.length; i++) {
            objectList.add(array[i]);
        }

        return objectList;
    }

    /**
     * generate object to json string
     * @param object
     * @return
     * @throws Exception
     */
    public static String toJsonString(Object object) throws Exception {
        if(object == null){
            return "";
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
