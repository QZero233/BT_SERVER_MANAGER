package com.nasa.bt.server.manager.test;

import com.nasa.bt.server.manager.utils.CodeUtils;
import org.junit.Test;

public class CodeTest {

    @Test
    public void genCodeTest(){
        for(int i=0;i<10;i++){
            System.out.println(CodeUtils.genRandomCode());
        }
    }

}
