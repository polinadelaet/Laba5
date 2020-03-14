package app.collection.getQuery;

import app.collection.worker.Worker;

import java.util.ArrayList;
import java.util.List;

public class GetLower extends GetQuery {
    private final Worker targetWorker;

    public GetLower(Worker targetWorker) {
        this.targetWorker = targetWorker;
    }

    @Override
    public List<Worker> execute(List<Worker> workers) {
        List<Worker> result = null;
        for (Worker worker : workers) {
            result = new ArrayList<>();
            if (worker.compareTo(targetWorker) < 0) {
                result.add(worker);
            }
        }
        return result;
    }
}
