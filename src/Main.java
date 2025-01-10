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
        master.execute(3);

        try {
            master.join();
            System.out.println("Master joined with main thread");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Validator.isSortedArray(array);
        System.out.println("The sorted array is:");
        for (int num : array) {
            System.out.print(num + ", ");
        }

        //  System.exit(0);
    }
}
