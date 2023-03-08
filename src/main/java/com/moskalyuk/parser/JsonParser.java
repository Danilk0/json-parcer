package com.moskalyuk.parser;

import com.moskalyuk.json.JsonArray;
import com.moskalyuk.json.JsonObject;
import com.moskalyuk.json.JsonValue;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.*;

@RequiredArgsConstructor
public class JsonParser {

    public JsonObject toJson(Object object, JsonObject jsonFile){
        Field[] declaredFields = object.getClass().getDeclaredFields();
        Arrays.stream(declaredFields)
                .forEach(field -> {
                    try {
                        Object fieldValue = getFieldValue(field, object);
                        if(fieldValue instanceof Collection<?>)
                            jsonFile.addJsonValue(arrayToJson(field,fieldValue));
                        else
                            jsonFile.addJsonValue(new JsonValue(aroundKey(field.getName()),fieldValue));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        return jsonFile;
    }
    public Object toObject(String jsonFile, Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = clazz.getConstructor();
        Object obj = constructor.newInstance();

        List<String> jsonObject = createJsonObject(jsonFile);
        jsonObject.stream()
                .map(value -> {
                    if (isJsonArray(value))
                        return new JsonArray(value);
                    else
                        return new JsonValue(value);
                })
                .forEach(jsonValue->{
                            try {
                                Field field=clazz.getField(jsonValue.getKey());
                                field.setAccessible(true);
                                if(jsonValue instanceof JsonArray)
                                    field.set(obj,((JsonArray) jsonValue).getJsonValues(field) );
                                else
                                    field.set(obj,jsonValue.getValue(field));
                                field.setAccessible(false);
                            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException |
                                     InstantiationException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                });

        return obj;
    }

    private static List<String> createJsonObject(String jsonFile){
        String json = jsonFile.substring(1, jsonFile.length() - 1);
        boolean arrayFlag =false;
        List<String> jsonObject =new ArrayList<>();
        StringBuilder jsonValue= new StringBuilder();
        for (char c : json.toCharArray()) {
            if(c==',' && !arrayFlag){
                jsonObject.add(jsonValue.toString());
                jsonValue = new StringBuilder();
                continue;
            }
            if(c=='[')
                arrayFlag = true;

            if(c==']')
                arrayFlag = false;

            jsonValue.append(c);
        }
        jsonObject.add(jsonValue.toString());
        return jsonObject;
    }

    private static boolean isJsonArray(String jsonValue){
        return jsonValue.split(":")[1].startsWith("[");
    }

    private static JsonArray arrayToJson(Field field,Object ref) throws IllegalAccessException {
        JsonArray jsonArray = new JsonArray();
        jsonArray.setKey(aroundKey(field.getName()));
        for (Object item : (Collection<?>) ref) {
             jsonArray.addJsonValue(item);
        }
        return jsonArray;

    }

    private static Object getFieldValue(Field field,Object o) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(o);

    }

    private static String aroundKey(String key){
        return "\""+key+"\"";
    }

}
