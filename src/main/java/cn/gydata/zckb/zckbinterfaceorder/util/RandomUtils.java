package cn.gydata.zckb.zckbinterfaceorder.util;

import java.util.Random;

public class RandomUtils {

    public static int getRandomNumber(int min,int max){
        return new Random().nextInt(max-min)+min;
    }
}
