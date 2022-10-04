package com.hspro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hspro.enity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/10/1
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
