package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {

    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /**
     * query the history page
     * @param ordersPageQueryDTO
     * @return
     */

    PageResult historyPageQuery(int page, int pageSize, Integer status);

    OrderVO showDetails(Long orderId);

    void cancelOrders(long id) throws Exception;

    void resend(Long id);

    PageResult conditionalSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatisticsVO getStatistic();

    OrderVO getDetails(Long id);

    void confirmOrder(OrdersConfirmDTO ordersConfirmDTO);

    void reject(OrdersRejectionDTO ordersRejectionDTO);

    void complete(Long id);

    void diliver(Long id);

    void reminder(Long id);
}
