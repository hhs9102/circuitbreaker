package me.ham.circuitbreaker.resilience.controller;

import me.ham.circuitbreaker.resilience.service.ResilienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/resilence")
public class ResilienceController {

        @Autowired
        ResilienceService resilienceService;

        @RequestMapping(value = "/success")
        public String success(){
            return resilienceService.sucess();
        }

        @RequestMapping(value = "/failure")
        public String failure() throws Exception {
            return resilienceService.failure();
        }


}
