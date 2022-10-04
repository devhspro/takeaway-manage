package com.hspro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hspro.dto.SetmealDto;
import com.hspro.enity.Setmeal;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/10/2
 */
public interface SetmealService extends IService<Setmeal> {
    void saveWithCategory(SetmealDto setmealDto);
}
