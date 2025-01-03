package masterworker;

import task.QuickSort;
import task.SortDetail;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master extends Thread {

    private final Queue<SortDetail> masterQueue;
    private List<Worker> workers;
    private QuickSort task;

    public Master() {
        this.masterQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void run() {

    }

    protected void addMasterQueue(SortDetail detail) {
        this.masterQueue.add(detail);
    }

    public void execute(QuickSort quickSort) {

        this.task = quickSort;
        SortDetail initialDetail = new SortDetail(0, quickSort.getArraySize(), this.task);
        masterQueue.add(initialDetail);
    }
}
