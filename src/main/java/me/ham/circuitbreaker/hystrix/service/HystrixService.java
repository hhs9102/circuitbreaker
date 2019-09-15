package me.ham.circuitbreaker.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@EnableCircuitBreaker
@Service
@Slf4j
public class HystrixService {

    AtomicInteger successMethodCallCnt;
    AtomicInteger failureMethodCallCnt;
    AtomicInteger failureFallBackMethodCallCnt;

    public HystrixService() {
        this.successMethodCallCnt = new AtomicInteger(0);
        this.failureMethodCallCnt = new AtomicInteger(0);
        this.failureFallBackMethodCallCnt = new AtomicInteger(0);
    }

    @HystrixCommand(fallbackMethod = "fallback"
//            ,commandKey = "hsham"
//            ,commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000") //요청 대기 시간
//            ,@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5") //최소 요청 건수
//            ,@HystrixProperty(name = "- circuitBreaker.sleepWindowInMilliseconds", value = "5000") //서킷브레이커 지속시간
//            }
            )
    public String success() {
        successMethodCallCnt.addAndGet(1);
        successMethodCallCnt.addAndGet(1);
        log.info("HystrixService success method is called");
        return "This is success method.\n";
    }

    @HystrixCommand(fallbackMethod = "fallback" )
    public String failure(){
        failureMethodCallCnt.addAndGet(1);
        log.info("HystrixService failure method is called");
        throw new RuntimeException("throw runtimeException");
    }

    @HystrixCommand(fallbackMethod = "fallback" )
    public String failure(int rate){

        int probability = failureMethodCallCnt.get();
        probability = probability%10;

        log.info("HystrixService failure method is called");
        if(probability<rate){
            failureMethodCallCnt.addAndGet(1);
            return "This is failure method";
        }else{
            failureMethodCallCnt.addAndGet(1);
            throw new RuntimeException("throw runtimeException");
        }
    }

    public String fallback(){
        failureFallBackMethodCallCnt.addAndGet(1);
        log.info("HystrixService failure FALLBACK method is called");
        return "This is fallback method.\n";
    }

    public String fallback(int rate){
        failureFallBackMethodCallCnt.addAndGet(1);
        log.info("HystrixService failure FALLBACK method is called");
        return "This is fallback method.\n";
    }

    public void reset(){
        successMethodCallCnt = new AtomicInteger(0);
        failureMethodCallCnt = new AtomicInteger(0);
        failureFallBackMethodCallCnt = new AtomicInteger(0);
        print();
    }

    public String print(){
        return "successMethodCallCnt : "+successMethodCallCnt.get() +"\nfailureMethodCallCnt : "+failureMethodCallCnt.get()+"\nfailureFallBackMethodCallCnt : "+failureFallBackMethodCallCnt.get()+
                "\n";
    }
}
