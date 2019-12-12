package cn.gydata.zckb.zckbinterfaceorder.vo;

import lombok.Data;

import javax.persistence.Entity;

/**
 * @ClassName UserVo
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-11-14 10:09
 * @VERSION 1.0
 **/
@Data
@Entity
public class UserVo {
    private  Long userId;
    private int isMember;
    private Long validityTime;
    private int isTrial;
    private int integration;
    private int grade;
    private int charm;
    private Long companyUserId;
    private int pricingPackageType;
    private String memberGroup;
    private Boolean isDredgeCompany;
    private Boolean isUpgradeMemberGroup;
    private Boolean isUpgradeOrAdd;
}
