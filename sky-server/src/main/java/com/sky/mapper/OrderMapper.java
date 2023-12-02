package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    /**
     * insert the data
     *
     * @param orders
     */
    void insert(Orders orders);

    /**
     * 根据订单号查询订单
     *
     * @param orderNumber
     */
    @Select("select * from sky_take_out.orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     *
     * @param orders
     */
    void update(Orders orders);

    Page<Orders> queryHistory(OrdersPageQueryDTO ordersPageQueryDTO);


    @Select("select * from sky_take_out.orders where id = #{orderId}")
    Orders getById(Long orderId);


    @Delete("delete from sky_take_out.orders where id = #{id}")
    void deleteById(long id);


    @Select("select * from sky_take_out.orders")
    List<Orders> list();

    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select * from sky_take_out.orders where status = #{status} and order_time <#{orderTime}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);

    Double sumByMap(Map map);


    Integer countByMap(Map map);

    List<GoodsSalesDTO> getSalesTopTen(LocalDateTime begin, LocalDateTime end);
}
