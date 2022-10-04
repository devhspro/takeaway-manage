package com.hspro.dto;

import com.hspro.enity.Dish;
import com.hspro.enity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/10/1
 */
@Data
public class DishDto extends Dish {
    private List<DishFlavor> flavors = new ArrayList<>();
    private String categoryName;
    private Integer copies;
}
