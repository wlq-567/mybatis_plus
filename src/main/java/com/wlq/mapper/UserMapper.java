package com.wlq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wlq.entity.User;
import org.springframework.stereotype.Repository;

//  在对应的mapper上面继承 BaseMapper
//@Mapper
@Repository //  代表持久层
public interface UserMapper extends BaseMapper<User> {
    //所有的CRUD操作都已经完成
    //不需要像之前一样配置一堆配置文件了
}
