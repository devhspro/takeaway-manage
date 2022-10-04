package com.hspro.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hspro.common.R;
import com.hspro.dto.DishDto;
import com.hspro.enity.Category;
import com.hspro.enity.Dish;
import com.hspro.enity.DishFlavor;
import com.hspro.service.DishFlavourService;
import com.hspro.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author: devhspro@gmail.com
 * @Date: 2022/10/1
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavourService dishFlavourService;

    @PostMapping
    public R<String> add(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.saveWithFlavours(dishDto);
        return R.success("保存成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<DishDto> pageInfo = dishService.page(page, pageSize, name);
        System.out.println(pageInfo.getRecords());
        return R.success(pageInfo);
    }

    @DeleteMapping
    public R<String> deleteMore(Long[] ids){
        log.info(Arrays.toString(ids));
        for (Long id : ids) {
            dishService.removeById(id);
        }
        return R.success("删除成功");
    }

    @GetMapping("/{id}")
    public R<DishDto> dataRollBack(@PathVariable Long id){
        Dish dish = dishService.getById(id);
        DishDto dishDto = new DishDto();

        BeanUtils.copyProperties(dish, dishDto);

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getId, id);
        List<DishFlavor> list = dishFlavourService.list(queryWrapper);

        dishDto.setFlavors(list);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateById(dishDto);
        return R.success("修改成功");
    }

    @PostMapping("/status/0")
    public R<String> stopSold(Long[] ids){
        for (Long id : ids) {
            Dish dish = dishService.getById(id);
            dish.setStatus(0);
            dishService.updateById(dish);
        }
        return R.success("停售成功");
    }

    @PostMapping("/status/1")
    public R<String> startSold(Long[] ids){
        for (Long id : ids) {
            Dish dish = dishService.getById(id);
            dish.setStatus(1);
            dishService.updateById(dish);
        }
        return R.success("起售成功");
    }

    @GetMapping("/list")
    public R<List<DishDto>> queryCategory(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null, Dish::getCategoryId, dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus, 1);
        queryWrapper.orderByDesc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);

        List<DishDto> dishDtoList = list.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(DishFlavor::getDishId, dishId);
            List<DishFlavor> dish_list = dishFlavourService.list(Wrapper);
           dishDto.setFlavors(dish_list);
           return dishDto;
        }).collect(Collectors.toList());

        return R.success(dishDtoList);
    }
}
