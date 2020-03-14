package app.collection.worker.factory;

import app.collection.worker.*;
import app.collection.worker.idGeneratorException.IdGeneratorException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

public final class WorkersFactory {
    private final IdGenerator idGenerator;

    public WorkersFactory(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }
    public Worker create(String name,
                         Coordinates coordinates,
                         long salary,
                         ZonedDateTime startDate,
                         LocalDate endDate,
                         Status status,
                         Person person) throws WorkerCreationException {
        try {
            return new Worker(idGenerator.getId(),
                    name,
                    coordinates,
                    Date.from(Instant.now()),
                    salary,
                    startDate,
                    endDate,
                    status,
                    person);
        } catch (IdGeneratorException e) {
            throw new WorkerCreationException(e);
        }
    }
}
