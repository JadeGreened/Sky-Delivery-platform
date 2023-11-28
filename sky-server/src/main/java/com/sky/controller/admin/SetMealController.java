package com.sky.controller.admin;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/setmeal")
@ApiOperation("SetMeal relevant interface")
public class SetMealController {
    @Autowired
    private SetmealService setmealService;

    @GetMapping("/page")
    @ApiOperation("page the setMeal data")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("page the setMeal data");
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);

    }

    @PostMapping
    @ApiOperation("add the setMeal data")
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        log.info("insert the setMeal data:{}",setmealDTO);
        setmealService.insert(setmealDTO);
        return Result.success();
    }


    @PutMapping
    @ApiOperation("edit the setmeal info")
    public Result update(@RequestBody SetmealDTO setmealDTO){
        log.info("update the setmeal info");
        setmealService.update(setmealDTO);
        return Result.success();


    }

    @GetMapping("/{id}")
    @ApiOperation("Get the setmeal by id")
    public Result<SetmealVO> getById(@PathVariable Long id){
        log.info("Get the setmeal by id: {}",id);
        SetmealVO setmealVO = setmealService.getById(id);
        return Result.success(setmealVO);
    }

    @DeleteMapping
    @ApiOperation("Batch delete the setmeal")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result batchDelete(@RequestParam List<Long> ids){
        log.info("Batch Delete the setmeal");
        setmealService.batchDelete(ids);
        return Result.success();
    }


    @PostMapping("/status/{status}")
    @ApiOperation("set the start and stop")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result startOrStop(@PathVariable Integer status, Long id){
        log.info("Start or stop the setmeal: {} {}",status,id);
        setmealService.startOrStop(status,id);
        return Result.success();

    }

}
