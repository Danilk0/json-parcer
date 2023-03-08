package com.moskalyuk.json;

import com.moskalyuk.json.util.JsonUtil;
import lombok.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Collection;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public  class JsonValue {

    private String key;

    private Object value;

    public JsonValue(String jsonValue){
        String[] split = jsonValue.split(":");
        this.key=split[0].substring(1,split[0].length()-1);
        this.value = split[1];
    }

    public Object getValue(Field field) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<?> type = field.getType();
        String stringValue = this.value.toString();
        return JsonUtil.convertValue(stringValue,type);
    }

    @Override
    public String toString() {
        if(value instanceof String)
            return key + ":\"" + value +"\"";
        if(value instanceof Boolean || value instanceof Integer || value instanceof Float )
            return key + ":" + value;

        return key + ":" + null ;
    }
}
