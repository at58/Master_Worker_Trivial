import masterworker.Master;
import task.QuickSort;
import util.Generator;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        int[] bigArray = Generator.getRandomNumbers(150, 0, 200);
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
        master.execute(new QuickSort(bigArray), 3);

        try {
            master.join();
            System.out.println("Master thread returned to Main");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("The sorted array is:");
        for (int num : bigArray) {
            System.out.print(num + ", ");
        }

        Map<Integer, String> map = new TreeMap<>();
        Map<Integer, String> hash = new HashMap<>();
        System.exit(0);
    }
}
