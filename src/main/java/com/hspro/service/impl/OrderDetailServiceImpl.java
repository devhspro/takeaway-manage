package com.hspro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hspro.enity.OrderDetail;
import com.hspro.mapper.OrderDetailMapper;
import com.hspro.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/10/4
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
