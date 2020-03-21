package app.commands;

import app.collection.WorkerCollection;
import app.collection.getQuery.GetByField;
import app.collection.worker.Color;
import app.collection.worker.Country;
import app.collection.worker.Person;
import app.collection.worker.Worker;
import app.collection.worker.workerCollectionException.WorkerCollectionException;
import app.response.Response;
import app.response.Status;

import java.util.Iterator;
import java.util.List;

public final class FilterByPerson extends WorkerCollectionCommand {
    public FilterByPerson(List<String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute() {



        Iterator<String> iterator = inputArguments.iterator();
        iterator.next();

        Double weight = Double.parseDouble(iterator.next());
        Color hairColor = Color.valueOf(iterator.next());
        Country nationality = Country.valueOf(iterator.next());
        Person person = new Person(weight, hairColor, nationality);

        try {
            GetByField getByField = new GetByField(Worker.class.getDeclaredField("person"),person);
            List<Worker> resultWorkers = workerCollection.executeGetQuery(getByField);


            if (resultWorkers != null){
                String message = "Элементы коллекции, значение поля person которых равно " + person + ":" + System.lineSeparator();
                for (Worker worker: resultWorkers){
                    message += worker.toString() + System.lineSeparator();
                }
                return new Response(app.response.Status.OK, message);
            } return new Response(app.response.Status.BAD_REQUEST, "В коллекции нет элементов, значечние поля person которых равно " + person + ".");
        } catch (WorkerCollectionException e){
            return new Response(app.response.Status.BAD_REQUEST, e.getMessage());
        } catch (NoSuchFieldException e){
            return new Response(Status.INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервера. Держи червя.");
        }
    }
}
