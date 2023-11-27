package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    public void saveWithFlavor(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBench(List<Long> ids);

    DishVO getIdWithFlavor(Long id);

    void updateWithFlavor(DishDTO dishDTO);

    List<Dish> listByCategory(Long categoryId);

    void update(Integer status,Long id);
}
