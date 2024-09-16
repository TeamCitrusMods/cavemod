package com.elysiasilly.cavemod.utils;

import java.util.Random;

public class RandomNumber {

    public static int between(int min, int max) {
        Random a = new Random();
        return a.nextInt(max-min);
    }
}
