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
import java.io.File;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

public final class WorkerCollection {
    private List<Worker> workers;
    private CollectionInfo collectionInfo;
    private WorkersFactory workersFactory;

    public WorkerCollection(WorkerCollectionDTO workerCollectionDTO,
                            WorkersFactory workersFactory) {
        workers = workerCollectionDTO.workers;
        collectionInfo = workerCollectionDTO.collectionInfo;
        this.workersFactory = workersFactory;
    }

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
            workers.add(index, worker);
        } catch (IndexOutOfBoundsException | WorkerCreationException e) {
            throw new WorkerCollectionException("По этому индексу нельзя добавить элемент.");
        }
    }

    public void update(Worker targetWorker) throws WorkerCollectionException {
        Worker currentWorker = null;
        for (Worker worker: workers){
            if (worker.getId() == targetWorker.getId()){
                currentWorker = worker;
                break;
            }
        }
        workers.remove(currentWorker);
        workers.add(targetWorker);
    }

    public void removeAll() {
        workers.clear();
    }

//TODO

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
     //  workers.remove(worker);
    }

    public CollectionInfo getCollectionInfo() {
        return collectionInfo;
    }

    public List<Worker> executeGetQuery(GetQuery getQuery) throws WorkerCollectionException{
        return getQuery.execute(workers);
    }

    public void save (File file) throws SavingException {
        WorkerCollectionDTO workerCollectionDTO = new WorkerCollectionDTO();
        workerCollectionDTO.collectionInfo = collectionInfo;
        workerCollectionDTO.workers = workers;
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(WorkerCollectionDTO.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(workerCollectionDTO, file);
        } catch (javax.xml.bind.JAXBException | IllegalArgumentException e ){
           e.printStackTrace();
            //throw new SavingException("Ошибка сохранения в файл.");
        }
    }

    public static WorkerCollection load(File file, WorkersFactory workersFactory) throws LoadingException {
        WorkerCollectionDTO workerCollectionDTO;
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(WorkerCollectionDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            workerCollectionDTO = (WorkerCollectionDTO) unmarshaller.unmarshal(file);
        } catch (javax.xml.bind.JAXBException | IllegalArgumentException e){
            throw new LoadingException("Ошибка выгрузки коллекции из файла");
        }

        return new WorkerCollection(workerCollectionDTO, workersFactory);
    }
}
