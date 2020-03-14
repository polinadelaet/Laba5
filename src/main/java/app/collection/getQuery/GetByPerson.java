package app.collection.getQuery;

import app.collection.worker.Color;
import app.collection.worker.Country;
import app.collection.worker.Person;
import app.collection.worker.Worker;
import app.collection.worker.workerCollectionException.WorkerCollectionException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GetByPerson extends GetQuery {

    private final Person person;

    public GetByPerson(Person person) {
        this.person = person;
    }

    @Override
    public List<Worker> execute(List<Worker> workers) throws WorkerCollectionException {
        //TODO удалить к хуям и заменить в команде
        List<Worker> result = null;
        for (Worker worker: workers){
            if (worker.getPerson() == person){
                result = new ArrayList<>();
                result.add(worker);
            }
        }
        return result;
    }
}
