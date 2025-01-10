package task;

public class SortDetail {

    private final int start;
    private final int end;
    private static QuickSort reference;

    public SortDetail(int startIndex, int endIndex) {
        this.start = startIndex;
        this.end = endIndex;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    protected void setTaskReference(QuickSort quickSort) {
        reference = quickSort;
    }

    public QuickSort getReference() {
        return reference;
    }
}