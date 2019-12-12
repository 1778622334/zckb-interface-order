package cn.gydata.zckb.zckbinterfaceorder.service.shop_server;

import cn.gydata.zckb.zckbinterfaceorder.vo.UserVo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
@Service
@FeignClient(value = "shop")
public interface ShopServer {

    @PostMapping("/server/shop/isUserPayShop")
    Map<String, Object> isUserPayShop(@RequestParam(value = "deviceType") Integer deviceType,@RequestParam(value = "shopId") Long shopId,@RequestBody UserVo userVo);

    @PostMapping("/server/shop/countUserBuyShopPrice")
    JSONObject countUserBuyShopPrice(@RequestParam(value = "shopId") Long shopId, @RequestBody  UserVo userVo, @RequestParam(value = "deviceType") Integer deviceType, @RequestParam(value = "vipType") Integer vipType, @RequestParam(value = "memberGroup") String memberGroup);
}
