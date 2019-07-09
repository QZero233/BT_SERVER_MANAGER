package com.nasa.bt.server.manager.utils;

import java.util.UUID;

/**
 * Created by Administrator on 2018/4/23 0023.
 */

public class UUIDUtils {
    public static String getRandomUUID(){
        UUID uuid=UUID.randomUUID();
        return uuid.toString();
    }
}
