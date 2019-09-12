package me.ham.circuitbreaker.resilience.service;

import me.ham.circuitbreaker.resilience.connector.ResilienceAnotherConnector;
import me.ham.circuitbreaker.resilience.connector.ResilienceConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResilienceAnotherService {

    @Autowired
    ResilienceAnotherConnector resilienceAnotherConnector;

    public String success() throws InterruptedException {
        return resilienceAnotherConnector.success();
    }

    public String failure(){
        return resilienceAnotherConnector.failure();
    }
    public String failureLamda(){
        return resilienceAnotherConnector.failureLamda();
    }

    public void reset(){
        resilienceAnotherConnector.reset();
    }

    public String print() {
        return resilienceAnotherConnector.print();
    }
}
