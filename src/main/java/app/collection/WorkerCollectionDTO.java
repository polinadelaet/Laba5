package app.collection;

import app.collection.worker.Worker;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlType(name = "workerCollectionDTO")
@XmlAccessorType(XmlAccessType.FIELD)
public final class WorkerCollectionDTO {
    @XmlElementWrapper(name = "workers")
    public List<Worker> workers;
    public CollectionInfo collectionInfo;
}
