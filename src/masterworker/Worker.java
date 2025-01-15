package masterworker;

import task.QuickSort;
import task.TaskDetail;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Worker extends Thread {

    private final int workerID;
    private final Queue<TaskDetail> workerQueue;
    private final Master master;
    private final int[] array;
    private final AtomicBoolean active;

    public Worker(Master master, int workerID) {
        this.workerID = workerID;
        this.workerQueue = new ConcurrentLinkedQueue<>();
        this.master = master;
        this.array = master.getData();
        this.active = new AtomicBoolean(true);
        start(); // start this worker-thread.
    }

    protected void addToWorkerQueue(TaskDetail detail) {
        this.workerQueue.add(detail);
    }

    protected void release() {
        System.out.println("Worker #" + this.workerID + " released");
        this.active.set(false);
    }

    @Override
    public void run() {
        System.out.println("Worker #" + this.workerID + " thread started...");

        while (active.get()) {

            if ( ! workerQueue.isEmpty()) {
                TaskDetail detail = this.workerQueue.poll();

                int pivot = QuickSort.sort(this.array, detail);

                if (pivot == -1) {
                    detail.setFinal();
                    this.master.response(new TaskDetail[] {detail});
                    continue;
                }

                TaskDetail left = new TaskDetail(detail.getStart(), (pivot - 1));
                TaskDetail right = new TaskDetail((pivot + 1), detail.getEnd());
                this.master.response(new TaskDetail[] {left, right});
            }
        }
        System.out.println("Worker #" + this.workerID + " thread is stopped.");
    }
}
