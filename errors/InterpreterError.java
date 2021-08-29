package errors;

public class InterpreterError extends Error {
    public int line;
    public InterpreterError(String error){
        super(error);
    }
    public InterpreterError(String error, int line){
        super(error);
        this.line = line;
    }
}

