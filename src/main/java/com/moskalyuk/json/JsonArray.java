package com.moskalyuk.json;

import com.moskalyuk.json.util.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@NoArgsConstructor
@Getter
public class JsonArray extends JsonValue {
    private final List<Object> jsonValues = new ArrayList<>();

    public void addJsonValue(Object value) {
        this.jsonValues.add(value);
    }

    public JsonArray(String jsonArray){
        String[] split = jsonArray.split(":");
        setKey(split[0].substring(1,split[0].length()-1));
        String value = split[1];
        if(value.startsWith("[") && value.endsWith("]")){
            String substring = value.substring(1, value.length() - 1);
            for (String s : substring.split(",")) {
               if(s.startsWith("\"") && s.endsWith("\""))
                   this.jsonValues.add(s.substring(1,s.length()-1));
               else
                   this.jsonValues.add(s);
            }
        }
    }

    public List<Object> getJsonValues(Field field) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ParameterizedType listType = (ParameterizedType) field.getGenericType();
        Class<?> type = (Class<?>) listType.getActualTypeArguments()[0];
        return jsonValues.stream()
                .map(Object::toString)
                .map(stringValue-> JsonUtil.convertValue(stringValue,type))
                .toList();
    }


    @Override
    public String toString() {
        String collect = jsonValues.stream()
                .map(value->{
                    if(value instanceof String)
                        return "\"" + value +"\"";
                    return value.toString();
                })
                .collect(Collectors.joining(","));
        return getKey()+":[" +
                collect +
                "]";
    }

}
