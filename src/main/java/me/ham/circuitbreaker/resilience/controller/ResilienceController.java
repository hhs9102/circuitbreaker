package me.ham.circuitbreaker.resilience.controller;

import me.ham.circuitbreaker.resilience.service.ResilienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Timer;
import java.util.TimerTask;

@RestController
@RequestMapping(value = "/resilience")
public class ResilienceController {

    @Autowired
    ResilienceService resilienceService;

    @RequestMapping("/{ms}")
    public void onlyTest(@PathVariable long ms){
        new Thread(()->{
            Timer t = new Timer(true);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("5초가 지났다.");
                }
            };

            t.schedule(timerTask, 5000);
        }).start();

        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setReadTimeout(2000);
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);

//        RestTemplate restTemplate = new RestTemplate((uri, httpMethod) -> {
//
//        });
        try{

            ResponseEntity<String> test = restTemplate.getForEntity("http://localhost:8080/connect/waitting/"+ms, String.class);
            if(test.getStatusCode().isError()) {
                System.out.println("test.getStatusCode().isError() true 이것이 실행되었따.");
                System.out.println("에러발생했따~~");
            }else{
                System.out.println("이것이 실행되었따.");
            }
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

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
