package app;


import app.collection.WorkerCollection;
import app.collection.worker.IdGenerator;
import app.collection.worker.factory.WorkersFactory;
import app.collection.worker.loadingException.LoadingException;
import app.collection.worker.savingException.SavingException;
import console.ConsoleWork;
import app.controller.Controller;
import server.Server;

import java.io.*;

public class App {
    private static final String PATH_TO_ID_GENERATOR = "./files/idGenerator";


    public static void main(String[] args) throws IOException {
       try {
           String path = System.getenv("WC_FILE");
           File file = new File(path);

           IdGenerator idGenerator = IdGenerator.createIdGenerator(PATH_TO_ID_GENERATOR);
           WorkersFactory workersFactory = new WorkersFactory(idGenerator);
           WorkerCollection workerCollection = null;


           if (file.exists()) {
               if (!file.canRead()){
                   throw new FileNotFoundException("Нет доступа к файлу.");
               }
               workerCollection = WorkerCollection.load(file, workersFactory);
           } else {
               throw new LoadingException("Файл не найден.");
           }

           Controller controller = new Controller(workerCollection);
           Server server = new Server(1000, 128, controller);
           server.start();
//           ConsoleWork consoleWork = new ConsoleWork(System.in, System.out, controller);
//           consoleWork.start();


       } catch (NullPointerException e){
           System.out.println("Нужно установить имя файла.");
        }catch (LoadingException | FileNotFoundException e) {
           System.out.println(e.getMessage());
       }catch (SavingException e){
           System.out.println("Айди не сохранены.");
       }

    }
}
