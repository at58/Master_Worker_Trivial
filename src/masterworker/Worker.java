package masterworker;

import task.QuickSort;
import task.TaskDetail;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Worker extends Thread {

    private final int workerID;
    private final Queue<TaskDetail> workerQueue;
    private final Master master;
    private final int[] array;
    private volatile boolean active;

    public Worker(Master master, int workerID) {
        this.workerID = workerID;
        this.workerQueue = new ConcurrentLinkedQueue<>();
        this.master = master;
        this.array = master.getArray();
        this.active = true;
        start(); // start this worker-thread.
    }

    protected void addToWorkerQueue(TaskDetail detail) {
        this.workerQueue.add(detail);
    }

    protected void release() {
        System.out.println("Worker #" + this.workerID + " released");
        this.active = false;
    }

    @Override
    public void run() {
        System.out.println("Worker #" + this.workerID + " thread started...");

        while (active) {

            if ( ! workerQueue.isEmpty()) {
                TaskDetail detail = this.workerQueue.poll();

                int pivot = QuickSort.sort(this.array, detail);

                if (pivot == -1) {
                    System.out.println("Final Task added to MasterQueue");
                    this.master.addToMasterQueue(null);
                    continue;
                }

                TaskDetail left = new TaskDetail(detail.getStart(), (pivot - 1));
                TaskDetail right = new TaskDetail((pivot + 1), detail.getEnd());
                this.master.addToMasterQueue(left);
                this.master.addToMasterQueue(right);
            }
        }
        System.out.println("Worker #" + this.workerID + " thread is stopped.");
    }
}
