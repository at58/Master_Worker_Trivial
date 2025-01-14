import masterworker.Master;
import task.QuickSort;
import util.Generator;
import util.Validator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int[] array = Generator.getRandomNumbers(300, 0, 999);
        System.out.println("The unsorted Array:");
        for (int num : array) {
            System.out.print(num + ", ");
        }
        System.out.println();

        Master master = new Master(array);
        long startTime = System.currentTimeMillis();
        master.execute(5);

        try {
            master.join();
            System.out.println("Master joined with main thread");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        long runTime = (endTime - startTime);
        char[] runtimeArray = String.valueOf(runTime).toCharArray();
        StringBuilder runtimeBuilder = new StringBuilder();
        if (runtimeArray.length < 4) {
            runtimeBuilder.append("0");
        }
        for (int i = 0; i < runtimeArray.length; i++) {
            if (i == (runtimeArray.length - 3)) {
                runtimeBuilder.append(".");
            }
            runtimeBuilder.append(runtimeArray[i]);
        }

        System.out.println("Runtime in sec is: " + runtimeBuilder + " sec.");

        Validator.isSortedArray(array);
        System.out.println("The sorted array is:");
        for (int num : array) {
            System.out.print(num + ", ");
        }
    }
}
