package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * dish management
 */

@RestController
@RequestMapping("/admin/dish")
@Api
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;



    /**
     * adding new dish
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("add new dish")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("add new dish: {}",dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("dish page query")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("The dish page query:{}",dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);

        return Result.success(pageResult);

    }


    @DeleteMapping()
    @ApiOperation("massive delete the dish")
    public Result delete(@RequestParam List<Long> ids){
        log.info("massive delete the dish,{}",ids);
        dishService.deleteBench(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("query the dish data by Id")
    public Result<DishVO> getById(@PathVariable Long id){
        log.info("get the dish data by Id: {}",id);
        DishVO dishVO = dishService.getIdWithFlavor(id);
        return Result.success(dishVO);

    }


    @PutMapping
    @ApiOperation("edit the dish data")
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("update the dish data: {}",dishDTO);
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("list the dish by category")
    public Result<List<Dish>> listByCategory(Long categoryId){
        List<Dish> dishVOList = dishService.listByCategory(categoryId);
        return Result.success(dishVOList);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("start or stop the dish")
    public Result startOrStop(@PathVariable Integer status,Long id){
        dishService.update(status,id);
        return Result.success();
    }
}
