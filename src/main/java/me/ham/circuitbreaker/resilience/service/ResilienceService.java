package me.ham.circuitbreaker.resilience.service;

import me.ham.circuitbreaker.resilience.connector.ResilienceConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResilienceService {

    @Autowired
    ResilienceConnector resilienceConnector;

    public String success(){
        return resilienceConnector.success();
    }

    public String failure(){
        return resilienceConnector.failure();
    }
    public String failureLamda(){
        return resilienceConnector.failureLamda();
    }
}
