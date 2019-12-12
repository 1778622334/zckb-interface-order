package cn.gydata.zckb.zckbinterfaceorder.handle;


import net.sf.json.JSONObject;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandle {
    /**
     * 请求成功响应数据格式封装
     *
     * @param obj
     * @param resultType (0:数据是否存在返回字段dataIsNot:0：不存在,1：存在 1:返回列表 2:返回单个对象 3:返回java.lang 4列表与对象组合的对象 )
     * @return
     */
    public static Map<String, Object> Success(Object obj, int resultType, Integer curPage, Integer pageCount) {
        //userIsLogin =(userIsLogin==0&&isNeedLogin)?0:1;
        Map<String, Object> map = new HashMap();
        switch (resultType) {
            case 0:
                if (obj instanceof List) {
                    if (((List) obj).size() > 0) {
                        map.put("msg", "200");
                        map.put("msgBox", "查询到数据为List,数据数量：" + ((List) obj).size());
                        map.put("dataIsNot", 1);
                        map.put("responseTime", new Date().getTime());
                    } else {
                        map.put("msg", "200");
                        map.put("msgBox", "查询到数据为List,数据数量：0");
                        map.put("dataIsNot", 0);
                        map.put("responseTime", new Date().getTime());
                    }
                } else if (obj instanceof Map) {
                    if (obj != null) {
                        map.put("msg", "200");
                        map.put("msgBox", "查询到数据为Obj,数据存在");
                        map.put("dataIsNot", 1);
                        map.put("responseTime", new Date().getTime());
                    } else {
                        map.put("msg", "200");
                        map.put("msgBox", "查询到数据为Obj,数据不存在");
                        map.put("dataIsNot", 0);
                        map.put("responseTime", new Date().getTime());
                    }
                }

                break;
            case 1:
                if (obj == null)
                    return ResponseHandle.Success("暂无数据", 3, curPage, pageCount);
                if (obj instanceof List) {
                    List<Map<String, Object>> data = (List<Map<String, Object>>) obj;
                    map.put("msg", "200");
                    map.put("msgBox", "查询到数据为List,数据数量：" + data.size());
                    if (curPage != null && curPage != 0) map.put("CurPage", curPage);
                    if (pageCount != null && pageCount != 0) map.put("PageCount", pageCount);
                    map.put("responseTime", new Date().getTime());
                    map.put("PageContent", data);
                } else {
                    map.put("msg", "400");
                    map.put("msgBox", "查询的数据不是List，检查Handle返回数据格式");
                    map.put("responseTime", new Date().getTime());
                }
                break;
            case 2:
                if (obj == null)
                    return ResponseHandle.Success("暂无数据", 3, curPage, pageCount);
                if (obj instanceof Map) {
                    map.put("msg", "200");
                    map.put("msgBox", "查询的数据为Object");
                    map.put("responseTime", new Date().getTime());
                    map.put("JsonContent", obj);
                } else {
                    map.put("msg", "400");
                    map.put("msgBox", "查询的数据不是Object，检查Handle返回数据格式");
                    map.put("responseTime", new Date().getTime());
                }
                break;
            case 3://客户端提示
                if (obj instanceof String || obj instanceof Integer || obj instanceof Boolean) {
                    map.put("msg", "200");
                    map.put("msgBox", obj);
                    map.put("responseTime", new Date().getTime());
                }
                break;

        }

        return map;
    }

    public static Map<String, Object> Success(Object obj, int resultType) {
        return Success(obj, resultType, null, null);
    }

    public static Map<String, Object> Error(String errorMsg) {
        Map<String, Object> map = new HashMap();
        map.put("msg", "400");//出错
        map.put("msgBox", errorMsg);
        map.put("responseTime", new Date().getTime());
        return map;
    }


    /// <summary>
    /// 获取json字符串
    /// </summary>
    /// <param name="type"></param>
    /// <param name="message"></param>
    /// <param name="CurPage"></param>
    /// <param name="PageCount"></param>
    /// <param name="Content"></param>
    /// <param name="PageHtml"></param>
    /// <param name="otherContent"></param>
    /// <param name="htList"></param>
    /// <returns></returns>
    public static String getMessageJsonStr(Integer type, String message, Integer CurPage, int PageCount, String Content, String PageHtml, Map otherContent, List<Map> htList) {
        try {
            Map mapResult = new HashMap();
            mapResult.put("msg", type);
            mapResult.put("msgbox", message);
            mapResult.put("CurPage", CurPage);
            mapResult.put("PageCount", PageCount);
            mapResult.put("PageHtml", PageHtml);
            mapResult.put("Content", Content);
            mapResult.put("LoginState", 0);

            if (otherContent != null && otherContent.size() > 0) {
                mapResult.put("otherContent", otherContent);
            }

            if (htList != null && htList.size() > 0) {
                mapResult.put("PageContent", htList);
            }
            return JSONObject.fromObject(mapResult).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }


}
