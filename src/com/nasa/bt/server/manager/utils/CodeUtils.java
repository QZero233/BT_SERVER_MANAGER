package com.nasa.bt.server.manager.utils;

import java.util.Random;

public class CodeUtils {

    /**
     * 生成一个符合 《NASA安全规范》（当然是我瞎编的） 的用户身份校验密码
     * 这个密码由 8位随机数字和2个减号随机组合形成
     * @return 密码
     */
    public static String genRandomCode(){
        StringBuffer codeStr=new StringBuffer();

        Random random=new Random();
        int countOfMin=0,countOfNumber=0;
        for(int i=0;i<10;i++){
            //随机生成一个int，如果是奇数这一位就是减号，如果是偶数就是数字
            if(random.nextInt()%2==1){
                if(countOfMin>=2){
                    //减号有2个，只能是数字了
                    codeStr.append(getRandomPositiveNumberLessThan10());
                    countOfNumber++;
                }else{
                    //减号
                    codeStr.append("-");
                    countOfMin++;
                }
            }else{
                if(countOfNumber>=8){
                    //数字有8个，只能是减号了
                    codeStr.append("-");
                    countOfMin++;
                }else{
                    //数字
                    codeStr.append(getRandomPositiveNumberLessThan10());
                    countOfNumber++;
                }
            }
        }

        return codeStr.toString();
    }

    public static int getRandomPositiveNumberLessThan10(){
        Random random=new Random();
        int num=Math.abs(random.nextInt())%10;
        return num;
    }
}
