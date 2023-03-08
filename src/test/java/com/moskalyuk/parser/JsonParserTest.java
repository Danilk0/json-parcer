package com.moskalyuk.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moskalyuk.json.JsonObject;
import com.moskalyuk.model.Car;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JsonParserTest {

    public static ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    public static JsonParser parser = new JsonParser();

    private static Car CAR = Car.builder()
            .id(1)
            .name("car")
            .strings(List.of("1a","2b"))
            .integers(List.of(1,2))
            .booleans(List.of(true,false,true))
            .build();


    @Test
    void checkParseToJson() throws JsonProcessingException {
        String expected =objectMapper.writeValueAsString(CAR);
        JsonObject jsonObject = new JsonObject();
        JsonObject actual =parser.toJson(CAR,jsonObject);

        assertThat(actual.toString()).isEqualTo(expected);
    }

    @Test
    void checkParseToObject() throws JsonProcessingException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String str=objectMapper.writeValueAsString(CAR);
        Car actual = (Car) parser.toObject(str,Car.class);

        assertThat(actual).isEqualTo(CAR);
    }
}