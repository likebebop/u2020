package com.jakewharton.u2020.data;


import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.jakewharton.u2020.data.FpijTest.Folks.friends;
import static com.jakewharton.u2020.data.FpijTest.Prices.prices;

/**
 * Created by likebebop on 2017. 5. 7..
 */

public class FpijTest {

    public static class Prices {
        public static final List<BigDecimal> prices = Arrays.asList(
                new BigDecimal("10"), new BigDecimal("30"), new BigDecimal("17"),
                new BigDecimal("20"), new BigDecimal("15"), new BigDecimal("18"),
                new BigDecimal("45"), new BigDecimal("12"));
    }

    @Test
    public void testFpij() {


        final Predicate<BigDecimal> ftFunction = price -> price.compareTo(BigDecimal.valueOf(20)) > 0;
        final BigDecimal totalOfDiscountedPrices =
                prices.stream()
                        .filter(ftFunction)
                        .map(price -> price.multiply(BigDecimal.valueOf(0.9)))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Total of discounted prices: " + totalOfDiscountedPrices);

        System.out.println(" sum prices: " + prices.stream().mapToInt(BigDecimal::intValue).sum());


    }

    public static class Folks {
        public static
        final List<String> friends =
                Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

        public static

        final List<String> editors =
                Arrays.asList("Brian", "Jackie", "John", "Mike");

        public static

        final List<String> comrades =
                Arrays.asList("Kate", "Ken", "Nick", "Paula", "Zach");
    }

    @Test
    public void testHighOrder() {
        System.out.println(friends.stream().filter(name->name.startsWith("N")).count());
        System.out.println(friends.stream().filter(name->name.startsWith("R")).count());

        Function<String, Predicate<String>> startWith = letter->name->name.startsWith(letter);
        System.out.println(friends.stream().filter(startWith.apply("N")).count());
        System.out.println(friends.stream().reduce((s, s2) -> s.length() > s2.length() ? s : s2).orElse("empty"));
        System.out.println(friends.stream().reduce("", (s, s2) -> s.length() > s2.length() ? s : s2));

        System.out.println(friends.stream().collect(Collectors.toList()));

        System.out.println(friends.stream().collect(Collectors.joining()));
        System.out.println(friends.stream().collect(Collectors.joining(", ")));
        System.out.println(friends.stream().reduce((s, s1)->s+", "+s1).orElse(""));


    }

    @Test
    public void test2() {
        final Function<String, Predicate<String>> startsWithLetter = letter->name->name.startsWith(letter);

        final long countFriendsStartN =
                friends.stream()
                        .filter(startsWithLetter.apply("N")).count();

        final long countFriendsStartB =
                friends.stream()
                        .filter(startsWithLetter.apply("B")).count();

        System.out.println(countFriendsStartN);
        System.out.println(countFriendsStartB);
    }

}
