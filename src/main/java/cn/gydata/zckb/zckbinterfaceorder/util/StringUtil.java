package cn.gydata.zckb.zckbinterfaceorder.util;

import java.util.UUID;

public class StringUtil {

    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    public static boolean useLoop(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }
}
