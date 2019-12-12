package cn.gydata.zckb.zckbinterfaceorder.config.mybatis;


import cn.gydata.zckb.zckbinterfaceorder.config.base.CameHumpInterceptor;
import com.github.pagehelper.PageHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.spring.annotation.MapperScan;
;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

;

/**
 * @ClassName MyBatisConfigMysql
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-10-09 14:51
 * @VERSION 1.0
 **/
@Configuration
@MapperScan(basePackages = {
        "cn.gydata.zckb.zckbinterfaceorder.mapper.order"},  //这里扫描dao层的接口
        sqlSessionFactoryRef = MyBatisZckbOrderConfigMysql.SESSION_FACTORY,
        markerInterface = BaseMapper.class   //指定tkmybatis 中的通用mapper
)
public class MyBatisZckbOrderConfigMysql {
    private static Log log = LogFactory.getLog(MyBatisZckbOrderConfigMysql.class);

    static final String SESSION_FACTORY = "sqlSessionFactoryBusiness";


    static final String SESSION_TEMPLATE = "sqlSessionTemplateBusiness";

    //mapper.xml位置
    static final String MAPPER_XML_LOCATION = "classpath*:/mybatis/mapper/order/*.xml";



    static final String TRANSACTION_MANAGER = "mysqlTransactionManagerBusiness";

    @Autowired
    @Qualifier("OrderDataSource")
    private DataSource dataSource;


    @Bean(name = SESSION_FACTORY)
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        // 分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);


        // 驼峰插件
        CameHumpInterceptor cameHumpInterceptor = new CameHumpInterceptor();
//
        Interceptor[] interceptorArray = new Interceptor[] {cameHumpInterceptor};
        bean.setPlugins(interceptorArray);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        // 添加扫描目录
        bean.setMapperLocations(resolver.getResources(MAPPER_XML_LOCATION));

        return bean.getObject();
    }

    @Bean(name = SESSION_TEMPLATE)
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return  new SqlSessionTemplate(sqlSessionFactory());
    }

    @Bean(name = TRANSACTION_MANAGER)
    @Primary
    public DataSourceTransactionManager authTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
