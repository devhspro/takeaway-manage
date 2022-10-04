package com.hspro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hspro.enity.User;
import com.hspro.mapper.UserMapper;
import com.hspro.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/10/3
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
