package components;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.HashMap;
import java.util.ArrayList;

class Command {

    class Operand extends HashMap<String, String> {
        public Operand(String type, String value){
            this.put(type, value);
        }
    }
    class Expression extends HashMap<String, ArrayList<Operand>> {
        public Expression(String operator, ArrayList<Operand> operands){
            this.put(operator, operands);
        }
    }

    String cmd_name;
    ArrayList<Expression> expressions;

    public Command(){}
    public Command(String cmd_name){
        this.cmd_name = cmd_name;
    }
    public Command(String cmd_name, ArrayList<Expression> expressions){
        this.cmd_name = cmd_name;
        this.expressions = expressions;
    }

    
}

public class CommandAnalyser {
    public CommandAnalyser(){
    }

    public static Command analise(String cmd){

        if(cmd.matches("^fim.*")){
            return new Command("fim");
        }
        else if(cmd.matches("^ler.*")){

        }
        else if(cmd.matches("^imprimir.*")){

        }
        else if(cmd.matches("^if")){

        }
        else{

        }

        return new Command();
    }
}
