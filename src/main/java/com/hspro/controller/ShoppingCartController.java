package com.hspro.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hspro.common.R;
import com.hspro.enity.BaseContext;
import com.hspro.enity.ShoppingCart;
import com.hspro.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/10/4
 */
@RestController
@Slf4j
@RequestMapping("/shoppingCart")
public class ShoppingCartController {


    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){

        Long id = BaseContext.getId();
        shoppingCart.setUserId(id);

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, id);

        Long dishId = shoppingCart.getDishId();
        if(dishId!=null){
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        }else {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }


        ShoppingCart res = shoppingCartService.getOne(queryWrapper);

        if(res!=null){
            Integer number = res.getNumber();
            res.setNumber(number+1);
            shoppingCartService.updateById(res);
        }else {
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            res = shoppingCart;
        }

        return R.success(res);
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getId());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);

        return R.success(list);
    }

    @DeleteMapping("/clean")
    public R<String> clean(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getId());
        shoppingCartService.remove(queryWrapper);
        return R.success("清空购物车成功");
    }
}
