import masterworker.Master;
import task.QuickSort;
import util.Generator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int[] array = new int[] {9, 10, 1, 25, 47, 2, 0, 4, 16};
        int[] bigArray = Generator.getRandomNumbers(110, 0, 1000);
        System.out.println("The unsorted Array:");
        for (int num : bigArray) {
            System.out.print(num + ", ");
        }

        /*Scanner scanner = new Scanner(System.in);
        System.out.println("How many workers do you want to apply?");
        String worker = scanner.next();
        int workerNumber = Integer.parseInt(worker);
        scanner.close();*/

        Master master = new Master();
        master.execute(new QuickSort(bigArray), 1);

        try {
            master.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("The sorted array is:");
        for (int num : bigArray) {
            System.out.print(num + ", ");
        }

        System.exit(0);
    }
}
