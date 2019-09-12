package me.ham.circuitbreaker.resilience.controller;

import me.ham.circuitbreaker.resilience.connector.ResilienceAnotherConnector;
import me.ham.circuitbreaker.resilience.service.ResilienceAnotherService;
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

    @PutMapping(value = "reset")
    public void reset(){
        resilienceService.reset();
    }

    @GetMapping(value = "print")
    public String print(){
        return resilienceService.print();
    }

}