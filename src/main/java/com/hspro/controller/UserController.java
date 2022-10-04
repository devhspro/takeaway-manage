package com.hspro.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hspro.common.R;
import com.hspro.enity.User;
import com.hspro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/10/3
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<User> login(HttpServletRequest request, @RequestBody User user){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, user.getPhone());
        if(userService.getOne(queryWrapper)==null){
            user.setStatus(1);
            userService.save(user);
        }
        User user1 = userService.getOne(queryWrapper);
        Long id = user1.getId();
        request.getSession().setAttribute("userId", id);
        return R.success(user);
    }
}
