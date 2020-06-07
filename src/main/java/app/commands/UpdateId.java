package app.commands;

import app.collection.WorkerCollection;
import app.collection.getQuery.GetByField;
import app.collection.worker.*;
import app.collection.worker.workerCollectionException.WorkerCollectionException;
import response.Response;
import response.Status;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

public final class UpdateId extends WorkerCollectionCommand {
    public UpdateId(HashMap<String, String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute() {

        try {
            //Integer id = Integer.parseInt(inputArguments.get(0));
            Integer id = Integer.parseInt(inputArguments.get("id"));
            Worker worker;
            GetByField getByField = new GetByField(Worker.class.getDeclaredField("id"), id);
            List<Worker> workers = workerCollection.executeGetQuery(getByField);
            worker = workers.get(0);
            worker.setName(inputArguments.get("name"));
            Double coordinateX = Double.parseDouble(inputArguments.get("coordinate x"));
            Integer coordinateY = Integer.parseInt(inputArguments.get("coordinate y"));
            worker.setCoordinates(new Coordinates(coordinateX, coordinateY));

            worker.setSalary(Long.parseLong(inputArguments.get("salary")));
            worker.setStartDate(ZonedDateTime.parse(inputArguments.get("startDate")));
            worker.setEndDate(LocalDate.parse(inputArguments.get("endDate")));
            worker.setStatus(app.collection.worker.Status.valueOf(inputArguments.get("status")));
            Double personWeight = Double.parseDouble(inputArguments.get("person weight"));
            Color hairColor = Color.valueOf(inputArguments.get("person hairColor"));
            Country nationality = Country.valueOf(inputArguments.get("person country"));
            worker.setPerson(new Person(personWeight, hairColor, nationality));

            workerCollection.update(worker);
            return new Response(Status.OK,"Worker обновлен успешно.");
        } catch (NoSuchFieldException | WorkerCollectionException |
                NumberFormatException | IndexOutOfBoundsException e) {
            return new Response(Status.BAD_REQUEST, "Элемента с таким id не существует");
        }

    }
}
