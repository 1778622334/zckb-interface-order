package cn.gydata.zckb.zckbinterfaceorder.service;

import cn.gydata.zckb.zckbinterfaceorder.service.user_server.UserServer;
import cn.gydata.zckb.zckbinterfaceorder.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName UserServerService
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-11-18 11:55
 * @VERSION 1.0
 **/
@Service
public class UserServerService {

    @Autowired
    private UserServer userServer;
    public UserVo getUserAccount(Long userId){
        UserVo userVo = new UserVo();
        Map<String, Object> result = userServer.getUserAccount(userId);
        Map<String, Object> userInfo = (Map<String, Object>) result.get("JsonContent");
        userVo.setUserId(userId);
        userVo.setCompanyUserId(Long.valueOf(String.valueOf(userInfo.get("companyuserid"))));
        userVo.setIsMember((Integer.valueOf(String.valueOf(userInfo.get("ismember")))));
        userVo.setIsTrial((Integer.valueOf(String.valueOf(userInfo.get("istrial")))));
        userVo.setIntegration((Integer.valueOf(String.valueOf(userInfo.get("integration")))));
        userVo.setCharm((Integer.valueOf(String.valueOf(userInfo.get("charm")))));
        userVo.setGrade((Integer.valueOf(String.valueOf(userInfo.get("grade")))));
        userVo.setValidityTime((Long.valueOf(String.valueOf(userInfo.get("validitytime")))));
        userVo.setPricingPackageType((Integer.valueOf(String.valueOf(userInfo.get("pricingpackagetype")))));
        userVo.setMemberGroup(userInfo.get("membergroup").toString());
        return userVo;
    }

}
