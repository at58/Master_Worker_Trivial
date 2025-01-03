package masterworker;

import task.QuickSort;
import task.SortDetail;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master extends Thread {

    private final Queue<SortDetail> masterQueue;

    public Master() {
        this.masterQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void run() {

    }

    public void execute(QuickSort object) {

        SortDetail initialDetail =
    }
}
