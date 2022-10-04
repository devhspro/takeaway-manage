package com.hspro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hspro.enity.Employee;
import com.hspro.mapper.EmployeeMapper;
import com.hspro.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/9/25
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
