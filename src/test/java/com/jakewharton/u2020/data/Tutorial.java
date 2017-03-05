package com.jakewharton.u2020.data;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import static junit.framework.Assert.assertEquals;


/**
 * Created by likebebop on 2017. 2. 5..
 */

public class Tutorial {
    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");
    List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    @Test
    public void testMatch() {
        /*System.out.printf("any match : " + Dish.menu.stream().noneMatch(new Predicate<Dish>() {
            @Override
            public boolean test(Dish dish) {
                return dish.getCalories() >   800;
            }
        }));*/

        Dish.menu.stream().filter(Dish::isVegetarian).findAny().ifPresent(System.out::println);
        Dish.menu.stream().filter(Dish::isVegetarian).findFirst().ifPresent(System.out::println);

        System.out.println(Dish.menu.stream().map(dish -> dish.getCalories()).reduce(0, (a, b) -> {
                    System.out.printf("%d + %d\n", a, b);
                    return a + b;
                }
        ));

        System.out.println(Dish.menu.stream().map(dish -> dish.getCalories()).reduce(0, (a, b) -> a + b));

        System.out.println(Dish.menu.stream().map(dish -> dish.getCalories()).reduce((a, b) -> Math.max(a, b)));
    }

    @Test
    public void stream() {
        String[] list = {"soyeon", "bebop", "taeyeon", "ej"};
        pickName(list, "be");
        pickName(list, "ba");
        pickName(list, "so");

    }

    void pickName(String[] list, String startWith) {
        Optional<String> r = Stream.of(list).filter(s -> s.startsWith(startWith)).findFirst();
        System.out.printf("startWith %s => %s\n", startWith, r.orElse("not found"));
    }

    @Test
    public void testPractice() {
        transactions.stream().filter(t -> t.getYear() == 2011).sorted((o1, o2) -> o1.getValue() - o2.getValue()).forEach(System.out::println);

        transactions.stream().map(t -> t.getTrader().getCity()).distinct().forEach(System.out::println);

        transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge")).distinct().sorted((o1, o2) -> o1.getTrader().getName().compareTo(o2.getTrader().getName())).forEach(System.out::println);

        //transactions.stream().filter(t->t.getTrader().getCity().equals("Cambridge")).map(t->t.getTrader()).distinct().sorted(Comparator.comparing(Trader::getName)).forEach(System.out::println);

        System.out.println(transactions.stream().map(t -> t.getTrader().getName()).distinct().sorted().reduce((a, b) -> {
            System.out.println(a + " " + b);
            return a + " " + b;
        }).orElse("empty"));

        //System.out.println(transactions.stream().map(t->t.getTrader().getName()).distinct().sorted().collect(joining(",")));

        System.out.println(transactions.stream().filter(t -> t.getTrader().getCity().equals("Milan")).findAny().isPresent());

        System.out.println(transactions.stream().anyMatch(t -> t.getTrader().getCity().equals("Milan")));

        System.out.println(transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge")).map(t -> Integer.toString(t.getValue())).reduce((a, b) -> (a + " " + b)));

        //System.out.println(transactions.stream().map(t->t.getValue()).reduce((a, b)->Math.max(a, b)));
        System.out.println(transactions.stream().map(t -> t.getValue()).reduce(Math::max));
        //System.out.println(transactions.stream().map(Transaction::getValue).reduce(Integer::max));
        System.out.println(transactions.stream().map(t -> t.getValue()).reduce((a, b) -> Math.min(a, b)));


    }

    @Test
    public void testLamda() {
        List<Integer> list = new ArrayList();
        list.add(1);

        Runnable r = () -> list.add(3);
        Callable<Boolean> c = () -> list.add(3);
        Function<Boolean, Integer> f = a -> a ? 3 : 1;
        assertEquals(3, (int) f.apply(true));
        assertEquals(1, (int) f.apply(false));
    }

    static public class Dish {

        public static final List<Dish> menu =
                Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT),
                        new Dish("beef", false, 700, Dish.Type.MEAT),
                        new Dish("chicken", false, 400, Dish.Type.MEAT),
                        new Dish("french fries", true, 530, Dish.Type.OTHER),
                        new Dish("rice", true, 350, Dish.Type.OTHER),
                        new Dish("season fruit", true, 120, Dish.Type.OTHER),
                        new Dish("pizza", true, 550, Dish.Type.OTHER),
                        new Dish("prawns", false, 400, Dish.Type.FISH),
                        new Dish("salmon", false, 450, Dish.Type.FISH));
        private final String name;
        private final boolean vegetarian;
        private final int calories;
        private final Type type;

        public Dish(String name, boolean vegetarian, int calories, Type type) {
            this.name = name;
            this.vegetarian = vegetarian;
            this.calories = calories;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public boolean isVegetarian() {
            return vegetarian;
        }

        public int getCalories() {
            return calories;
        }

        public Type getType() {
            return type;
        }

        @Override
        public String toString() {
            return name;
        }

        public enum Type {MEAT, FISH, OTHER}
    }

    static public class Trader {

        private String name;
        private String city;

        public Trader(String n, String c) {
            this.name = n;
            this.city = c;
        }

        public String getName() {
            return this.name;
        }

        public String getCity() {
            return this.city;
        }

        public void setCity(String newCity) {
            this.city = newCity;
        }

        public String toString() {
            return "Trader:" + this.name + " in " + this.city;
        }
    }

    static public class Transaction {

        private Trader trader;
        private int year;
        private int value;

        public Transaction(Trader trader, int year, int value) {
            this.trader = trader;
            this.year = year;
            this.value = value;
        }

        public Trader getTrader() {
            return this.trader;
        }

        public int getYear() {
            return this.year;
        }

        public int getValue() {
            return this.value;
        }

        public String toString() {
            return "{" + this.trader + ", " +
                    "year: " + this.year + ", " +
                    "value:" + this.value + "}";
        }
    }
}
