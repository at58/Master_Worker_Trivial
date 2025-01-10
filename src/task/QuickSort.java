package task;

public class QuickSort {

    private int[] array;

    public QuickSort(int[] array) {
        this.array = array;
    }

    public int getArrayLength() {
        return this.array.length;
    }

    public SortDetail getInitialTaskDetail() {
        SortDetail initialDetail = new SortDetail(0, (this.array.length - 1));
        initialDetail.setTaskReference(this);
        return initialDetail;
    }

    /**
     * Executes the sorting algorithm of Quicksort.
     *
     * @param start The start index of the sorting range.
     * @param end The end index of the sorting range.
     * @return the index of the pivot element, which splits the partition into 2 new partitions.
     */
    public int sort(int start, int end) {

        if ((end - start) <= 100) {

            System.out.println("BUBBLE");
            bubbleSort(start, end);
            return -1;
        }

        return quickSort(start, end); // return the real pivot index after implementation.
    }

    // TODO: implement Quicksort algorithm here.
    private int quickSort(int start, int end) {

        int pivotIndex = start + ((end - start) / 2);
        System.out.println("Pivot Index: " + pivotIndex);
        int pivotElement = this.array[pivotIndex];

        int left = start;
        int right = end;

        while (left < right) {

            while (array[left] < pivotElement) {
                left ++;
            }
            while (array[right] > pivotElement) {
                right --;
            }
            swap(left, right);
        }
        return left; // return right would be the same because the values are equal at the end.
    }

    private void swap(int i, int j) {
        int cache = this.array[i];
        this.array[i] = this.array[j];
        this.array[j] = cache;

    }

    private void bubbleSort(int start, int end) {
        for (int i = start; i <= end; i++) {
            for (int j = start; j < end ; j++) {
                int a = this.array[j];
                int b = this.array[j + 1];

                if (a > b) {
                    this.array[j] = b;
                    this.array[j + 1] = a;
                }
            }
        }
    }
}