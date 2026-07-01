package br.com.senac.team;

public class Error {

    private String message;
    private String stack;

    public Error(String message, String stack) {
        this.message = message;
        this.stack = stack;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }
}
