package util;

public class Validator {

    public static boolean isSortedArray(int[] array) {

        for (int i = 0; i < (array.length - 1); i++) {
            int current = array[i];
            int next = array[i + 1];
            if (current > next) {
                System.out.println("Array is not in order at index: " + i);
                return false;
            }
        }
        System.out.println("The Array is sorted precisely!");
        return true;
    }
}
