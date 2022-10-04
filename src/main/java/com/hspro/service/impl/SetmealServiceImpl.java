package com.hspro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hspro.dto.SetmealDto;
import com.hspro.enity.Setmeal;
import com.hspro.enity.SetmealDish;
import com.hspro.mapper.SetmealMapper;
import com.hspro.service.SetmealDishService;
import com.hspro.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/10/2
 */
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
    @Transactional
    public void saveWithCategory(SetmealDto setmealDto) {
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        this.save(setmealDto);
        setmealDishes = setmealDishes.stream().map((item)->{
            LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Setmeal::getName, setmealDto.getName());
            Setmeal temp = this.getOne(queryWrapper);
            item.setSetmealId(temp.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }
}
