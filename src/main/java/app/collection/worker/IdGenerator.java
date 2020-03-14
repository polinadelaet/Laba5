package app.collection.worker;

import app.collection.worker.idGeneratorException.IdGeneratorException;
import app.collection.worker.savingException.SavingException;

import java.io.*;
import java.net.URL;
import java.util.*;

public class IdGenerator implements Serializable {
    private static final int MIN_ID = 1, MAX_ID = 10000;
    private static final String FILE_PATH = " ";

    private final Queue<Integer> idCollection;

    /**
     * Когда создаешь IdGenerator новый, не из файла.
     */
    public IdGenerator() throws SavingException{
        idCollection = new ArrayDeque<>();
        createIdCollection();

        ClassLoader classLoader = IdGenerator.class.getClassLoader();
        URL fileURL = classLoader.getResource("file");

        if (fileURL == null) {
            throw new SavingException(new FileNotFoundException());
        } else {
            String pathToFile = fileURL.getFile();
            File file = new File(pathToFile);
        }
    }

    private void createIdCollection() {
        for (int currentId = MIN_ID; currentId <= MAX_ID; currentId++) {
            idCollection.add(currentId);
        }
    }

    /**
     * Когда есть уже файл с сохранением пула id.
     */
    public static IdGenerator readIdGenerator(String filePath) throws SavingException {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(filePath)))){
            return (IdGenerator) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SavingException(e);
        }
    }

    public static void writeIdGenerator(String filePath, IdGenerator idGenerator) throws SavingException{
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(filePath)))){
             objectOutputStream.writeObject(idGenerator);
        } catch (IOException e) {
            throw new SavingException(e);
        }
    }

    public int getId() throws IdGeneratorException {
        if (idCollection.isEmpty()){
            throw new IdGeneratorException();
        }
        return idCollection.poll();
    }
}
