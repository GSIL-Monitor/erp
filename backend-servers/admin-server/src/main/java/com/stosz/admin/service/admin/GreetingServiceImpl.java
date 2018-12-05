package com.stosz.admin.service.admin;

import com.stosz.admin.ext.service.IGreetingService;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements IGreetingService {

    public String getGreeting() {
        return "hello , nice to meet you!";
    }
}
