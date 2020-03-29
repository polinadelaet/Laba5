package app.commands;

import app.collection.WorkerCollection;
import app.collection.worker.Color;
import app.collection.worker.Coordinates;
import app.collection.worker.Country;
import app.collection.worker.Person;
import app.collection.worker.workerCollectionException.WorkerCollectionException;
import app.response.Response;
import app.response.Status;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.List;

public final class Add extends WorkerCollectionCommand {

    public Add(List<String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }



    @Override
    public Response execute() {
        Iterator <String> iterator = inputArguments.iterator();
        iterator.next();
        String name = iterator.next();
        Double x = Double.parseDouble(iterator.next());
        Integer y = Integer.parseInt(iterator.next());
        Coordinates coordinates = new Coordinates(x,y);
        long salary = Long.parseLong(iterator.next());
        ZonedDateTime startDate = ZonedDateTime.parse(iterator.next());
        LocalDate endDate = LocalDate.parse(iterator.next());
        app.collection.worker.Status status = app.collection.worker.Status.valueOf(iterator.next());
        Double weight = Double.parseDouble(iterator.next());
        Color hairColor = Color.valueOf(iterator.next());
        Country nationality = Country.valueOf(iterator.next());
        Person person = new Person(weight, hairColor, nationality);

        try {
            workerCollection.add(name, coordinates, salary, startDate, endDate, status, person);
        } catch (WorkerCollectionException e) {
            return new Response(Status.INTERNAL_SERVER_ERROR,"");
        }
        return new Response(Status.OK, "Продукт успешно добавлен в коллекцию.");
    }
}
