package com.hspro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hspro.enity.Category;
import com.hspro.mapper.CategoryMapper;
import com.hspro.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/9/30
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
