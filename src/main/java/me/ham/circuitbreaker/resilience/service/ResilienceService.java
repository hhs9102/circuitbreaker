package me.ham.circuitbreaker.resilience.service;

import me.ham.circuitbreaker.resilience.connector.ResilienceConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResilienceService {

    ResilienceConnector resilienceConnector;

    @Autowired
    public ResilienceService(ResilienceConnector resilienceConnector){
        this.resilienceConnector    = resilienceConnector;
    }

    public String success() throws InterruptedException {
        return resilienceConnector.success();
    }

    public String failure(){
        return resilienceConnector.failure();
    }
    public String failureLamda(){
        return resilienceConnector.failureLamda();
    }

    public void reset(){
        resilienceConnector.reset();
    }

    public String print() {
        return resilienceConnector.print();
    }

    public String circuitBreaker() {
        return resilienceConnector.circuitBreaker();
    }

    public String bulkhead() throws InterruptedException {
        return resilienceConnector.bulkhead();
    }

    public String retry() {
        return resilienceConnector.retry();
    }
    public String all() {
        return resilienceConnector.all();
    }
}
