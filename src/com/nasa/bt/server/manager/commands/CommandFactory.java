package com.nasa.bt.server.manager.commands;

public class CommandFactory {

    public static ManagerCommand getCommand(String action){
        if(action.equalsIgnoreCase("addUser"))
            return new AddUserCommand();
        return null;
    }

}
