package com.jakewharton.u2020.data;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.BiFunction;

import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by likebebop on 2017-04-25.
 */

public class Chap6 {

    //-- https://github.com/aNNiMON/Lightweight-Stream-API
    @Test
    public void testGrouping() {
        System.out.println(Stream.of(Dish.menu).collect(Collectors.toList()));
        Map<Dish.Type, List<Dish>> map = Stream.of(Dish.menu).collect(Collectors.groupingBy(Dish::getType));
        System.out.println(map.toString());
        Stream.of(Dish.menu).groupBy(Dish::getType).forEach(System.out::println);
        System.out.println("isVegetarian -> " + Stream.of(Dish.menu).filter(Dish::isVegetarian).peek(System.out::println).count());

        System.out.println("All Calories 1 " + Stream.of(Dish.menu).reduce(0, (s, d) -> d.getCalories() + s));
        System.out.println("All Calories 2 " + Stream.of(Dish.menu).map(Dish::getCalories).reduce(Integer::sum).get());
    }

}
