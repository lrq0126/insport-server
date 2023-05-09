package com.example.warehouse.common;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

//@Configuration
public class DruidUtils {

//    private static DataSource dataSource = null;
//    private static Properties properties = null;
//
//    static {
//        properties = new Properties();
//
//        try{
//            properties.load(DruidUtils.class.getClassLoader().getResourceAsStream("application.yml"));
//            dataSource = DruidDataSourceFactory.createDataSource(properties);
//        }catch (Exception e){
//            throw  new RuntimeException("连接池加载失败");
//        }
//    }
//
//    public static DataSource getDataSource(){
//        return dataSource;
//    }
//
//    public static Connection getConnection() throws SQLException{
//        return dataSource.getConnection();
//    }
    @Value("${spring.datasource.driver-class-name}")
    private String DRIVER_CLASS_NAME;
    @Value("${spring.datasource.url}")
    private String DATASOURCE_URL;
    @Value("${spring.datasource.username}")
    private String USERNAME;
    @Value("${spring.datasource.password}")
    private String PASSWORD;
    @Bean
    public DataSource dataSourceSqlServer(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(DRIVER_CLASS_NAME);
        druidDataSource.setUrl(DATASOURCE_URL);
        druidDataSource.setUsername(USERNAME);
        druidDataSource.setPassword(PASSWORD);
        druidDataSource.setTestWhileIdle(false);

        List<Filter> filterList = new ArrayList<>();

        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
        slf4jLogFilter.setStatementExecutableSqlLogEnable(true);
        filterList.add(slf4jLogFilter);

        StatFilter statFilter = new StatFilter();
        statFilter.setMergeSql(true);
        statFilter.setLogSlowSql(true);
        statFilter.setSlowSqlMillis(300);
        filterList.add(statFilter);
        druidDataSource.setProxyFilters(filterList);
        return druidDataSource;
    }

    @Bean
    @Profile("druidStat")
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("loginUsername", "root");
        reg.addInitParameter("loginPassword", "root");
        reg.addInitParameter("exclusions", "*.js, *.gif, *.jpg, *.png, *.css, *.ico, /druid/*");
        return reg;
    }

//    public static void main(String[] args) {
//
//        DataSource dataSource = getDataSource();
//
//        System.out.println(dataSource);
//    }

}
