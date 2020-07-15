package app;


import app.collection.WorkerCollection;
import app.collection.worker.IdGenerator;
import app.collection.worker.factory.WorkersFactory;
import app.collection.worker.loadingException.LoadingException;
import app.collection.worker.savingException.SavingException;
import app.console.ConsoleWork;
import app.controller.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class App {
    private static final String PATH_TO_ID_GENERATOR = "./files/idGenerator";


    public static void main(String[] args) throws IOException {
       try {
           String path = System.getenv("WC_FILE");
           File file = new File(path);

           IdGenerator idGenerator = IdGenerator.createIdGenerator(PATH_TO_ID_GENERATOR);
           WorkersFactory workersFactory = new WorkersFactory(idGenerator);
           WorkerCollection workerCollection;


           if (file.exists()) {
               workerCollection = WorkerCollection.load(file, workersFactory);
           } else {
               workerCollection = new WorkerCollection(workersFactory);
           }

           Controller controller = new Controller(workerCollection);
           ConsoleWork consoleWork = new ConsoleWork(System.in, System.out, controller);

           consoleWork.start();
       } catch ( LoadingException | SecurityException e) {
           System.out.println("Файл не найден.");
       } catch (SavingException e){
           System.out.println("Айди не сохранены.");
           System.out.println("999");
       }
    }
}
