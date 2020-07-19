package app;


import app.collection.WorkerCollection;
import app.collection.worker.IdGenerator;
import app.collection.worker.factory.WorkersFactory;
import app.collection.worker.loadingException.LoadingException;
import app.collection.worker.savingException.SavingException;
import app.connection.ServerConnection;
import app.console.ConsoleWork;
import app.controller.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class App {
    private static final String PATH_TO_ID_GENERATOR = "./files/idGenerator";
    private static final String PATH_TO_WORKERCOLLECTION = "./files/workerCollection";

    public static void main(String[] args) throws IOException {
       try {

           //String path = System.getenv("WC_FILE");
           File file = new File(PATH_TO_WORKERCOLLECTION);
           IdGenerator idGenerator = IdGenerator.createIdGenerator(PATH_TO_ID_GENERATOR);
           WorkersFactory workersFactory = new WorkersFactory(idGenerator);
           WorkerCollection workerCollection = null;

           if (file.exists()) {
               if (!file.canRead()){
                   System.out.println("Нет прав на чтение файла коллекции.");
                   System.exit(0);
               }
               workerCollection = WorkerCollection.load(file, workersFactory);
           }
           if (!file.exists()){
               workerCollection = new WorkerCollection(workersFactory);
           }

           Controller controller = new Controller(workerCollection);
           ServerConnection serverConnection = new ServerConnection(controller);
           ConsoleWork consoleWork = new ConsoleWork(System.in, System.out, serverConnection);
           consoleWork.start();

       } catch ( LoadingException e) {
           System.out.println("Файл не найден.");
       } catch (SavingException e){
           System.out.println("ID воркеров не сохранены.");
       }
    }
}
