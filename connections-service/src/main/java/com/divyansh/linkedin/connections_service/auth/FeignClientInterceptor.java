package com.divyansh.linkedin.connections_service.auth;

import com.divyansh.linkedin.connections_service.auth.UserContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Long userId = UserContextHolder.getCurrentUserId();
        if(userId!=null){
            requestTemplate.header("X-User-Id",userId.toString());
        }
    }
}
