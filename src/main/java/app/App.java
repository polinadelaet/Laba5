package app;


import app.collection.WorkerCollection;
import app.collection.worker.IdGenerator;
import app.collection.worker.factory.WorkersFactory;
import app.collection.worker.loadingException.LoadingException;
import app.collection.worker.savingException.SavingException;
import app.console.ConsoleWork;
import app.controller.Controller;

import java.io.*;

public class App {
    public static void main(String[] args) {
       try {
           IdGenerator idGenerator = new IdGenerator();
           WorkersFactory workersFactory = new WorkersFactory(idGenerator);
           WorkerCollection workerCollection = new WorkerCollection(workersFactory);
           String path = System.getenv("переменная окружения");
           File file = new File(path);
           Controller controller = new Controller(workerCollection.load(file));
           ConsoleWork consoleWork = new ConsoleWork(System.in, System.out, controller);

           while (true) {
               consoleWork.start();
           }
       } catch ( LoadingException | SecurityException e) {
           System.out.println("Файл не найден.");
       } catch (SavingException e){
           System.out.println("Айди не сохранены.");
       }
    }
        //TODO: создать здесь объект Console и запустить его бесконечный цикл
}
