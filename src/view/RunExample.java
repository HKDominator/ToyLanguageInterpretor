package view;

import controller.IController;
import model.adt.exceptions.AppExceptions;

public class RunExample extends Command {
    private IController controller;
    boolean flag = true;


    public RunExample(String key, String description, IController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute(){
        try{
            controller.doAllSteps(flag);
            }catch(AppExceptions e){
            System.out.println(e.getMessage());
        }
    }
}