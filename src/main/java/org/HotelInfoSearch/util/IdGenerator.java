package org.HotelInfoSearch.util;

import java.util.Random;

public class IdGenerator {
    private static final Integer bound = 10000;

    public static Long create() {
        Random rand = new Random();
        return System.currentTimeMillis() * bound + rand.nextInt(bound);
    }
}
