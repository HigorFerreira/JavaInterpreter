import components.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import components.Commands;

public class Interpreter {
    public static void main(String[] args) throws Exception{
        // String command = "10 a=0";
        // final Pattern regex = Pattern.compile("^(\\s*)(\\d+)(\\s+)(.*)$");
        // Matcher matcher = regex.matcher(command);
        // if(matcher.matches()){
        //     System.out.println(matcher.group(1));
        //     System.out.println(matcher.group(2));
        //     System.out.println(matcher.group(3));
        //     System.out.println(matcher.group(4));
        // }
        // else {
        //     System.out.println("Padrão não encontrado");
        // }

        InterpreterFileHandler ifh = new InterpreterFileHandler(args);
        Commands cmds = ifh.getCommands();
        System.out.println(cmds.getLines());
        System.out.println(cmds.getCommands());

        System.out.println("fim".matches("^[Ff]im.*"));
    }
}