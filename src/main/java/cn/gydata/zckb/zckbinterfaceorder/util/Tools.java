/**
 * @ClassName: Tools.java
 * @author For Qiyi
 * @version V1.0
 * @Date 2019-03-11 下午13:12:32
 * @Description: TODO通用工具类
 */

package cn.gydata.zckb.zckbinterfaceorder.util;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

@Service
public class Tools {
    /**
     * 字符串非空
     * @param str
     * @return
     */
    public static String stringIsNull(String str){
        if(str == null){
            return "";
        }else{
            return str;
        }
    }


    /**
     * 解析.properties文件获取配置
     * @param filePath
     * @param key
     * @return
     */
    public static String getProperty(String filePath, String key) {
        Properties props = new Properties();
        try {
//            InputStream in = new Tools().getClass().getClassLoader().getResourceAsStream(filePath);
//            props.load(in);
            InputStream in = new Tools().getClass().getClassLoader().getResourceAsStream(filePath);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in,"UTF-8"));//解决读取properties文件中产生中文乱码的问题
            props.load(bf);
            String value = props.getProperty(key);

            return value;
        } catch (Exception e) {

            System.err.println(e);
            return null;
        }
    }
}
