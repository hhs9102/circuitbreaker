package me.ham.circuitbreaker.resilience.controller;

import me.ham.circuitbreaker.resilience.service.ResilienceAnotherService;
import me.ham.circuitbreaker.resilience.service.ResilienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;

@RestController
@RequestMapping(value = "/resilience/another")
public class ResilienceAnotherController {

    @Autowired
    ResilienceAnotherService resilienceAnotherService;

    @RequestMapping(value = "/success")
    public String success() throws InterruptedException {
        return resilienceAnotherService.success();
    }

    @RequestMapping(value = "/failure")
    public String failure(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String str =  resilienceAnotherService.failure();
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis() + "소요 됐다.");
        return str;
    }
    @RequestMapping(value = "/failure/lamda")
    public String failureLamda(){
        return resilienceAnotherService.failureLamda();
    }

    @PutMapping(value = "reset")
    public void reset(){
        resilienceAnotherService.reset();
    }

    @GetMapping(value = "print")
    public String print(){
        return resilienceAnotherService.print();
    }

}
