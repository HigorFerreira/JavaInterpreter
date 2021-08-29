import components.*;

import java.util.regex.Pattern;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Function;
import java.util.regex.Matcher;
import components.Commands;
import errors.InterpreterError;

import java.util.Collection;
import java.util.TreeMap;


class Utils {
    public static boolean thereisSpaces(String str){
        str = str.trim();
        return str.matches("\\s+");
    }

    public static boolean varNameMaxCharacteres(String str){
        return str.length() > 255;
    }

    public static boolean isInteger(String str){
        return str.matches("^\\d+$");
    }

    public static int getVarOrLiteral(String _var_or_literal, TreeMap<String, Integer> m){

        final Object var_or_literal = _var_or_literal.matches("\\d+") ? Integer.parseInt(_var_or_literal) : _var_or_literal;

        if(var_or_literal.getClass() == Integer.class)
            return (Integer)var_or_literal;
        else if(var_or_literal.getClass() == String.class){
            if(Utils.thereisSpaces((String)var_or_literal))
                throw new InterpreterError(var_or_literal + " não é uma variável válida");
            if(Utils.varNameMaxCharacteres((String)var_or_literal))
                throw new InterpreterError(var_or_literal + " excede o limite de caracteres");

            return m.get(var_or_literal);
        }
        else {
            throw new InterpreterError("Erro ao recuperar operando");
        }
    }

    public static int getVarOrLiteralOrArithmetic(String operand, TreeMap<String, Integer> memory){

        Matcher m = Pattern.compile("(\\w+)(\\s*)(.*?)(\\s*)(\\w+)").matcher(operand);
        if(m.matches()){
            String operator = m.group(3);
            int op1 = getVarOrLiteral(m.group(1), memory);
            int op2 = getVarOrLiteral(m.group(5), memory);
            switch(operator){
                case "+":
                    return op1 + op2;
                case "-":
                    return op1 - op2;
                case "*":
                    return op1 * op2;
                case "div":
                    return op1 / op2;
                case "mod":
                    return op1 % op2;
                default:
                    throw new InterpreterError("Operador " + operator + " não reconhecido");
            }
        }
        else {
            return getVarOrLiteral(operand, memory);
        }
    }
}

public class Interpreter {
    public static void main(String[] args) throws Exception{
        try {
            InterpreterFileHandler ifh = new InterpreterFileHandler(args);
            Commands cmds = ifh.getCommands();

            ArrayList<Integer> lines = cmds.getLines();
            HashMap<Integer, String> commands = cmds.getCommands();
            TreeMap<String, Integer> memory = new TreeMap<String, Integer>();

            for(int i = 0; i < lines.size()-1; ){
                final int line = lines.get(i);
                final String command = commands.get(line);

                if(command.matches("^fim.*")){
                    System.exit(0);
                }
                else if(command.matches("^ler.*")){
                    Matcher m = Pattern.compile("(ler)(\\s+)(.*)").matcher(command);
                    if(!m.matches()) throw new InterpreterError("Sintaxe incorreta do comando: " + command, line);
                    final String op = m.group(3);

                    if(Utils.thereisSpaces(op))
                        throw new InterpreterError("Erro de sintaxe no comando: " + command, line);
                    if(Utils.varNameMaxCharacteres(op))
                        throw new InterpreterError("Variável " + op + " excede o limite de caracteres", line);

                    Scanner sc = new Scanner(System.in);
                    String user_input = sc.nextLine();
                    if(!Utils.isInteger(user_input)){
                        sc.close();
                        throw new InterpreterError("Entrada inválida: " + user_input, line);
                    }
                    sc.close();

                    memory.put(op, Integer.parseInt(user_input));
                }
                else if(command.matches("^imprime.*")){
                    Matcher m = Pattern.compile("(imprime)(\\s+)(.*)").matcher(command);
                    if(!m.matches()) throw new InterpreterError("Sintaxe incorreta do comando: " + command, line);
                    final String op = m.group(3);

                    if(op.matches("\\d+")) System.out.println(op);
                    else {
                        if(Utils.thereisSpaces(op))
                            throw new InterpreterError("Erro de sintaxe no comando: " + command, line);
                        if(Utils.varNameMaxCharacteres(op))
                            throw new InterpreterError("Variável " + op + " excede o limite de caracteres", line);

                        System.out.println(memory.get(op));
                    }  
                }
                else if(command.matches("^goto.*")){
                    Matcher m = Pattern.compile("(goto)(\\s+)(\\d+)").matcher(command);
                    if(!m.matches()) throw new InterpreterError("Sintaxe incorreta do comando: " + command, line);
                    final int line_to_go = Integer.parseInt(m.group(3));
                    i = lines.indexOf(line_to_go);
                    continue;
                }
                else if(command.matches("^if.*")){
                    Matcher m = Pattern.compile("(if)(\\s+)(.*)(\\s+)(goto.*)").matcher(command);
                    if(!m.matches()) throw new InterpreterError("Sintaxe incorreta do comando: " + command, line);
                    final String logic = m.group(3).trim();
                    final String _goto = m.group(5);
        
                    m = Pattern.compile("(\\w+)(\\s+)(.*?)(\\s+)(\\w+)").matcher(logic);
                    if(!m.matches()) throw new InterpreterError("Erro na lógica do comando: " + command, line);
                    String operator = m.group(3);
                    int operand1 = Utils.getVarOrLiteral(m.group(1), memory);
                    int operand2 = Utils.getVarOrLiteral(m.group(5), memory);
                    boolean truth_value;

                    switch(operator){
                        case ".gt.":
                            truth_value = operand1 > operand2;
                            break;
                        case ".lt.":
                            truth_value = operand1 < operand2;
                            break;
                        case ".eq.":
                            truth_value = operand1 == operand2;
                            break;
                        case ".ge.":
                            truth_value = operand1 >= operand2;
                            break;
                        case ".le.":
                            truth_value = operand1 <= operand2;
                            break;
                        case ".neq.":
                            truth_value = operand1 != operand2;
                            break;
                        default:
                            throw new InterpreterError(operator + " não é um operador válido", line);
                    }

                    if(truth_value){
                        m = Pattern.compile("(goto)(\\s+)(\\d+)").matcher(_goto);
                        if(!m.matches()) throw new InterpreterError("Sintaxe incorreta do comando: " + _goto, line);
                        final int line_to_go = Integer.parseInt(m.group(3));
                        i = lines.indexOf(line_to_go);
                        continue;
                    }
                }
                else{

                    Matcher m = Pattern.compile("(\\w+)(\\s*)(=)(\\s*)(.*)").matcher(command);
                    if(m.matches()){
                        final String id_var = m.group(1);
                        final String right_operand = m.group(5);

                        if(Utils.thereisSpaces(id_var))
                            throw new InterpreterError("Erro de sintaxe no comando: " + command, line);
                        if(Utils.varNameMaxCharacteres(id_var))
                            throw new InterpreterError("Variável " + id_var + " excede o limite de caracteres", line);

                        memory.put(id_var, Utils.getVarOrLiteralOrArithmetic(right_operand, memory));
                    }
                    else {
                        throw new InterpreterError("Comando " + command + " não reconhecido", line);
                    }
        
                }

                i++;
            }
        }
        catch(InterpreterError e){
            System.out.println("Erro na linha: " + e.line);
            throw e;
        }
    }
}

