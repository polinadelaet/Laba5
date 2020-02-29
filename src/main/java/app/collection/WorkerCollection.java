package app.collection;

import app.collection.getQuery.GetQuery;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

//Паттерн Repository (Репозиторий)
public class WorkerCollection {
    private final List<Worker> workers;
    private final CollectionInfo collectionInfo;

    public WorkerCollection(){
       workers = new LinkedList<>();
       collectionInfo = new CollectionInfo(ZonedDateTime.now(),
                                           LinkedList.class,
                                           0);

//        for (Worker worker : workers) {
//            save(worker);
//        }
//        save(collectionInfo);
    }

    public void add() {

    }

    public void update() {

    }

    public void remove() {

    }

    public Collection<Worker> executeGetQuery(GetQuery getQuery) {
        //Не все элементы, а лишь для конркетного getQuery
        return getQuery.processQuery(workers);
    }
}
