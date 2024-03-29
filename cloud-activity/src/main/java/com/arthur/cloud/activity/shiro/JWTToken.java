package com.arthur.cloud.activity.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JWTToken implements AuthenticationToken {

    // 密钥
    private String token;

   /* public JWTToken(String token) {
        this.token = token;
    }*/

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
