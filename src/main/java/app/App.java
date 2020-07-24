package app;


import app.collection.WorkerCollection;
import app.collection.worker.IdGenerator;
import app.collection.worker.factory.WorkersFactory;
import app.collection.worker.loadingException.LoadingException;
import app.collection.worker.savingException.SavingException;
import app.controller.Controller;
import app.serverWork.Server;
import java.io.File;
import java.io.IOException;

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
                   System.out.println("Нет прав на чтение файла коллекции.");
                   System.exit(0);
               }

               workerCollection = WorkerCollection.load(file, workersFactory);

           }

           if (!file.exists()){
               workerCollection = new WorkerCollection(workersFactory);
           }
           Controller controller = new Controller(workerCollection);
           Server server = new Server(controller);
           server.start();

       } catch (LoadingException e) {
           System.out.println("Файл не найден. Относительный путь должен пыть ");
       } catch (SavingException e){
           System.out.println("ID воркеров не сохранены.");
       }
    }
}
