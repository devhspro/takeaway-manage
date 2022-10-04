package com.hspro.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hspro.dto.DishDto;
import com.hspro.enity.Dish;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/10/1
 */
public interface DishService extends IService<Dish> {
    void saveWithFlavours(DishDto dishDto);
    Page<DishDto> page(int page, int pageSize, String name);
}
