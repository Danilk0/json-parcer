package com.moskalyuk;

import com.moskalyuk.json.JsonObject;
import com.moskalyuk.model.Car;
import com.moskalyuk.parser.JsonParser;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


public class Main {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Car car= Car.builder()
                .id(1)
                .name("car")
                .strings(List.of("1a","2b"))
                .integers(List.of(1,2))
                .booleans(List.of(true,false,true))
                .build();
        JsonParser parser=new JsonParser();
        JsonObject jsonObject=new JsonObject();
        System.out.println(parser.toJson(car,jsonObject));
        System.out.println(parser.toObject(jsonObject.toString(), Car.class));
    }

}