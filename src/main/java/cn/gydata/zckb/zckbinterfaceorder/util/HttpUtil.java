package cn.gydata.zckb.zckbinterfaceorder.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class HttpUtil {
    private static final Log log = LogFactory.getLog(HttpUtil.class);

    /**
     *
     * HTTP协议GET请求方法
     */
    public static String httpMethodGet(String url, String gb) {
        if (null == gb || "".equals(gb)) {
            gb = "UTF-8";
        }
        StringBuffer sb = new StringBuffer();
        URL urls;
        HttpURLConnection uc = null;
        BufferedReader in = null;
        try {
            urls = new URL(url);
            uc = (HttpURLConnection) urls.openConnection();
            uc.setRequestMethod("GET");
            uc.connect();
            in = new BufferedReader(new InputStreamReader(uc.getInputStream(),
                    gb));
            String readLine = "";
            while ((readLine = in.readLine()) != null) {
                sb.append(readLine);
            }
            if (in != null) {
                in.close();
            }
            if (uc != null) {
                uc.disconnect();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (uc != null) {
                uc.disconnect();
            }
        }
        return sb.toString();
    }

    /**
     *
     * HTTP协议POST请求方法
     */
    public static String httpMethodPost(String url, String params, String gb) {
        if (null == gb || "".equals(gb)) {
            gb = "UTF-8";
        }
        StringBuffer sb = new StringBuffer();
        URL urls;
        HttpURLConnection uc = null;
        BufferedReader in = null;
        try {
            urls = new URL(url);
            uc = (HttpURLConnection) urls.openConnection();
            uc.setRequestMethod("POST");
            uc.setDoOutput(true);
            uc.setDoInput(true);
            uc.setUseCaches(false);
            uc.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            uc.connect();
            DataOutputStream out = new DataOutputStream(uc.getOutputStream());
            out.write(params.getBytes(gb));
            out.flush();
            out.close();
            in = new BufferedReader(new InputStreamReader(uc.getInputStream(),
                    gb));
            String readLine = "";
            while ((readLine = in.readLine()) != null) {
                sb.append(readLine);
            }
            if (in != null) {
                in.close();
            }
            if (uc != null) {
                uc.disconnect();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (uc != null) {
                uc.disconnect();
            }
        }
        return sb.toString();
    }

    /**
     *
     * HTTP协议POST请求方法
     */
    public static String httpMethodPost(String url,
                                        TreeMap<String, String> paramsMap, String gb) {
        if (null == gb || "".equals(gb)) {
            gb = "UTF-8";
        }
        String params = null;
        if (null != paramsMap) {
            params = getParamStr(paramsMap);
        }
        System.out.println("组装业务请求参数:"+params);
        StringBuffer sb = new StringBuffer();
        URL urls;
        HttpURLConnection uc = null;
        BufferedReader in = null;
        try {
            urls = new URL(url);
            uc = (HttpURLConnection) urls.openConnection();
            uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            uc.setDoOutput(true);
            uc.setDoInput(true);
            uc.setRequestMethod("POST");
            uc.setUseCaches(false);
            uc.connect();
            DataOutputStream out = new DataOutputStream(uc.getOutputStream());
            out.write(params.getBytes(gb));
            out.flush();
            out.close();
            in = new BufferedReader(new InputStreamReader(uc.getInputStream(),
                    gb));
            String readLine = "";
            while ((readLine = in.readLine()) != null) {
                sb.append(readLine);
            }
            if (in != null) {
                in.close();
            }
            if (uc != null) {
                uc.disconnect();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (uc != null) {
                uc.disconnect();
            }
        }
        return sb.toString();
    }

    /**
     *
     * HTTP协议POST请求添加参数的封装方�?
     */
    private static String getParamStr(TreeMap<String, String> paramsMap) {
        StringBuilder param = new StringBuilder();
        for (Iterator<Map.Entry<String, String>> it = paramsMap.entrySet()
                .iterator(); it.hasNext();) {
            Map.Entry<String, String> e = it.next();
            param.append("&").append(e.getKey()).append("=")
                    .append(e.getValue());
        }
        return param.toString().substring(1);
    }

}
