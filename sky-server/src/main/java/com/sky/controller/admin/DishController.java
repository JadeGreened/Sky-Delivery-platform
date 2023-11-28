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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
    @Autowired
    private RedisTemplate redisTemplate;



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
        String key = "dish_"+ dishDTO.getCategoryId();
        cleanCache(key);
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
        cleanCache("dish_*");
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
        cleanCache("dish_*");
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
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * clean the cache data
     * @param pattern
     */
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
