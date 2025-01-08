package task;

public class QuickSort {

    private int[] array;

    public QuickSort(int[] array) {
        this.array = array;
    }

    public int getArrayLength() {
        return this.array.length;
    }

    /**
     * Executes the sorting algorithm of Quicksort.
     *
     * @param start The start index of the sorting range.
     * @param end The end index of the sorting range.
     * @return the index of the pivot element, which splits up the range into 2 new ranges.
     */
    public int sort(int start, int end) {

        if ((end - start) <= 100) {
            //TODO: Final sort with BubbleSort

            return -1;
        }
        // TODO: implement Quicksort algorithm here.

        return 0; // return the real pivot index after implementation.
    }
}