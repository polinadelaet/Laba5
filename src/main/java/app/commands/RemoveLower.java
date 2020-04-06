package app.commands;

import app.collection.WorkerCollection;
import app.collection.getQuery.GetLower;
import app.collection.worker.*;
import app.collection.worker.workerCollectionException.WorkerCollectionException;
import app.response.Response;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.List;

public final class RemoveLower extends WorkerCollectionCommand {
    public RemoveLower(List<String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute(){

        Iterator<String> iterator = inputArguments.iterator();
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
            GetLower getLower = new GetLower(worker);
            List<Worker> resultWorkers = workerCollection.executeGetQuery(getLower);
            if (resultWorkers.size() != 0){
                for (Worker targetWorker: resultWorkers){
                    workerCollection.remove(targetWorker);
                }
                return new Response(app.response.Status.OK, "Элементы коллекции успешно удалены.");
            } return new Response(app.response.Status.BAD_REQUEST, "Элементы не были удалены, так как они все не меньше заданного");
        } catch (WorkerCollectionException e){
            return new Response(app.response.Status.BAD_REQUEST, e.getMessage());
        }
    }
}
