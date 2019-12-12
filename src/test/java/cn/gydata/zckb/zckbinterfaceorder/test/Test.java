package cn.gydata.zckb.zckbinterfaceorder.test;

import cn.gydata.zckb.zckbinterfaceorder.entity.order.OrderInfo;
import cn.gydata.zckb.zckbinterfaceorder.service.OrderInfoService;
import cn.gydata.zckb.zckbinterfaceorder.service.UserServerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName Test
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-11-24 11:09
 * @VERSION 1.0
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@RefreshScope
public class Test {
    @Value("${test.userIds}")
    private String userIds;
    @Autowired
    private OrderInfoService orderInfoService;
    @org.junit.Test
    public void test1(){
        List<OrderInfo> list = orderInfoService.getNoInvoiceOrder(23L);
        System.out.println(list.size());
    }

    @org.junit.Test
    public void test2(){
        System.out.println(userIds);
    }
}
