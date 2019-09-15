package me.ham.circuitbreaker.resilience.controller;

import me.ham.circuitbreaker.resilience.service.ResilienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/resilience")
public class ResilienceController {

    @Autowired
    ResilienceService resilienceService;

    @RequestMapping(value = "/success")
    public String success() throws InterruptedException {
        return resilienceService.success();
    }

    @RequestMapping(value = "/failure")
    public String failure(){
        return resilienceService.failure();
    }

    @RequestMapping(value = "/failure/lamda")
    public String failureLamda(){
        return resilienceService.failureLamda();
    }

    @RequestMapping(value = "/failure/circuitBreaker")
    public String circuitBreaker(){
        return resilienceService.circuitBreaker();
    }

    @RequestMapping(value = "/failure/bulkhead")
    public String bulkhead() throws InterruptedException {
        return resilienceService.bulkhead();
    }

    @RequestMapping(value = "/failure/retry")
    public String retry(){
        return resilienceService.retry();
    }

    @RequestMapping(value = "/failure/all")
    public String all(){
        return resilienceService.retry();
    }

    @PutMapping(value = "reset")
    public void reset(){
        resilienceService.reset();
    }

    @GetMapping(value = "print")
    public String print(){
        return resilienceService.print();
    }

}
