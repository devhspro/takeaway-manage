package com.hspro.dto;

import com.hspro.enity.Setmeal;
import com.hspro.enity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
