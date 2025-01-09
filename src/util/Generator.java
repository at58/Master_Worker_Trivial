package util;

import java.util.Random;

public class Generator {

    private static final Random random = new Random();

    public static int[] getRandomNumbers(int length, int from, int to) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt(from, to);
        }
        return array;
    }
}
