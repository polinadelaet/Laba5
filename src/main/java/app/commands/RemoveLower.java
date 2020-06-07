package app.commands;

import app.collection.WorkerCollection;
import app.collection.getQuery.GetLower;
import app.collection.worker.*;
import app.collection.worker.workerCollectionException.WorkerCollectionException;
import response.Response;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class RemoveLower extends WorkerCollectionCommand {
    public RemoveLower(HashMap<String, String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute(){

        String name = inputArguments.get("name");
        Double x = Double.parseDouble(inputArguments.get("coordinate x"));
        Integer y = Integer.parseInt(inputArguments.get("coordinate y"));
        Coordinates coordinates = new Coordinates(x,y);
        long salary = Long.parseLong(inputArguments.get("salary"));
        ZonedDateTime startDate = ZonedDateTime.parse(inputArguments.get("startDate"));
        LocalDate endDate = LocalDate.parse(inputArguments.get("endDate"));
        app.collection.worker.Status status = app.collection.worker.Status.valueOf(inputArguments.get("status"));
        Double weight = Double.parseDouble(inputArguments.get("person weight"));
        Color hairColor = Color.valueOf(inputArguments.get("person hairColor"));
        Country nationality = Country.valueOf(inputArguments.get("person country"));
        Person person = new Person(weight, hairColor, nationality);
        Worker worker = new Worker(coordinates);
        try {
            GetLower getLower = new GetLower(worker);

            List<Worker> resultWorkers = workerCollection.executeGetQuery(getLower);
            if (resultWorkers.size() != 0){
                for (Worker targetWorker: resultWorkers){
                    workerCollection.remove(targetWorker);
                }
                return new Response(response.Status.OK, "Элементы коллекции успешно удалены.");
            } return new Response(response.Status.BAD_REQUEST, "Элементы не были удалены, так как они все не меньше заданного");
        } catch (WorkerCollectionException e){
            return new Response(response.Status.BAD_REQUEST, e.getMessage());
        }
    }
}
