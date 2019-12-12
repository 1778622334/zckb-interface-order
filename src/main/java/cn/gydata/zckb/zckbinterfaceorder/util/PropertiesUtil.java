/**
 * @ClassName: Tools.java
 * @author For Qiyi
 * @version V1.0
 * @Date 2019-03-11 下午13:12:32
 * @Description: TODO通用工具类
 */


package cn.gydata.zckb.zckbinterfaceorder.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    /**
     * 字符串非空
     * @param str
     * @return
     */
    public static String stringIsNot(String str){
        if(str == null){
            return "";
        }else{
            return str;
        }
    }
    public static Integer integerIsNot(Integer i){
        if(i == null){
            return 0;
        }else{
            return i;
        }
    }

    public static void main(String[] args){
        System.out.println(PropertiesUtil.getProperty("jrzb.propertiesdb", "hbasedb","quorum"));
    }

    /**
     * 解析.properties文件获取配置
     * @param filePath
     * @param key
     * @return
     */
    public static String getProperty(String filePath, String key, String name) {
        String keys = key +"."+ name;
        Properties props = new Properties();
        try {
            InputStream in = new PropertiesUtil().getClass().getClassLoader().getResourceAsStream("config/"+filePath);
            props.load(in);
            String value = props.getProperty(keys);
            value =new String(value.getBytes("ISO-8859-1"),"UTF-8");
            return value;
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }
}
