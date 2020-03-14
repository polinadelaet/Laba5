package app.commands;

import app.collection.WorkerCollection;
import app.collection.getQuery.GetByField;
import app.collection.worker.Color;
import app.collection.worker.Coordinates;
import app.collection.worker.Country;
import app.collection.worker.Worker;
import app.collection.worker.workerCollectionException.WorkerCollectionException;
import app.response.Response;
import app.response.Status;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.List;

public class UpdateId extends WorkerCollectionCommand {
    public UpdateId(List<String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute() {
        Iterator<String> iterator = inputArguments.iterator();
        iterator.next();
        Integer id = Integer.parseInt(iterator.next());
        Worker worker;
        try {
            GetByField getByField = new GetByField(Worker.class.getDeclaredField("id"), id);
            List<Worker> workers = workerCollection.executeGetQuery(getByField);
            worker = workers.get(0);
        }catch (NoSuchFieldException | WorkerCollectionException e){
            return new Response(Status.INTERNAL_SERVER_ERROR, "держи червя");
        }
        for(int i=2; i<inputArguments.size(); i+=2){
            if (inputArguments.get(i).equals("name")){
                worker.setName(inputArguments.get(i+1));
            }

            if (inputArguments.get(i).equals("coordinate x")) {
                String stringX = inputArguments.get(i + 1);
                double x = Double.parseDouble(stringX);
                Coordinates coordinates = worker.getCoordinates();
                coordinates.setX(x);
            }
            if (inputArguments.get(i).equals("coordinate y")){
                String stringY = inputArguments.get(i+2);
                Integer y = Integer.parseInt(stringY);
                Coordinates coordinates = worker.getCoordinates();
                coordinates.setY(y);
            }

            if (inputArguments.get(i).equals("creationDate")){
                worker.setCreationDate(Date.valueOf(inputArguments.get(i+1)));
            }

            if (inputArguments.get(i).equals("salary")){
                worker.setSalary(Long.parseLong(inputArguments.get(i+1)));
            }

            if (inputArguments.get(i).equals("startDate")){
                worker.setStartDate(ZonedDateTime.parse(inputArguments.get(i+1)));
            }

            if (inputArguments.get(i).equals("endDate")){
                worker.setEndDate(LocalDate.parse(inputArguments.get(i+1)));
            }
            if (inputArguments.get(i).equals("Status")){
                worker.setStatus(app.collection.worker.Status.valueOf(inputArguments.get(i+1)));
            }

            if (inputArguments.get(i).equals("person weight")){
                worker.getPerson().setWeight(Double.parseDouble(inputArguments.get(i+1)));

            }

            if (inputArguments.get(i).equals("person hairColor")){
                worker.getPerson().setHairColor(Color.valueOf(inputArguments.get(i+2)));
            }

            if (inputArguments.get(i).equals("person country")){
                worker.getPerson().setNationality(Country.valueOf(inputArguments.get(i+4)));
            }
        }

        try {
            workerCollection.update(worker);
            return new Response(Status.OK,"Worker обновлен успешно.");
        } catch (WorkerCollectionException e) {
            return new Response(Status.BAD_REQUEST, "Элемента с таким id");
        }

    }
}
