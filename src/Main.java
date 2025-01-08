import masterworker.Master;
import task.QuickSort;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int[] array = new int[] {9, 10, 1, 25, 47, 2, 0, 4, 16};

        QuickSort quickSort = new QuickSort(array);

        Master master = new Master();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Wieviele Worker sollen eingesetzt werden?");
        String worker = scanner.next();
        int workerNumber = Integer.parseInt(worker);

        master.execute(quickSort, workerNumber);


    }
}
