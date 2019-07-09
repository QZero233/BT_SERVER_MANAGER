package com.nasa.bt.server.manager.commands;

import java.util.Map;

public interface ManagerCommand {
    Map<String,String> getParams();
    boolean process(Map<String,String> params);
    void after(boolean success);
}
