package components;

import errors.InterpreterError;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import components.Commands;

public class InterpreterFileHandler {

    File f;
    final Commands commands = new Commands();

    public InterpreterFileHandler(String[] args) throws Exception {
        try {
            try{
                f = new File(args[0]);
            }
            catch(IndexOutOfBoundsException e){
                JFileChooser fc = new JFileChooser();
                if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    final String path_name = fc.getSelectedFile().getAbsolutePath();
                    f = new File(path_name);
                }
                else{
                    throw new Exception("Arquivo não encontrado");
                }
            }

            Scanner reader = new Scanner(f);

            while(reader.hasNextLine()){
                final String command = reader.nextLine();
                final Pattern pattern = Pattern.compile("^(\\s*)(\\d+)(\\s+)(.*)$");
                Matcher matcher = pattern.matcher(command);
                if(matcher.matches()){
                    commands.add(
                        Integer.parseInt(matcher.group(2)),
                        matcher.group(4)
                    );
                }
                else {
                    reader.close();
                    throw new InterpreterError("Erro de sintaxe: \"" + command + "\" não é reconhecido como um commando");
                }
            }

            reader.close();

            if(!commands.isSorted()) throw new InterpreterError("Command line numbers out of ascending order");
        }
        catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado");
            throw new Exception("Arquivo não encontrado");
        }
    }

    public Commands getCommands(){
        return commands;
    }
}
