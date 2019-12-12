package cn.gydata.zckb.zckbinterfaceorder.service;

import cn.gydata.zckb.zckbinterfaceorder.entity.order.WxPayReturn;
import cn.gydata.zckb.zckbinterfaceorder.mapper.order.WxPayReturnMapper;
import org.springframework.stereotype.Service;

/**
 * @ClassName WxPayReturnService
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-11-20 09:26
 * @VERSION 1.0
 **/
@Service
public class WxPayReturnService extends BaseService<WxPayReturn>{
    WxPayReturnService(WxPayReturnMapper mapper) {this.mapper = mapper;}

    public boolean insertWxPayReturn(WxPayReturn wxPayReturn) {
        try{
            insert(wxPayReturn);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
