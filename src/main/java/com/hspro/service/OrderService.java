package com.hspro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hspro.enity.Orders;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/10/4
 */
public interface OrderService extends IService<Orders> {
    void submit(Orders orders);
}
