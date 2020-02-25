package app.query.queryBuilder;
import app.console.ConsoleWork;
import app.query.Query;

public class AddQueryBuilder extends QueryBuilder{

   public AddQueryBuilder(ConsoleWork consoleWork){
       super(consoleWork);
   }

    @Override
    public Query create(){
       return new Query();
    }
}
