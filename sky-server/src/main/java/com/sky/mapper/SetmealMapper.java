package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {
    

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);


    Page<SetmealVO> queryPage(SetmealPageQueryDTO setmealPageQueryDTO);



    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal setmeal);


    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);


    @Select("select * from sky_take_out.setmeal where id = #{id}")
    Setmeal getById(Long id);

    void batchDelete(List<Long> ids);


    @Select("select id from sky_take_out.setmeal where name like #{name}")
    Long getIdByName(String name);
}
