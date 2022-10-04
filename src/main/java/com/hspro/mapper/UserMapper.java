package com.hspro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hspro.enity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/10/3
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
