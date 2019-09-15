package me.ham.circuitbreaker.hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import me.ham.circuitbreaker.hystrix.service.HystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    @Autowired
    HystrixService hystrixService;

    @RequestMapping("/success")
    public String success(){
        return hystrixService.success();
    }

    @RequestMapping("/failure")
    public String failure(){
        return hystrixService.failure();
    }
    @RequestMapping("/failure/{rate}")
    public String failure(@PathVariable int rate){
        return hystrixService.failure(rate);
    }

    @PutMapping("/reset")
    public void reset(){
        hystrixService.reset();
    }

    @RequestMapping("/print")
    public String print(){
        return hystrixService.print();
    }
}
