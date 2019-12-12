package cn.gydata.zckb.zckbinterfaceorder.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @ClassName OrderUtils
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-11-16 09:53
 * @VERSION 1.0
 **/
public class OrderUtils {

    /**
     * UUID作为主订单订单号
     * @return
     */
    public static String produceOrderNo() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
//         0 代表前面补充0     
//         4 代表长度为4     
//         d 代表参数为正数型
        return  thisDate() + machineId+ String.format("%015d", hashCodeV);
    }

    /**
     * datetime+random作为子订单订单号
     * @return
     */
    public static String produceOrderNoByTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }


    public static String thisDate(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(date);
    }
}
