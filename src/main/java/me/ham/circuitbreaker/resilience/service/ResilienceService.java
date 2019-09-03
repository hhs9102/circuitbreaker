package me.ham.circuitbreaker.resilience.service;

import me.ham.circuitbreaker.resilience.connector.ResilienceConnectorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResilienceService {

    @Autowired
    ResilienceConnectorImpl resilienceConnector;

    public String sucess(){
        return resilienceConnector.sucess();
    }

    public String failure() throws Exception {
        return resilienceConnector.failure();
    }
}
