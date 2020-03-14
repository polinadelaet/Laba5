package app.commands;

import app.collection.WorkerCollection;

import java.util.List;

public abstract class WorkerCollectionCommand extends Command{

    protected final WorkerCollection workerCollection;

    public WorkerCollectionCommand(List<String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments);
        this.workerCollection = workerCollection;
    }
}
