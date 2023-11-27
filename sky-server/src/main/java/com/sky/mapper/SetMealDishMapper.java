package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetMealDishMapper {


    /**
     * select the setMeal id according to the dish id
     * @param dishIds
     * @return
     */
    List<Long> getSetMealIdsByDishIds(List<Long> dishIds);



    void insertBatch(List<SetmealDish> dishes);

    @Delete("delete from sky_take_out.setmeal_dish where id = #{setmealId}")
    void deleteById(Long setmealId);


    @Select("select * from sky_take_out.setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getDishesBySetmealId(Long id);

    void deleteByIds(List<Long> ids);
}
