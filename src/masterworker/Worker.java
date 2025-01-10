package masterworker;

import task.QuickSort;
import task.SortDetail;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Worker extends Thread {

    private final Queue<SortDetail> workerQueue;
    private final Master master;
    private boolean busy;

    public Worker(Master master) {
        this.workerQueue = new ConcurrentLinkedQueue<>();
        this.busy = true;
        this.master = master;
        start(); // start the run() method.
    }

    protected void addToWorkerQueue(SortDetail detail) {
        this.workerQueue.add(detail);
    }

    protected void release() {
        this.busy = false;
    }

    @Override
    public void run() {
        while (busy) {
            if ( ! workerQueue.isEmpty()) {
                SortDetail detail = this.workerQueue.poll();
                QuickSort taskReference = detail.getReference();

                int pivotIndex = taskReference.sort(detail.getStart(), detail.getEnd());

                if (pivotIndex == -1) {
                    System.out.println("Final Task added to MasterQueue");
                    this.master.addToMasterQueue(null);
                    continue;
                }

                SortDetail left = new SortDetail(detail.getStart(), (pivotIndex - 1), taskReference);
                SortDetail right = new SortDetail((pivotIndex+1), detail.getEnd(), taskReference);
                this.master.addToMasterQueue(left);
                this.master.addToMasterQueue(right);
            }
        }
    }
}
