package com.sky.Task;


import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;

    @Scheduled(cron = "0 0 1 * * ?")//trigger every minutes
    public void ProcessTimeOut(){
        log.info("process the overtime order:{}", LocalDateTime.now());
        Integer status = 0;
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, time);
        if (ordersList != null && ordersList.size() >0){
            for (Orders orders : ordersList) {
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelReason("order overtime, cancelled");
                orders.setCancelTime(LocalDateTime.now());
                orderMapper.update(orders);
            }
        }
    }


    @Scheduled(cron = "0 0 0 1 * ?")
    public void processDeliveryOrder(){
        log.info("process the order in delivery:{}",LocalDateTime.now());
        LocalDateTime time = LocalDateTime.now().minusMinutes(-60);
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, time);
        if (ordersList != null && ordersList.size() >0){
            for (Orders orders : ordersList) {
                orders.setStatus(Orders.COMPLETED);
                orderMapper.update(orders);
            }
        }
    }
}
