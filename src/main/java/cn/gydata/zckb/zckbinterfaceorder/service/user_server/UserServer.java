package cn.gydata.zckb.zckbinterfaceorder.service.user_server;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Service
@FeignClient(value = "user-auth")
public interface UserServer {

    @PostMapping("/userinfo/getUserAccountCompleteForService")
    Map<String, Object> getUserAccount(@RequestParam(value = "userId") Long userId);


    @PostMapping("/userinfo/updateUserAcountForService")
    boolean updateUserAccount(@RequestParam(value = "addValidityDays") Integer addValidityDays
            , @RequestParam(value = "companyUserId") Long companyUserId
            , @RequestParam(value = "memberGroup") String memberGroup
            , @RequestParam(value = "pricingPackageType") Integer pricingPackageType
            , @RequestParam(value = "shopId") Long shopId);
}
