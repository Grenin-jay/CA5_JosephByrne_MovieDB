package org.example.DTOs;

public class Command
{
    String command;
    Object param;


    public Command(String command, Object param) {
        this.command = command;
        this.param = param;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }
}