package com.jakewharton.u2020.data;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

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

        //Stream<Map.Entry<Dish.Type, List<Dish>>> a = Stream.of(Dish.menu).collect();
    }

}
