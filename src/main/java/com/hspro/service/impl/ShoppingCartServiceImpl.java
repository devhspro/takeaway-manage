package com.hspro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hspro.enity.ShoppingCart;
import com.hspro.mapper.ShoppingCartMapper;
import com.hspro.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/10/4
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
