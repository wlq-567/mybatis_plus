package com.wlq;

import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlq.entity.User;
import com.wlq.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class MybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        //查询全部
        //参数是一个mapper，条件构造器
        //userMapper.selectList.var可快速生成前面的users
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    void testInsert (){
        User user = new User();
        user.setId(6L);
        user.setName("wangwu");
        user.setAge(48);
        user.setEmail("888888@.qq.com");

        int result = userMapper.insert(user);   //自动生成id
        System.out.println("----------------------");
        System.out.println(result); //受影响的行数
        System.out.println(user);   //id自动回填
    }

    @Test
    void testUpdate1 (){
        User user = new User();
//        user.setId(6L);
        user.setName("lisi");
        user.setAge(18);
        user.setEmail("88888@.qq.com");

        userMapper.updateById(user);
    }

    @Test
    void testUpdate2 (){
        User user = new User();
        user.setId(6L);
        user.setAge(28);

        userMapper.updateById(user);
    }

    //测试乐观锁成功
    @Test
    void testOptimisticLocker (){
        User user = userMapper.selectById(1L);
        user.setName("火花");
        user.setAge(28);

        userMapper.updateById(user);
    }

    //测试乐观锁失败，多线程下
    @Test
    void testOptimisticLocker2 (){
        User user = userMapper.selectById(1L);
        user.setName("火花111");
        user.setAge(28);

        //模拟有线程插队
        User user2 = userMapper.selectById(1L);
        user2.setName("火花2222");
        user2.setAge(28);
        userMapper.updateById(user2);

        //如果没有乐观锁，这个更新操作会覆盖user2插队线程的数据
        userMapper.updateById(user);
    }

    //查询
    @Test
    void testSelect3 (){
        userMapper.selectById(11L);
    }

    @Test
    void testSelect (){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    //条件查询
    @Test
    void testSelect2 (){
        HashMap<String, Object> map = new HashMap<>();
        //自定义查询
        map.put("name","lisi");

        List<User> list = userMapper.selectByMap(map);
        list.forEach(System.out::println);
    }

    //分页查询
    @Test
    void testPage (){
        //参数一：当前页   参数二：页面大小
        Page<User> page = new Page<>(1,5);
        userMapper.selectPage(page,null);

        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal());    //总页数
    }

    //删除
    @Test
    void testDelete (){
        userMapper.deleteById(11L);
    }

    /**
     * SQL执行效率插件
     */
    @Bean
    @Profile({"dev","test"})    //设置dev test环境开启，保证我们的效率
    public PerformanceInterceptor performanceInterceptor(){
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setMaxTime(1);   //ms，设置SQL最大执行时间，如果超过则不执行
        performanceInterceptor.setFormat(true); //SQL格式化
        return performanceInterceptor;
    }
}
