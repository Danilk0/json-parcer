package com.moskalyuk.json;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class JsonObject {

    private List<JsonValue> jsonValues = new ArrayList<>();

    public void addJsonValue(JsonValue value) throws IllegalAccessException {
        this.jsonValues.add(value);
    }

    @Override
    public String toString() {
        String collect = jsonValues.stream()
                .map(JsonValue::toString)
                .collect(Collectors.joining(","));
        return "{" +
                collect +
                '}';
    }


}
