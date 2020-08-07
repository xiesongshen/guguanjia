import com.alibaba.druid.pool.DruidDataSource;
import com.xss.config.SpringMybatis;
import com.xss.entity.SysLog;
import com.xss.mapper.SysLogMapper;
import com.xss.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author XSS
 * @date 2020/7/16
 * @desc
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatis.class)
public class TestDemo {

    @Autowired
    DruidDataSource dataSource;

    @Autowired
    SysLogMapper mapper;

    @Autowired
    SysUserService service;

    @Autowired
    RedisTemplate<Object, Object> template;


    @Test
    public void test() {
        SysLog sysLog = new SysLog();
        sysLog.setType("1");
        List<SysLog> select = mapper.select(sysLog);
    }





}
