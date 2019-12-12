package cn.gydata.zckb.zckbinterfaceorder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DruidConfig
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-10-09 14:29
 * @VERSION 1.0
 **/
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidConfig extends DefaultDatabaseConfig {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.datasource.zckb-order-db.name}")
    private String mysql_order_name;
    @Value("${spring.datasource.zckb-order-db.url}")
    private String mysql_order_url;
    @Value("${spring.datasource.zckb-order-db.username}")
    private String mysql_order_username;
    @Value("${spring.datasource.zckb-order-db.password}")
    private String mysql_order_password;
    @Value("${spring.datasource.zckb-order-db.driver-class-name}")
    private String  mysqlOrderDriverClassName;

    @Bean(name = "OrderDataSource")
    public DataSource BusinessDataSource() {

        DruidDataSource datasource = new DruidDataSource();

        datasource.setName(mysql_order_name);
        datasource.setUrl(mysql_order_url);
        datasource.setUsername(mysql_order_username);
        datasource.setPassword(mysql_order_password);
        datasource.setDriverClassName(mysqlOrderDriverClassName);
        completeDataSourceProperties(datasource);

        List<Filter> filters = new ArrayList<>();
        filters.add(statFilter());
        filters.add(wallFilter());
        datasource.setProxyFilters(filters);
        return datasource;
    }

    @Bean
    public ServletRegistrationBean setDruidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        //控制台管理用户，加入下面2行 进入druid后台就需要登录
        //servletRegistrationBean.addInitParameter("loginUsername", "admin");
        //servletRegistrationBean.addInitParameter("loginPassword", "admin");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean setDruidFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "/static/*,*.js,*.swf,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        filterRegistrationBean.addInitParameter("sessionStatEnable", Boolean.TRUE.toString());
        return filterRegistrationBean;
    }

    /**
     * 统计SQL执行时间
     * @return
     */
    @Bean
    public StatFilter statFilter(){
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true); //slowSqlMillis用来配置SQL慢的标准，执行时间超过slowSqlMillis的就是慢。
        statFilter.setMergeSql(true); //SQL合并配置
        statFilter.setSlowSqlMillis(1000);//slowSqlMillis的缺省值为3000，也就是3秒。
        return statFilter;
    }

    @Bean
    public WallFilter wallFilter(){
        WallFilter wallFilter = new WallFilter();


        //允许执行多条SQL
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);

        wallFilter.setConfig(config);
        //设置对存在风险的sql 打印日志
        wallFilter.setLogViolation(true);
        //true 对被认为是攻击的SQL 抛出 SQLException false 不抛出异常
        wallFilter.setThrowException(false);
        return wallFilter;
    }

    public DataSource completeDataSourceProperties(DruidDataSource dataSource) {
        dataSource.setInitialSize(getInitialSize());
        dataSource.setMinIdle(getMinIdle());
        dataSource.setMaxActive(getMaxActive());
        dataSource.setMaxWait(getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(getValidationQuery());
        dataSource.setTestWhileIdle(getTestWhileIdle());
        dataSource.setTestOnBorrow(getTestOnBorrow());
        dataSource.setTestOnReturn(getTestOnReturn());
        dataSource.setPoolPreparedStatements(getPoolPreparedStatements());
        dataSource.setMaxOpenPreparedStatements(getMaxOpenPreparedStatements());
        dataSource.setRemoveAbandoned(getRemoveAbandoned());
        dataSource.setRemoveAbandonedTimeout(getRemoveAbandonedTimeout());
        dataSource.setConnectionProperties(getConnectionProperties());
        return dataSource;
    }
}
