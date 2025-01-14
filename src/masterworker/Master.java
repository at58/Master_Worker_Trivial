package masterworker;

import task.TaskDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master extends Thread {

    private final List<Worker> workerList;
    private final int[] data;
    private final Queue<TaskDetail> masterQueue;

    public Master(int[] array) {
        this.masterQueue = new ConcurrentLinkedQueue<>();
        this.workerList = new ArrayList<>();
        this.data = array;
    }

    @Override
    public void run() {

        int numberOfWorker = (workerList.size() - 1);
        int scheduler = 0;

        while (true) {

            boolean newFollowUpReceived = false;
            for (Worker worker : this.workerList) {
                List<TaskDetail> followUps = worker.queryFollowUps();
                if (!followUps.isEmpty()) {
                    newFollowUpReceived = true;
                    this.masterQueue.addAll(followUps);
                }
            }
            if (! newFollowUpReceived) {
                boolean busyWorkers = false;
                for (Worker worker : this.workerList) {
                    if (worker.isBusy()) {
                        busyWorkers = true;
                    }
                }
                if (! busyWorkers) {
                    for (Worker worker : this.workerList) {
                        worker.release();
                    }
                    break; // break the while loop and effect this thread to terminate.
                } else {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }

            TaskDetail detail = this.masterQueue.poll();


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

    /*protected synchronized void addToMasterQueue(TaskDetail[] details) {

        System.out.println("*** " + Thread.currentThread().getName() + " holds the Lock.***");

        this.completedTaskDetails.add(details[0]);
        if (details.length == 1) {
            System.out.println("Created task count " + taskCounter.get());
            System.out.println("Completed task count " + this.completedTaskDetails.size());
            if (taskCounter.get() == completedTaskDetails.size()) {
                for (Worker worker : this.workerList) {
                    worker.release();
                }
                taskCompleted.set(true);
            }
        } else {
            this.masterQueue.add(details[1]);
            this.masterQueue.add(details[2]);
            taskCounter.addAndGet(2);
        }
    }*/

    protected int[] getData() {
        return this.data;
    }

    public void execute(int workerNumber) {

        TaskDetail initialDetail = new TaskDetail(0, (this.data.length - 1));
        this.masterQueue.add(initialDetail);

        // creates and runs worker-threads.
        for (int i = 1; i <= workerNumber; i++) {
            Worker worker = new Worker(this.data, i);
            workerList.add(worker);
        }
        System.out.println("Master Thread started...");
        start(); // runs the master thread.
    }
}