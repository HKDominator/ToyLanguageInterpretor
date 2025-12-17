package view;

import controller.Controller;
import controller.IController;
import model.adt.exceptions.AppExceptions;
import model.statements.IStatement;
import repository.IRepo;
import repository.Repo;

public class RunExample extends Command {
    private IStatement statement;
    private static int idx = 0;
    boolean flag = true;


    public RunExample(String key, String description, IStatement controller) {
        super(key, description);
        this.statement = controller;
    }

    @Override
    public void execute(){
        try{
            IRepo repo = new Repo(this.statement, "output" + idx + ".txt");
            IController controller = new Controller(repo);
            idx++;
            controller.doAllSteps(flag);
        }catch(AppExceptions e){
            System.out.println(e.getMessage());
        }
    }
}