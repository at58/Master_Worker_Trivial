package task;

public class TaskDetail {

    private final int start;
    private final int end;
    private boolean isCompleted;

    public TaskDetail(int startIndex, int endIndex) {
        this.start = startIndex;
        this.end = endIndex;
        this.isCompleted = false;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public void completed() {
        this.isCompleted = true;
    }
}