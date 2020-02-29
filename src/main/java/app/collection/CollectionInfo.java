package app.collection;

import app.commands.Command;
import app.commands.Help;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

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
