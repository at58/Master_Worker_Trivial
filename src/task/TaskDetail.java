package task;

public class TaskDetail {

    private final int start;
    private final int end;

    public TaskDetail(int startIndex, int endIndex) {
        this.start = startIndex;
        this.end = endIndex;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }
}