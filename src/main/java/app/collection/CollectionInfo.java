package app.collection;

import java.time.ZonedDateTime;
import java.util.Collection;

public class CollectionInfo {

    private ZonedDateTime creationDate;
    private Class<? extends Collection> collectionType;
    private int size;


    public CollectionInfo(ZonedDateTime creationDate,
                          Class<? extends Collection> collectionType,
                          int size) {
        this.creationDate = creationDate;
        this.collectionType = collectionType;
        this.size = size;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Class<? extends Collection> getCollectionType() {
        return collectionType;
    }

    public int getSize() {
        return size;
    }

    public void increaseSize() {
        size++;
    }

    public void decreaseSize() {
        if (size > 0) {
            size--;
        }
    }
}
