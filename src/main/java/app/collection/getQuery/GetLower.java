package app.collection.getQuery;

import app.collection.worker.Worker;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetLower extends GetQuery {
    private final Worker targetWorker;

    public GetLower(Worker targetWorker) {
        this.targetWorker = targetWorker;
    }

    @Override
    public List<Worker> execute(List<Worker> workers) {
        List<Worker> result;
        /*for (Worker worker : workers) {
            if (worker.compareTo(targetWorker) < 0) {
                result.add(worker);
            }
        }*/

        result = workers.stream().filter((s) -> s.compareTo(targetWorker) < 0).collect(Collectors.toList());
        return result;
    }
}
