package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {


    /**
     * Dynamic Query
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);


    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart shoppingCart);

    /**
     * insert the shopping cart
     * @param shoppingCart
     */
    @Insert("insert into sky_take_out.shopping_cart(name, image, user_id, dish_id, setmeal_id, dish_flavor, number, amount, create_time) VALUES " +
            "(#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},#{createTime})")
    void insert(ShoppingCart shoppingCart);


    @Delete("delete from sky_take_out.shopping_cart where user_id = #{userId}")
    void deleteByUserId(Long userId);

    void insertBatch(List<ShoppingCart> shoppingCartList);


    @Delete("delete from sky_take_out.shopping_cart where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);


    @Delete("delete from sky_take_out.shopping_cart where setmeal_id = #{setmealId}")
    void deleteBySetmealId(Long setmealId);



    @Select("select * from sky_take_out.shopping_cart where dish_id = #{dishId}")
    ShoppingCart getByDishId(Long dishId);

    @Select("select * from sky_take_out.shopping_cart where setmeal_id = #{setmealId}")
    ShoppingCart getBySetmealId(Long setmealId);
}
