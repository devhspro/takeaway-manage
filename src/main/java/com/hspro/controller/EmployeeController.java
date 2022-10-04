package com.hspro.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hspro.common.R;
import com.hspro.enity.Employee;
import com.hspro.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/9/25
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee emps) {
        String password = emps.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, emps.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        if (emp == null) {
            return R.error("账号不存在");
        }

        if (!emp.getPassword().equals(password)) {
            return R.error("密码错误");
        }

        if (emp.getStatus() != 1) {
            return R.error("此账号已被禁用");
        }
        request.getSession().setAttribute("uid", emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("uid");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        employee.setCreateUser((long) request.getSession().getAttribute("uid"));
//        employee.setUpdateUser((long) request.getSession().getAttribute("uid"));

        employeeService.save(employee);

        return R.success("保存成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page pageInfo = new Page(page, pageSize);
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(name != null, Employee::getName, name);
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo);
        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> changeStatus(HttpServletRequest request, @RequestBody Employee emp) {
//        emp.setUpdateTime(LocalDateTime.now());
//        emp.setUpdateUser((long) request.getSession().getAttribute("uid"));
        employeeService.updateById(emp);
        return R.success("修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> rollData(@PathVariable String id){
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getId, id);
        Employee one = employeeService.getOne(queryWrapper);
        return R.success(one);
    }
}
