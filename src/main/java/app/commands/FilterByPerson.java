package app.commands;

import app.collection.WorkerCollection;
import app.collection.getQuery.GetAll;
import app.collection.worker.Color;
import app.collection.worker.Country;
import app.collection.worker.Person;
import app.collection.worker.Worker;
import app.collection.worker.workerCollectionException.WorkerCollectionException;
import response.Response;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FilterByPerson extends WorkerCollectionCommand {
    public FilterByPerson(HashMap<String, String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute() {


        Double weight = Double.parseDouble(inputArguments.get("person weight"));
        Color hairColor = Color.valueOf(inputArguments.get("person hairColor"));
        Country nationality = Country.valueOf(inputArguments.get("person country"));

        Person person = new Person(weight, hairColor, nationality);
        try {
            GetAll getAll = new GetAll();
            List<Worker> resultWorkers = workerCollection.executeGetQuery(getAll);

            if (resultWorkers.size() != 0){
                String message = "Элементы коллекции, значения полей person которых равно заданным:" + System.lineSeparator();
                List<Worker> result = resultWorkers.stream().filter((s) -> weight.equals(s.getPerson().getWeight())).collect(Collectors.toList());
                for(Worker worker: result){
                    message += worker.toString() + System.lineSeparator();
                }

                return new Response(response.Status.OK, message);
            } return new Response(response.Status.BAD_REQUEST, "В коллекции нет элементов, значечние поля person которых равно " + person + ".");
        } catch (WorkerCollectionException e){
            return new Response(response.Status.BAD_REQUEST, e.getMessage());
        }
    }
}
