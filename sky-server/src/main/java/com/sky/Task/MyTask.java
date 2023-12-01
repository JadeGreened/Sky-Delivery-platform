package com.sky.Task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Component
@Slf4j
public class MyTask {
    /**
     * the setTime mission
     */


    @Scheduled(cron = "0/5 * * * * ?")
    public void excuteTask(){
        log.info("the set time task in excuting: {}",new Date());
    }


}
