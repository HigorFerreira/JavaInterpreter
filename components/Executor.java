package components;

public class Executor {

    public void execute(){

    }

    public void preExecute(){
        System.out.println("Pre executing command");
        execute();
    }


    public Executor(){

        preExecute();
    }    
}
