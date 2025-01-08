package masterworker;

import task.QuickSort;
import task.SortDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master extends Thread {

    private final Queue<SortDetail> masterQueue;
    private final List<Worker> workerList;
    private int incomingTaskCounter;
    private int finalTaskCounter;

    public Master() {
        this.masterQueue = new ConcurrentLinkedQueue<>();
        this.workerList = new ArrayList<>();
        this.incomingTaskCounter = 0;
        this.finalTaskCounter = 0;
    }

    @Override
    public void run() {

        int numberOfWorker = workerList.size();
        int counter = 0;

        while (true) {
            //TODO: change while loop condition to avoid infinity loop.
            SortDetail detail = this.masterQueue.remove();
            Worker worker = this.workerList.get(counter);
            worker.addToWorkerQueue(detail);

            if (counter == numberOfWorker) {
                counter = 0;
            } else {
                counter++;
            }
        }
    }

    protected void addToMasterQueue(SortDetail detail) {
        if (detail == null) {
            finalTaskCounter++;
        } else {
            this.masterQueue.add(detail);
            incomingTaskCounter++;
        }

    }

    private void checkCounter() {

        if ((incomingTaskCounter + 1) == finalTaskCounter) {
            for (Worker worker : this.workerList) {
                worker.release();
            }
        }
    }

    public void execute(QuickSort taskObject, int workerNumber) {

        SortDetail initialDetail = new SortDetail(0, taskObject.getArrayLength(), taskObject);
        this.masterQueue.add(initialDetail);

        for (int i = 0; i < workerNumber; i++) {
            Worker worker = new Worker(this);
            workerList.add(worker);
        }
        start();
        try {
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
