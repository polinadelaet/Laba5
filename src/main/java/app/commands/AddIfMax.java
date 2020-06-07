package app.commands;

import app.collection.WorkerCollection;
import app.collection.getQuery.GetMax;
import app.collection.worker.*;
import app.collection.worker.workerCollectionException.WorkerCollectionException;
import response.Response;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class AddIfMax extends WorkerCollectionCommand {
    public AddIfMax(HashMap<String, String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute() {

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
        Worker worker = new Worker(salary);
        try {
            GetMax getMax = new GetMax();
            List<Worker> resultWorkers = workerCollection.executeGetQuery(getMax);
            if (resultWorkers.isEmpty() || worker.compareTo(resultWorkers.get(0)) > 0) {
                workerCollection.add(name, coordinates, salary, startDate, endDate, status, person);
                return new Response(response.Status.OK, "Элемент успешно добавлен в коллекцию.");
            }

            return new Response(response.Status.BAD_REQUEST, "Элемент не был добавлен, так как он оказался меньше максимального.");
        } catch (WorkerCollectionException e){
            return new Response(response.Status.BAD_REQUEST,e.getMessage());
        }

    }
}
