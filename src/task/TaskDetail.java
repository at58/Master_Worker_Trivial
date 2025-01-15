package task;

public class TaskDetail {

    private final int start;
    private final int end;
    private boolean isFinalTask;

    public TaskDetail(int startIndex, int endIndex) {
        this.start = startIndex;
        this.end = endIndex;
        this.isFinalTask = false;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public int getRange() {
        return (this.getEnd() - this.getStart() + 1);
    }

    public void setFinal() {
        this.isFinalTask = true;
    }

    public boolean isFinalTask() {
        return this.isFinalTask;
    }
}