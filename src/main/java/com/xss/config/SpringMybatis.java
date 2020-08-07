package com.xss.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import com.xss.utils.MapWrapperFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import tk.mybatis.spring.annotation.MapperScan;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author XSS
 * @date 2020/7/16
 * @desc
 */
@Configuration
@MapperScan("com.xss.mapper")
@Import({SpringTransaction.class,SpringRedisConfig.class,SpringCacheConfig.class})
public class SpringMybatis {

    @Bean
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        InputStream is = SpringMybatis.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pro = new Properties();
        try {
            pro.load(is);

            dataSource.configFromPropety(pro);
            return dataSource;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DruidDataSource dataSource){
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        tk.mybatis.mapper.session.Configuration configuration =
                new tk.mybatis.mapper.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCallSettersOnNulls(true);
        configuration.setObjectWrapperFactory(new MapWrapperFactory());//自定义map对象的包装工厂

        factoryBean.setConfiguration(configuration);


        //设置分页插件
        PageInterceptor pageInterceptor = new PageInterceptor();//分页拦截对象
        pageInterceptor.setProperties(new Properties());//设置参数，用于生成默认的方言
        factoryBean.setPlugins(pageInterceptor);

        return factoryBean;
    }




}
