package com.sky.controller.admin;


import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "shop relavant interface")
@Slf4j
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;
    public static final String KEY = "SHOP_STATUS";

    @PutMapping("/{status}")
    @ApiOperation("set the shop status")
    public Result setStatus(@PathVariable Integer status) {
        log.info("set the shop status to :{}", status == 1 ? "on" : "close");
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }


    @GetMapping("/status")
    @ApiOperation("get the operation status od the shop")
    public Result<Integer> getStatus() {
        Integer shopStatus = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("get the shop status:{}", shopStatus == 1 ? "on" : "off");
        return Result.success(shopStatus);
    }
}
