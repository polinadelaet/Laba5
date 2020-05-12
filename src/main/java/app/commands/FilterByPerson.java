package app.commands;

import app.collection.WorkerCollection;
import app.collection.getQuery.GetAll;
import app.collection.worker.Color;
import app.collection.worker.Country;
import app.collection.worker.Person;
import app.collection.worker.Worker;
import app.collection.worker.workerCollectionException.WorkerCollectionException;
import response.Response;

import java.util.Iterator;
import java.util.List;

public final class FilterByPerson extends WorkerCollectionCommand {
    public FilterByPerson(List<String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute() {

        Iterator<String> iterator = inputArguments.iterator();
        Double weight = Double.parseDouble(iterator.next());
        Color hairColor = Color.valueOf(iterator.next());
        Country nationality = Country.valueOf(iterator.next());
        Person person = new Person(weight, hairColor, nationality);
        try {
            GetAll getAll = new GetAll();
            List<Worker> resultWorkers = workerCollection.executeGetQuery(getAll);

            if (resultWorkers.size() != 0){
                String message = "Элементы коллекции, значения полей person которых равно заданным:" + System.lineSeparator();
                for (Worker worker: resultWorkers){

                    if (weight.equals(worker.getPerson().getWeight())){
                        message += worker.toString() + System.lineSeparator();
                    }
                }
                return new Response(response.Status.OK, message);
            } return new Response(response.Status.BAD_REQUEST, "В коллекции нет элементов, значечние поля person которых равно " + person + ".");
        } catch (WorkerCollectionException e){
            return new Response(response.Status.BAD_REQUEST, e.getMessage());
        }
    }
}
