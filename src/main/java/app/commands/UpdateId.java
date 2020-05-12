package app.commands;

import app.collection.WorkerCollection;
import app.collection.getQuery.GetByField;
import app.collection.worker.*;
import app.collection.worker.workerCollectionException.WorkerCollectionException;
import response.Response;
import response.Status;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public final class UpdateId extends WorkerCollectionCommand {
    public UpdateId(List<String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute() {

        try {
            Integer id = Integer.parseInt(inputArguments.get(0));
            Worker worker;
            GetByField getByField = new GetByField(Worker.class.getDeclaredField("id"), id);
            List<Worker> workers = workerCollection.executeGetQuery(getByField);
            worker = workers.get(0);
            worker.setName(inputArguments.get(1));
            Double coordinateX = Double.parseDouble(inputArguments.get(2));
            Integer coordinateY = Integer.parseInt(inputArguments.get(3));
            worker.setCoordinates(new Coordinates(coordinateX, coordinateY));

            worker.setSalary(Long.parseLong(inputArguments.get(4)));
            worker.setStartDate(ZonedDateTime.parse(inputArguments.get(5)));
            worker.setEndDate(LocalDate.parse(inputArguments.get(6)));
            worker.setStatus(app.collection.worker.Status.valueOf(inputArguments.get(7)));
            Double personWeight = Double.parseDouble(inputArguments.get(8));
            Color hairColor = Color.valueOf(inputArguments.get(9));
            Country nationality = Country.valueOf(inputArguments.get(10));
            worker.setPerson(new Person(personWeight, hairColor, nationality));

            workerCollection.update(worker);
            return new Response(Status.OK,"Worker обновлен успешно.");
        } catch (NoSuchFieldException | WorkerCollectionException |
                NumberFormatException | IndexOutOfBoundsException e) {
            return new Response(Status.BAD_REQUEST, "Элемента с таким id не существует");
        }

    }
}
