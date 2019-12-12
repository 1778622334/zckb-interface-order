package cn.gydata.zckb.zckbinterfaceorder.service;

import cn.gydata.zckb.zckbinterfaceorder.entity.order.AliPayReturn;
import cn.gydata.zckb.zckbinterfaceorder.mapper.order.AliPayReturnMapper;
import org.springframework.stereotype.Service;

/**
 * @ClassName AliPayReturnService
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-11-20 09:30
 * @VERSION 1.0
 **/
@Service
public class AliPayReturnService extends BaseService<AliPayReturn> {

    AliPayReturnService(AliPayReturnMapper mapper) {this.mapper = mapper;}

    public boolean insertAliPayReturn(AliPayReturn aliPayReturn){
        try{
            insert(aliPayReturn);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
