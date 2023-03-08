package com.moskalyuk.json.util;

import java.math.BigDecimal;

public class JsonUtil {
    public static Object convertValue(String stringValue,Class<?> type){
        if ("null".equals(stringValue))
            return null;
        else if ("java.lang.String".equals(type.getName()))
            return stringValue;
        else if ("java.lang.Character".equals(type.getName()) || "char".equals(type.getName()))
            return stringValue.charAt(1);
        else if ("java.lang.Boolean".equals(type.getName()) || "boolean".equals(type.getName()))
            return Boolean.valueOf(stringValue);
        else {
            return getNumber(type, stringValue);
        }
    }

    private static Object getNumber(Class<?> type, String stringValue) {
        return switch (type.getSimpleName()) {
            case "Short", "short" -> Short.parseShort(stringValue);
            case "Byte", "byte" -> Byte.parseByte(stringValue);
            case "Integer", "int" -> Integer.parseInt(stringValue);
            case "Long", "long" -> Long.parseLong(stringValue);
            case "BigDecimal" -> new BigDecimal(stringValue);
            case "Double", "double" -> Double.parseDouble(stringValue);
            default -> Float.parseFloat(stringValue);
        };
    }
}
