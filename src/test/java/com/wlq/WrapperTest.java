package com.wlq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wlq.entity.User;
import com.wlq.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class WrapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        //查询name不为空，Email不为空，age大于20
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper
                .isNotNull("name")
                .isNotNull("email")
                .ge("age",12);
        userMapper.selectList(wrapper).forEach(System.out::println);
    }

    @Test
    void test1() {
        //查询一个
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.eq("name","lisi");
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }

    @Test
    void test2() {
        //查询区间
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.between("age",20,30);
        Integer count = userMapper.selectCount(wrapper);
        System.out.println(count);
    }

    @Test
    void test3() {
        //模糊查询
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //%z%(String), t%(String)
        wrapper
                .notLike("name","z")
                .likeRight("email","t");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    @Test
    void test4() {
        //模糊查询
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //%z%(String), t%(String)
        wrapper
                .notLike("name","z")
                .likeRight("email","t");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    @Test
    void test5() {
        //IN查询
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //id 在子查询中查出来
        wrapper.inSql("id","select id from user where id<3");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }
}
