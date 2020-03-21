package app.commands;

import app.response.Response;
import app.response.Status;

import java.util.List;

public final class Help extends Command {


    public Help(List<String> inputArguments) {
        super(inputArguments);
    }
    @Override
    public Response execute(){
        String message = "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)" + System.lineSeparator() +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении" + System.lineSeparator() +
                "add {element} : добавить новый элемент в коллекцию" + System.lineSeparator() +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному" + System.lineSeparator() +
                "remove_by_id id : удалить элемент из коллекции по его id" + System.lineSeparator() +
                "clear : очистить коллекцию" + System.lineSeparator() +
                "save : сохранить коллекцию в файл" + System.lineSeparator() +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме." + System.lineSeparator() +
                "exit : завершить программу (без сохранения в файл)" + System.lineSeparator() +
                "insert_at index {element} : добавить новый элемент в заданную позицию" + System.lineSeparator() +
                "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции" + System.lineSeparator() +
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный" + System.lineSeparator() +
                "count_by_end_date endDate : вывести количество элементов, значение поля endDate которых равно заданному" + System.lineSeparator() +
                "filter_by_person person : вывести элементы, значение поля person которых равно заданному" + System.lineSeparator() +
                "print_field_descending_end_date endDate : вывести значения поля endDate в порядке убывания";
        return new Response(Status.OK,message);

    }
}
