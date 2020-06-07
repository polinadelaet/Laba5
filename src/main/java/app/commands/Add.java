package app.commands;

import app.collection.WorkerCollection;
import app.collection.worker.Color;
import app.collection.worker.Coordinates;
import app.collection.worker.Country;
import app.collection.worker.Person;
import app.collection.worker.workerCollectionException.WorkerCollectionException;
import response.Response;
import response.Status;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class Add extends WorkerCollectionCommand {

    public Add(HashMap<String, String> inputArguments, WorkerCollection workerCollection) {
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

        try {
            //workerCollection.add(inputArguments.get(name));
            workerCollection.add(name, coordinates, salary, startDate, endDate, status, person);
        } catch (WorkerCollectionException e) {
            return new Response(Status.INTERNAL_ERROR,"");
        }
        return new Response(Status.OK, "Продукт успешно добавлен в коллекцию.");
    }
}
