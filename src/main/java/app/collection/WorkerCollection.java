package app.collection;

import app.collection.getQuery.GetQuery;
import app.collection.worker.Coordinates;
import app.collection.worker.Person;
import app.collection.worker.Status;
import app.collection.worker.Worker;
import app.collection.worker.factory.WorkerCreationException;
import app.collection.worker.factory.WorkersFactory;
import app.collection.worker.loadingException.LoadingException;
import app.collection.worker.savingException.SavingException;
import app.collection.worker.workerCollectionException.WorkerCollectionException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

//Паттерн Repository (Репозиторий)
@XmlRootElement
public final class WorkerCollection {
    private final List<Worker> workers;
    private final CollectionInfo collectionInfo;
    private final WorkersFactory workersFactory;

    public WorkerCollection(WorkersFactory workersFactory){
       workers = new LinkedList<>();
       collectionInfo = new CollectionInfo(ZonedDateTime.now(),
                                           LinkedList.class,
                                           0);
       this.workersFactory = workersFactory;
    }


    @Override
    public String toString() {
        return "WorkerCollection{" +
                "workers=" + workers +
                '}';
    }


    public void add(String name,
                    Coordinates coordinates,
                    long salary,
                    ZonedDateTime startDate,
                    LocalDate endDate,
                    Status status,
                    Person person) throws WorkerCollectionException {
        Worker worker;
        try {
            worker = workersFactory.create(name, coordinates, salary, startDate, endDate, status, person);
        } catch (WorkerCreationException e) {
            throw new WorkerCollectionException(e);
        }
        workers.add(worker);
    }

    public void addByIndex(String name,
                           Coordinates coordinates,
                           long salary,
                           ZonedDateTime startDate,
                           LocalDate endDate,
                           Status status,
                           Person person,
                           int index) throws WorkerCollectionException {
        Worker worker;
        try {
            worker = workersFactory.create(name, coordinates, salary, startDate, endDate, status, person);
        } catch (WorkerCreationException e) {
            throw new WorkerCollectionException(e);
        }
        workers.add(index, worker);
    }

    public void update(Worker targetWorker) throws WorkerCollectionException {
        for (Worker worker: workers){
            if (worker.getId() == targetWorker.getId()){
                workers.remove(worker);
                workers.add(targetWorker);
                continue;
            }
            throw new WorkerCollectionException("Элемента коллекции с id = " + targetWorker.getId() + " не существует.");
        }
    }

    public void removeAll() {
        workers.clear();
    }


    public void remove(Worker worker) throws WorkerCollectionException {
        Worker targetWorker = null;
        for (Worker currentWorker: workers) {
            if (currentWorker.equals(worker)) {

                targetWorker = currentWorker;
                break;
            }
        }

        if (targetWorker == null) {
            throw new WorkerCollectionException("Элемента с такими id не существует.");
        }
        workers.remove(targetWorker);
    }

    public CollectionInfo getCollectionInfo() {
        return collectionInfo;
    }

    public List<Worker> executeGetQuery(GetQuery getQuery) throws WorkerCollectionException{
        return getQuery.execute(workers);
    }

    public void save (File file) throws SavingException {
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(WorkerCollection.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(this, file);
        } catch (javax.xml.bind.JAXBException | IllegalArgumentException e ){
           throw new SavingException("Ошибка сохранения в файл.");
        }
    }

    public WorkerCollection load(File file) throws LoadingException {
        WorkerCollection workerCollection = null;
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(WorkerCollection.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            workerCollection = (WorkerCollection) unmarshaller.unmarshal(file);
        } catch (javax.xml.bind.JAXBException | IllegalArgumentException e){
            throw new LoadingException("Ошибка выгрузки коллекции из файла");
        }
        return workerCollection;
    }
}
