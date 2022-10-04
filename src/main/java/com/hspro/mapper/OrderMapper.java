package com.hspro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hspro.enity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/10/4
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
