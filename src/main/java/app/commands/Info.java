package app.commands;

public class Info implements Command {
    @Override
    public void execute(){
        System.out.println(WorkerCollecion);

    };
}
