package app.collection.getQuery;

import app.collection.worker.Worker;

import java.util.List;

public final class GetAll extends GetQuery {
    @Override
    public List<Worker> execute(List<Worker> workers) {
        return workers;
    }
}
