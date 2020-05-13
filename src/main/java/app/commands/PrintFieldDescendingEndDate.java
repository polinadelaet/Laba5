package app.commands;

import app.collection.WorkerCollection;
import app.collection.getQuery.GetAll;
import app.collection.getQuery.GetQuery;
import app.collection.worker.Worker;
import app.collection.worker.workerCollectionException.WorkerCollectionException;
import response.Response;
import response.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class PrintFieldDescendingEndDate extends WorkerCollectionCommand {
    public PrintFieldDescendingEndDate(List<String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute() {

        try {
            GetQuery getAll = new GetAll();
            List<Worker> resultWorkers = workerCollection.executeGetQuery(getAll);


            if (resultWorkers.size() != 0) {
                List<Worker> endDates = resultWorkers.stream().sorted().collect(Collectors.toList());
                return new Response(Status.OK,endDates.toString());
            } return new Response(Status.BAD_REQUEST, "В коллекции нет элементов.");

        } catch (WorkerCollectionException e){
            return new Response(response.Status.BAD_REQUEST, e.getMessage());
        }


    }
}
