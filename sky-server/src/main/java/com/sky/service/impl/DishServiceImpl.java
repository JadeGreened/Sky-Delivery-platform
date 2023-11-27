package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        // insert one data in the dish table
        dishMapper.insert(dish);
        //get the key value from the insert sentence
        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0){
            //insert multiple data in the flavor table
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * page query
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }


    /**
     * the bench deletion of the dish
     * @param ids
     */
    @Override
    @Transactional
    public void deleteBench(List<Long> ids) {
        // judge if the dish is deletable -- is it on sale?
        for (Long id : ids) {
           Dish dish = dishMapper.getByID(id);
           if (Objects.equals(dish.getStatus(), StatusConstant.ENABLE)){
               throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
           }
        }

        // judge if the dish is deletable -- is it inlined?
        List<Long> setMealIds = setMealDishMapper.getSetMealIdsByDishIds(ids);
        if (setMealIds != null && setMealIds.size() > 0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

//        //delete the dish data
//        for (Long id : ids) {
//            dishMapper.deleteById(id);
//            dishFlavorMapper.deleteByDishId(id);
//            //delete the inlined flavor data
//        }
        //batch delete the dish data By dish id
        dishMapper.deleteByIds(ids);

        //batch delete the inlined flavor data
        dishFlavorMapper.deleteByDishIds(ids);
    }


    /**
     * get the dish data and the flavor
     * @return
     */
    @Override
    public DishVO getIdWithFlavor(Long id) {
        Dish dish = dishMapper.getByID(id);
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    /**
     * update the dish data and its corresponding information
     */
    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        //update the dish table
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);

        //delete the original flavor
        Long dishId = dishDTO.getId();
        dishFlavorMapper.deleteByDishId(dishId);
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0){
            //insert multiple data in the flavor table
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public List<Dish> listByCategory(Long categoryId) {
        List<Dish> dishes = dishMapper.getByCategoryId(categoryId);

        return dishes;
    }

    @Override
    public void update(Integer status,Long id) {
        Dish dish = Dish.builder()
                .status(status)
                .id(id)
                .build();
        dishMapper.update(dish);
    }
}
