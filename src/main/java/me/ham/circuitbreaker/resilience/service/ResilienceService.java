package me.ham.circuitbreaker.resilience.service;

import me.ham.circuitbreaker.resilience.connector.ResilienceConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResilienceService {

    @Autowired
    ResilienceConnector resilienceConnector;

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
}