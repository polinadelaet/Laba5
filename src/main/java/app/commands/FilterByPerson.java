package app.commands;

import app.collection.WorkerCollection;
import app.collection.getQuery.GetByPerson;
import app.collection.worker.Color;
import app.collection.worker.Country;
import app.collection.worker.Person;
import app.collection.worker.Worker;
import app.collection.worker.workerCollectionException.WorkerCollectionException;
import app.response.Response;

import java.util.Iterator;
import java.util.List;

public class FilterByPerson extends WorkerCollectionCommand {
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
            GetByPerson getByPerson = new GetByPerson(person);
            List<Worker> resultWorkers = workerCollection.executeGetQuery(getByPerson);
            if (resultWorkers != null){
                String message = "Элементы коллекции, значение поля person которых равно " + person + ":" + System.lineSeparator();
                for (Worker worker: resultWorkers){
                    message += worker.toString() + System.lineSeparator();
                }
                return new Response(app.response.Status.OK, message);
            } return new Response(app.response.Status.BAD_REQUEST, "В коллекции нет элементов, значечние поля person которых равно " + person + ".");
        } catch (WorkerCollectionException e){
            return new Response(app.response.Status.BAD_REQUEST, e.getMessage());
        }
    }
}
