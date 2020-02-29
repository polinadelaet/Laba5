package app.collection.getQuery;

import app.collection.Worker;

import java.util.Collection;
import java.util.List;

//Паттерн Command
public abstract class GetQuery {
    public abstract Collection<Worker> processQuery(List<Worker> workers);
}
