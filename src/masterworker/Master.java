package masterworker;

import task.TaskDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master extends Thread {

    private final List<Worker> workerList;
    private final int[] array;
    private final Queue<TaskDetail> masterQueue;
    private int incomingTaskCounter;
    private int finalTaskCounter;
    private boolean taskCompleted;
    private int releasedWorkerCounter;

    public Master(int[] array) {
        this.masterQueue = new ConcurrentLinkedQueue<>();
        this.workerList = new ArrayList<>();
        this.incomingTaskCounter = 0;
        this.finalTaskCounter = 0;
        this.taskCompleted = false;
        this.array = array;
    }

    @Override
    public void run() {

        int numberOfWorker = (workerList.size() - 1);
        int scheduler = 0;


        while (! taskCompleted) {

            TaskDetail detail = this.masterQueue.poll();

            if (Objects.isNull(detail)) {
                try {
                    sleep(500);
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

    protected synchronized void addToMasterQueue(TaskDetail detail) {
        if (detail == null) {
            finalTaskCounter += 2;
        } else {
            this.masterQueue.add(detail);
            incomingTaskCounter++;
        }
        System.out.println("task count " + (incomingTaskCounter+1));
        System.out.println("final task count " + finalTaskCounter);
        if ((incomingTaskCounter + 1) == finalTaskCounter) {
            for (Worker worker : this.workerList) {
                worker.release();
            }
            taskCompleted = true;
        }
    }

    protected int[] getArray() {
        return this.array;
    }

    public void execute(int workerNumber) {

        TaskDetail initialDetail = new TaskDetail(0, (this.array.length - 1));
        addToMasterQueue(initialDetail);

        // creates and runs worker-threads.
        for (int i = 1; i <= workerNumber; i++) {
            Worker worker = new Worker(this, i);
            workerList.add(worker);
        }
        System.out.println("Master Thread started...");
        start(); // runs the master thread.
    }
}