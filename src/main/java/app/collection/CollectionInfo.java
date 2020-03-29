package app.collection;

import app.collection.worker.xmlAdapters.ClassAdapter;
import app.collection.worker.xmlAdapters.ZonedDateTimeFormatter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.ZonedDateTime;
import java.util.Collection;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "collectionInfo")
@XmlRootElement
public final class CollectionInfo {
    @XmlJavaTypeAdapter(ZonedDateTimeFormatter.class)
    private ZonedDateTime creationDate;
    @XmlJavaTypeAdapter(ClassAdapter.class)
    private Class<? extends Collection> collectionType;
    private int size;

    public CollectionInfo() {}

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
