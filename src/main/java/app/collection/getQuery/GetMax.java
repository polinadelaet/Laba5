package app.collection.getQuery;

import app.collection.worker.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetMax extends GetQuery{
    @Override
    public List<Worker> execute(List<Worker> workers) {
        if (workers.isEmpty()){
            return new ArrayList<>();
        }

        Worker maxWorker = workers.get(0);
        for (Worker worker: workers){
            if (worker.compareTo(maxWorker) > 0){
                maxWorker = worker;
            }
        }
        List<Worker> result = new ArrayList<>();
        result.add(maxWorker);
        return result;
    }
}
