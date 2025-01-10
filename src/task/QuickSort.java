package task;

public class QuickSort {

    /**
     * Executes the sorting algorithm of Quicksort.
     *
     * @param detail The task detail.
     * @return the index of the pivot element, which splits the partition into 2 new partitions.
     */
    public static int sort(int[] array, TaskDetail detail) {

        int start = detail.getStart();
        int end = detail.getEnd();

        if ((end - start) <= 100) {

            System.out.println("BUBBLE SORT");
            bubbleSort(array ,start, end);
            return -1;
        }

        return quickSort(array, start, end);
    }

    private static int quickSort(int[] array, int start, int end) {

        int pivotIndex = start + ((end - start) / 2);
        System.out.println("Pivot Index: " + pivotIndex);
        int pivotElement = array[pivotIndex];

        int left = start;
        int right = end;

        while (left < right) {

            while (array[left] < pivotElement) {
                left ++;
            }
            while (array[right] > pivotElement) {
                right --;
            }
            if (left == right) {
                break;
            }
            // else: swap left and right
            swap(array, left, right);
        }
        return left; // return right would be the same because the values are equal at the end.
    }

    private static void swap(int[] array, int i, int j) {
        int cache = array[i];
        array[i] = array[j];
        array[j] = cache;
    }

    private static void bubbleSort(int[] array, int start, int end) {
        for (int i = start; i <= end; i++) {
            for (int j = start; j < end ; j++) {
                int a = array[j];
                int b = array[j + 1];

                if (a > b) {
                    array[j] = b;
                    array[j + 1] = a;
                }
            }
        }
    }
}