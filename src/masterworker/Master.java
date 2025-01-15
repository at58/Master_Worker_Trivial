package masterworker;

import task.TaskDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Master extends Thread {

    private final List<Worker> workerList;
    private final int[] data;
    private int dataRangeSum;
    private final Queue<TaskDetail> masterQueue;
    private boolean taskCompleted;

    public Master(int[] array) {
        this.masterQueue = new ConcurrentLinkedQueue<>();
        this.workerList = new ArrayList<>();
        this.taskCompleted = false;
        this.data = array;
        this.dataRangeSum = 0;
    }

    @Override
    public void run() {

        int numberOfWorker = (workerList.size() - 1);
        int scheduler = 0;


        while (! taskCompleted) {

            TaskDetail detail = this.masterQueue.poll();

            if (Objects.isNull(detail)) {
                try {
                    sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                Worker worker = this.workerList.get(scheduler);
                worker.addToWorkerQueue(detail);

                if (scheduler == numberOfWorker) {
                    scheduler = 0;
                } else {
                    scheduler++;
                }
            }
        }
        System.out.println("Master Thread stopped.");
    }

    protected synchronized void response(TaskDetail[] details) {

        //System.out.println("*** " + Thread.currentThread().getName() + " holds the Lock.***");

        for (TaskDetail taskDetail : details) {
            if (taskDetail.isFinalTask()) {
                this.dataRangeSum += taskDetail.getRange();
                if (this.dataRangeSum == this.data.length) {
                    for (Worker worker : this.workerList) {
                        worker.release();
                    }
                    this.taskCompleted = true;
                }
            } else {
                this.masterQueue.add(taskDetail);
            }
        }
    }

    protected int[] getData() {
        return this.data;
    }

    public void execute(int workerNumber) {

        TaskDetail initialDetail = new TaskDetail(0, (this.data.length - 1));
        this.masterQueue.add(initialDetail);

        // creates and runs worker-threads.
        for (int i = 1; i <= workerNumber; i++) {
            Worker worker = new Worker(this, i);
            workerList.add(worker);
        }
        System.out.println("Master Thread started...");
        start(); // runs the master thread.
    }
}