package com.hspro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hspro.enity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/9/25
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
