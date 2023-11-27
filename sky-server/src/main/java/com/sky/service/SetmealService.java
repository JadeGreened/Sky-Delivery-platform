package com.sky.service;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SetmealService {

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void insert(SetmealDTO setmealDTO);

    void update(SetmealDTO setmealDTO);

    SetmealVO getById(Long id);

    void batchDelete(List<Long> ids);

    void startOrStop(Integer status, Long id);
}
