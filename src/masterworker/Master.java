package masterworker;

import task.QuickSort;
import task.SortDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master extends Thread {

    private final Queue<SortDetail> masterQueue;
    private final List<Worker> workerList;
    private int incomingTaskCounter;
    private int finalTaskCounter;
    private boolean taskCompleted;

    public Master() {
        this.masterQueue = new ConcurrentLinkedQueue<>();
        this.workerList = new ArrayList<>();
        this.incomingTaskCounter = 0;
        this.finalTaskCounter = 0;
        this.taskCompleted = false;
    }

    @Override
    public void run() {

        System.out.println("Master Thread started...");
        int numberOfWorker = workerList.size();
        int scheduler = 0;

        while (! this.taskCompleted) {

            SortDetail detail = this.masterQueue.poll();

            if (Objects.isNull(detail)) {
                try {
                    sleep(200);
                    checkTaskState();
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Worker worker = this.workerList.get(scheduler);
            worker.addToWorkerQueue(detail);

            if (scheduler == numberOfWorker) {
                scheduler = 0;
            } else {
                scheduler++;
            }
        }
        System.out.println("Master Thread finished.");
    }

    protected void addToMasterQueue(SortDetail detail) {
        if (detail == null) {
            finalTaskCounter += 2;
        } else {
            this.masterQueue.add(detail);
            incomingTaskCounter++;
        }
    }

    private void checkTaskState() {

        if ((incomingTaskCounter + 1) == finalTaskCounter) {
            for (Worker worker : this.workerList) {
                worker.release();
            }
        }
        System.out.println("final counter = task counter");
        this.taskCompleted = true;
    }

    public void execute(QuickSort taskObject, int workerNumber) {

        SortDetail initialDetail = new SortDetail(0, (taskObject.getArrayLength() -1), taskObject);
        this.masterQueue.add(initialDetail);

        for (int i = 0; i < workerNumber; i++) {
            Worker worker = new Worker(this); // creates and runs new threads.
            System.out.println("Worker #" + (i + 1) + " created.");
            workerList.add(worker);
        }
        start(); // runs the master thread.

        /*for (Worker worker : workerList) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("The task was completed successfully.");
            }
        }*/
    }
}
