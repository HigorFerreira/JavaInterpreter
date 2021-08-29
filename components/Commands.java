package components;

import java.util.ArrayList;
import java.util.HashMap;

public class Commands{
    final HashMap<Integer, String> commands_str = new HashMap<Integer, String>();
    // final HashMap<Integer, Command> commands_obj = new HashMap<Integer, Command>();
    final ArrayList<Integer> line_nos = new ArrayList<Integer>();

    public Commands(){

    }

    public void add(Integer key, String value){
        line_nos.add(key);
        commands_str.put(key, value);
    }

    public String get(Integer key){
        return commands_str.get(key);
    }

    public int indexOf(int value){
        return line_nos.indexOf(value);
    }

    public boolean isSorted(){
        if(line_nos.size() > 1){
            for(int i = 1; i < line_nos.size(); i++)
                if(line_nos.get(i-1) >= line_nos.get(i))
                    return false;
        }
        return true;
    }

    public ArrayList<Integer> getLines(){
        return line_nos;
    }

    public HashMap<Integer, String> getCommands(){
        return commands_str;
    }
}