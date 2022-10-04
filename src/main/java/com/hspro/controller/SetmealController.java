package com.hspro.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hspro.common.R;
import com.hspro.dto.SetmealDto;
import com.hspro.enity.Category;
import com.hspro.enity.Dish;
import com.hspro.enity.Setmeal;
import com.hspro.enity.SetmealDish;
import com.hspro.service.CategoryService;
import com.hspro.service.DishService;
import com.hspro.service.SetmealDishService;
import com.hspro.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/10/2
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishService dishService;

    @PostMapping
    public R<String> add(@RequestBody SetmealDto setmealDto){
        System.out.println(setmealDto.toString());
        setmealService.saveWithCategory(setmealDto);
        return R.success("保存成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null, Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, setmealDtoPage);

        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();
            setmealDto.setCategoryName(categoryName);
            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(list);
        return R.success(setmealDtoPage);
    }

    @DeleteMapping
    public R<String> delete(Long[] ids){
        for (Long id : ids) {
            setmealService.removeById(id);
        }
        return R.success("删除成功");
    }

    @PostMapping("/status/0")
    public R<String> stopSold(Long[] ids){
        for (Long id : ids) {
            Setmeal setmeal = setmealService.getById(id);
            setmeal.setStatus(0);
            setmealService.updateById(setmeal);
        }
        return R.success("停售成功");
    }

    @PostMapping("/status/1")
    public R<String> startSold(Long[] ids){
        for (Long id : ids) {
            Setmeal setmeal = setmealService.getById(id);
            setmeal.setStatus(1);
            setmealService.updateById(setmeal);
        }
        return R.success("起售成功");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> dataRollBack(@PathVariable Long id){
        SetmealDto setmealDto = new SetmealDto();
        Setmeal setmeal = setmealService.getById(id);
        BeanUtils.copyProperties(setmeal, setmealDto);
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> list = setmealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(list);
        return R.success(setmealDto);
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        setmealService.updateById(setmealDto);
        return R.success("修改成功");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> queryCategory(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId()!=null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(Setmeal::getStatus, 1);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }

}
