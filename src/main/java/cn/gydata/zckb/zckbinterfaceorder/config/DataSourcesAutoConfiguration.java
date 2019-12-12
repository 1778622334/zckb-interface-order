package cn.gydata.zckb.zckbinterfaceorder.config;

import cn.gydata.zckb.zckbinterfaceorder.config.mybatis.MyBatisZckbOrderConfigMysql;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;

/**
 * @ClassName DataSourcesAutoConfiguration
 * @Description
 *  配置分布式事务管理
 * @Author yangqiyi
 * @Date 2019-10-10 13:40
 * @VERSION 1.0
 **/

/**
 */
@Configuration
@EnableTransactionManagement
@ConditionalOnBean({
        MyBatisZckbOrderConfigMysql.class,
        //新增数据源在这里添加
        //XxxDataSourceConfig.class
})
public class DataSourcesAutoConfiguration implements TransactionManagementConfigurer {

    @Resource
    private MyBatisZckbOrderConfigMysql myBatisJrzbBusinessConfigMysql;
    //新增数据源在这里添加
    //@Resource
    //private XxxDataSourceConfig xxxDataSourceConfig;

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new ChainedTransactionManager(
                myBatisJrzbBusinessConfigMysql.authTransactionManager()
                //新增数据源在这里添加
                //xxxDataSourceConfig.xxxTransactionManager()
        );
    }

}