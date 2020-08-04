import com.alibaba.druid.pool.DruidDataSource;
import com.xss.config.SpringMybatis;
import com.xss.entity.SysRole;
import com.xss.mapper.SysRoleMapper;
import com.xss.service.SysRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    SysRoleMapper mapper;

    @Autowired
    SysRoleService service;

    @Autowired
    RedisTemplate<Object, Object> template;


    @Test
    public void test() {
        SysRole role = new SysRole();

        service.selectPage(1,5,role);
    }





}
