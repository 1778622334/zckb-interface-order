package cn.gydata.zckb.zckbinterfaceorder.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValationUtils {

    private static final String mobileRegex = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

    public static boolean isMobile(String mobile){
        return match(mobile,mobileRegex);
    }

    private static boolean match(String str,String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
