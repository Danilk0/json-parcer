package com.moskalyuk.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    public Integer id;

    public String name;

    public List<Integer> integers;
    public List<String> strings;
    public List<Boolean> booleans;

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", integers=" + integers +
                ", strings=" + strings +
                ", booleans=" + booleans +
                '}';
    }
}
