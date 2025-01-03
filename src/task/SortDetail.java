package task;

public class SortDetail {

    private final int start;
    private final int end;
    private final QuickSort reference;

    public SortDetail(int startIndex, int endIndex, QuickSort reference) {
        this.start = startIndex;
        this.end = endIndex;
        this.reference = reference;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public QuickSort getReference() {
        return this.reference;
    }
}
