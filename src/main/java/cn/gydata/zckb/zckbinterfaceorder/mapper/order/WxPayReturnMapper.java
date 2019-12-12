package cn.gydata.zckb.zckbinterfaceorder.mapper.order;

import cn.gydata.zckb.zckbinterfaceorder.auth.BaseMapper;
import cn.gydata.zckb.zckbinterfaceorder.entity.order.WxPayReturn;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public interface WxPayReturnMapper extends BaseMapper<WxPayReturn> {
}