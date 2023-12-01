package com.sky.controller.user;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.OrderDetail;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Api(tags = "User port order interface")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    @ApiOperation("user submit order")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        log.info("the user submit order");
        OrderSubmitVO submitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(submitVO);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        orderService.paySuccess(ordersPaymentDTO.getOrderNumber());
        log.info("T模拟交易成功:{}",ordersPaymentDTO.getOrderNumber());
        return Result.success(orderPaymentVO);
    }


    @GetMapping("historyOrders")
    @ApiOperation("query the history orders")
    public Result<PageResult> history(int page, int pageSize, Integer status){
        log.info("query the history orders");
        PageResult pageResult = orderService.historyPageQuery(page,pageSize,status);
        return Result.success(pageResult);

    }


    @GetMapping("/orderDetail/{id}")
    @ApiOperation("show the order details")
    public Result<OrderVO> detatil(@PathVariable Long id){
        OrderVO orderVO = orderService.showDetails(id);
        return Result.success(orderVO);
    }

    @PutMapping("/cancel/{id}")
    @ApiOperation("delete orders")
    public Result cancel(@PathVariable long id) throws Exception {
        orderService.cancelOrders(id);
        return Result.success();
    }


    @PostMapping("/repetition/{id}")
    @ApiOperation("resend the order")
    public Result resend(@PathVariable Long id){
        orderService.resend(id);
        return Result.success();
    }


}
