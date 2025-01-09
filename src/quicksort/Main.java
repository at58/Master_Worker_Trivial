package quicksort;

import util.Generator;

public class Main {

    public static void main(String[] args) {

        int[] array = Generator.getRandomNumbers(20, 0, 50);
        System.out.println("unsorted");
        for (int i : array) {
            System.out.print(i + ", ");
        }


        QuickSort q = new QuickSort();
        int pivot = q.quickSort(array, 0, 19);

        System.out.println("\nsorted");
        for (int i : array) {
            System.out.print(i + ", ");
        }
        System.out.println("\nPivot-index: " + pivot);
    }
}
