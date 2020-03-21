package app.commands;

import app.collection.WorkerCollection;
import app.response.Response;

import java.util.List;
//TODO сделать эту хуету, тут сериализация
public final class Save extends Command {
    public Save(List<String> inputArguments) {
        super(inputArguments);
    }

    @Override
    public Response execute() {
        //todo сделать сохранение воркера в файл и загрузку
        return null;
    }
}
