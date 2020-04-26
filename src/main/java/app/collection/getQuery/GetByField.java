package app.collection.getQuery;

import app.collection.worker.Worker;
import app.collection.worker.workerCollectionException.WorkerCollectionException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GetByField extends GetQuery {
    private final Field targetField;
    private final Object value;

    public GetByField(Field targetField, Object value) {
        this.targetField = targetField;
        this.value = value;
    }

    @Override
    public List<Worker> execute(List<Worker> workers) throws WorkerCollectionException{
        try{
            List<Worker> result = new ArrayList<>();
            for (Worker worker: workers){
                targetField.setAccessible(true);
                if (targetField.get(worker).equals(value)) {
                    result.add(worker);
                }
                targetField.setAccessible(false);
            }
            return result;
        }
        catch (IllegalAccessException |  IllegalArgumentException e){
            throw new WorkerCollectionException(e);
        }

    }
}
