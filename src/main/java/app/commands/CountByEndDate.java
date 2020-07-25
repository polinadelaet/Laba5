package app.commands;

import app.collection.WorkerCollection;
import app.collection.getQuery.GetByField;
import app.collection.getQuery.GetQuery;
import app.collection.worker.Worker;
import app.collection.worker.workerCollectionException.WorkerCollectionException;
import response.Response;
import response.Status;

import java.time.LocalDate;
import java.util.List;

public final class CountByEndDate extends WorkerCollectionCommand {
    public CountByEndDate(List<String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute() {
        LocalDate endDate = LocalDate.parse(inputArguments.get(0));

        try {
            GetQuery getByField = new GetByField(Worker.class.getDeclaredField("endDate"), endDate);
            List<Worker> resultWorkers = workerCollection.executeGetQuery(getByField);
            int count = resultWorkers.size();
            return new Response(Status.OK, "Количество элементов, поле endDate которых равно " + endDate + ": " + count + ".");
        } catch (WorkerCollectionException e){
            return new Response(Status.BAD_REQUEST, e.getMessage());
        }  catch (NoSuchFieldException e) {
            return new Response(Status.INTERNAL_SERVER_ERROR, "Внутрення ошибка сервера. Держи червя.");
        }
    }
}
