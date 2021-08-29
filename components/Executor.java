package components;

import java.util.Stack;
import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import errors.InterpreterError;
class Command {
    Stack<String> name;
    Stack<String> operators;
    Stack<Object> operands;
    public Command(Stack<String> name){
        this.name = name;
    }
    // public Command(String name, ArrayList<String> operators){
    //     this.name = name;
    //     this.operators = operators;
    // }
    public Command(Stack<String> name, Stack<Object> operands){
        this.name = name;
        this.operands = operands;
    }

    public Command(Stack<String> name, Stack<String> operators, Stack<Object> operands){
        this.name = name;
        this.operators = operators;
        this.operands = operands;
    }

    public static boolean thereisSpaces(String str){
        str = str.trim();
        return str.matches("\\s+");
    }

    public static boolean varNameMaxCharacteres(String str){
        return str.matches(".{256}");
    }
}

public class Executor {

    public void execute(Command cmd){

    }

    public void preExecute(Command cmd){
        // System.out.println("Pre executing command");
        execute(cmd);
    }


    public Executor(String cmd){

        if(cmd.matches("^fim.*")){
            preExecute(new Command(
                new Stack<String>(){{ push("fim"); }}
            ));
        }
        else if(cmd.matches("^ler.*")){
            Matcher m = Pattern.compile("(ler)(\\s+)(.*)").matcher(cmd);
            final String var_name = m.group(3);
            if(Command.thereisSpaces(var_name))
                throw new InterpreterError("Erro de sintaxe no comando: " + cmd);
            if(Command.varNameMaxCharacteres(var_name))
                throw new InterpreterError("Variável " + var_name + " excede o limite de caracteres");

            preExecute(new Command(
                new Stack<String>(){{
                    push("ler");
                    push("store");
                }},
                new Stack<Object>(){{ push(var_name); }}
            ));            
        }
        else if(cmd.matches("^imprimir.*")){
            Matcher m = Pattern.compile("(imprimir)(\\s+)(.*)").matcher(cmd);
            final String var_name = m.group(3);
            if(Command.thereisSpaces(var_name))
                throw new InterpreterError("Erro de sintaxe no comando: " + cmd);
            if(Command.varNameMaxCharacteres(var_name))
                throw new InterpreterError("Variável " + var_name + " excede o limite de caracteres");

            preExecute(new Command(
                new Stack<String>(){{
                    push("imprimir");
                    push("load");
                }},
                new Stack<Object>(){{ push(var_name); }}
            ));   

        }
        else if(cmd.matches("^if.*")){
            Matcher m = Pattern.compile("(if)(\\s+)(\\w+)(\\s+)(.*)").matcher(cmd);
            final String logic = m.group(3);
            final String command = m.group(5);

            String[] composite_logic = logic.split("\\.((and)|(or)|(xor))\\.");
        }
        else{

        }
    }    
}
