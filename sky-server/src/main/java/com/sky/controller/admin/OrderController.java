package com.sky.controller.admin;

import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin/order")
@Api(tags = "admin port order interface")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/conditionSearch")
    @ApiOperation(value = "conditional search order")
    public Result<PageResult> conditionalSearch(OrdersPageQueryDTO ordersPageQueryDTO){
        PageResult pageResult = orderService.conditionalSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/statistics")
    @ApiOperation(value = "get statics")
    public Result<OrderStatisticsVO> statistic(){
        OrderStatisticsVO orderStatisticsVO = orderService.getStatistic();
        return Result.success(orderStatisticsVO);

    }

    @GetMapping("/details/{id}")
    @ApiOperation(value = "get order details")
    public Result<OrderVO> getDetails(@PathVariable Long id){
        OrderVO orderVO = orderService.getDetails(id);
        return Result.success(orderVO);

    }

    @PutMapping("/confirm")
    @ApiOperation(value = "confirm the order")
    public Result confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO){
        orderService.confirmOrder(ordersConfirmDTO);
        return Result.success();
    }

    @PutMapping("/rejection")
    @ApiOperation(value = "reject the order")
    public Result reject(@RequestBody OrdersRejectionDTO ordersRejectionDTO){
        orderService.reject(ordersRejectionDTO);
        return Result.success();
    }

    @PutMapping("/complete/{id}")
    @ApiOperation(value = "complete the order")
    public Result complete(@PathVariable Long id){
        orderService.complete(id);
        return Result.success();
    }

    @PutMapping("/delivery/{id}")
    @ApiOperation(value = "deliver the order")
    public Result diliver(@PathVariable Long id){
        orderService.diliver(id);
        return Result.success();
    }




}
