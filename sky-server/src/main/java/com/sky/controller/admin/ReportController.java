package com.sky.controller.admin;


import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 * statistics
 */

@RestController
@Api(tags = "statistics relevant interface")
@RequestMapping("/admin/report")
@Slf4j

public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/turnoverStatistics")
    @ApiOperation("the sum of the revenue statistics")
    public Result<TurnoverReportVO> turnoverStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("The sum of the revenue statistics");
        return Result.success(reportService.getTurnoverStatistics(begin, end));

    }


    @GetMapping("/userStatistics")
    @ApiOperation("user sum")
    public Result<UserReportVO> userStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("the sum of the users:{},{}", begin, end);


        return Result.success(reportService.getUserStatistics(begin, end));


    }


    @GetMapping("/ordersStatistics")
    @ApiOperation("orders relevant statistics")
    public Result<OrderReportVO> ordersStatistic(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("the order statistics from: {},{}", begin, end);

        return Result.success(reportService.getOrderStatistics(begin, end));


    }

    @GetMapping("/top10")
    @ApiOperation("orders relevant statistics")
    public Result<SalesTop10ReportVO> topTen(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("the top 10 in the dishes: {},{}", begin, end);

        return Result.success(reportService.getTopTen(begin, end));


    }




    @GetMapping("/export")
    @ApiOperation("export the operation data")
    public void export(HttpServletResponse httpServletResponse){
        reportService.exportBussinessData(httpServletResponse);

    }

}
