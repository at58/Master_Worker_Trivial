package masterworker;

import task.QuickSort;
import task.TaskDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Worker extends Thread {

    private final int workerID;
    private final Queue<TaskDetail> workerQueue;
    private final Queue<TaskDetail> followUpTaskDetails;
    private final int[] data;
    private final AtomicBoolean active;

    public Worker(int[] array, int workerID) {
        this.workerID = workerID;
        this.workerQueue = new ConcurrentLinkedQueue<>();
        this.followUpTaskDetails = new ConcurrentLinkedQueue<>();
        this.data = array;
        this.active = new AtomicBoolean(true);
        start(); // start this worker-thread.
    }

    protected void addToWorkerQueue(TaskDetail detail) {
        this.workerQueue.add(detail);
    }

    protected List<TaskDetail> queryFollowUps() {
        List<TaskDetail> followUps = new ArrayList<>();
        while (!followUpTaskDetails.isEmpty()) {
            followUps.add(followUpTaskDetails.poll());
        }
        return followUps;
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

                int pivot = QuickSort.sort(this.data, detail);

                if (pivot == -1) {
                    continue;
                }
                this.followUpTaskDetails.add(new TaskDetail(detail.getStart(), (pivot - 1)));
                this.followUpTaskDetails.add(new TaskDetail((pivot + 1), detail.getEnd()));
            } else {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        System.out.println("Worker #" + this.workerID + " thread is stopped.");
    }
}
