package com.hspro.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hspro.common.R;
import com.hspro.dto.OrdersDto;
import com.hspro.enity.*;
import com.hspro.service.AddressBookService;
import com.hspro.service.OrderDetailService;
import com.hspro.service.OrderService;
import com.hspro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/10/4
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private UserService userService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        orderService.submit(orders);
        return R.success("下单成功");
    }

    @GetMapping("/userPage")
    public R<Page> userPage(int page, int pageSize){
        Page<OrderDetail> detailPage = new Page<>(page, pageSize);
        Page<OrdersDto> dtoPage = new Page<>();

        orderDetailService.page(detailPage);
        List<OrderDetail> records = detailPage.getRecords();

        List<OrdersDto> ordersDtoList = records.stream().map((item)->{
            OrdersDto ordersDto = new OrdersDto();
            Long userId = BaseContext.getId();
            User user = userService.getById(userId);
            LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AddressBook::getUserId, userId);
            AddressBook addressBook = addressBookService.getOne(queryWrapper);
            ordersDto.setAddress(addressBook.getDetail());
            ordersDto.setConsignee(addressBook.getConsignee());
            ordersDto.setPhone(user.getPhone());
            ordersDto.setUserName(addressBook.getConsignee());
            return ordersDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(ordersDtoList);
        return R.success(dtoPage);
    }
}
