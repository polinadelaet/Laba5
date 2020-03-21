package app.commands;

import app.collection.WorkerCollection;
import app.collection.getQuery.GetByField;
import app.collection.getQuery.GetMax;
import app.collection.worker.*;
import app.collection.worker.workerCollectionException.WorkerCollectionException;
import app.response.Response;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.List;

public final class AddIfMax extends WorkerCollectionCommand {
    public AddIfMax(List<String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute() {

        Iterator<String> iterator = inputArguments.iterator();
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
        Worker worker = new Worker(salary);
        try {
            GetMax getMax = new GetMax();
            List<Worker> resultWorkers = workerCollection.executeGetQuery(getMax);
            if (resultWorkers.isEmpty() || worker.compareTo(resultWorkers.get(0)) > 0) {
                workerCollection.add(name, coordinates, salary, startDate, endDate, status, person);
                return new Response(app.response.Status.OK, "Элемент успешно добавлен в коллекцию.");
            }

            return new Response(app.response.Status.BAD_REQUEST, "Элемент был не добавлен, так как он оказался меньше максимального.");
        } catch (WorkerCollectionException e){
            return new Response(app.response.Status.BAD_REQUEST,e.getMessage());
        }

    }
}
