package quicksort;

public class QuickSort {

    public int quickSort(int[] array ,int start, int end) {

        int pivotIndex = start + ((end - start) / 2);
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
            swap(array, left, right);
        }
        return left;
    }

    private void swap(int[] array, int i, int j) {
        int cache = array[i];
        array[i] = array[j];
        array[j] = cache;

    }
}
