package cn.gydata.zckb.zckbinterfaceorder.handle;


import cn.gydata.zckb.zckbinterfaceorder.util.ConverterUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RequestHandle
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-11-18 14:19
 * @VERSION 1.0
 **/
public class RequestHandle {
    public static Map<String, Object> getPubParm(HttpServletRequest request){
        Map<String, Object> mapAppParms = new HashMap<>();
        try{

            Integer deviceType = ConverterUtils.toInt(request.getHeader("deviceType"),0) ;
            String diviceId = ConverterUtils.toString(request.getHeader("diviceId"));
            String channelId = ConverterUtils.toString(request.getHeader("channelId"));
            String versionCode = ConverterUtils.toString(request.getHeader("versionCode"));
            mapAppParms.put("deviceType",deviceType);
            mapAppParms.put("diviceId",diviceId);
            mapAppParms.put("channelId",channelId);
            mapAppParms.put("versionCode",versionCode);

            Long userId = ConverterUtils.toLong(request.getHeader("userId"),0) ;
            String  tokenKey = ConverterUtils.toString(request.getHeader("tokenKey"));
            Integer isLogin = ConverterUtils.toInt(request.getHeader("isLogin"));
            mapAppParms.put("userId",userId);
            mapAppParms.put("tokenKey",tokenKey);
            mapAppParms.put("isLogin",isLogin);

            Integer isMember = ConverterUtils.toInt(request.getHeader("isMember"));
            Long validityTime = ConverterUtils.toLong(request.getHeader("validityTime"),0) ;
            String  memberGroup = ConverterUtils.toString(request.getHeader("memberGroup"));
            mapAppParms.put("isMember",isMember);
            mapAppParms.put("validityTime",validityTime);
            mapAppParms.put("memberGroup",memberGroup);

        }catch (Exception e){
            e.printStackTrace();
        }
        return  mapAppParms;
    }
}
