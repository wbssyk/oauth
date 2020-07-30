package com.shi.server.authserver.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * RedisTokenStoreConfig
 *
 * @author fengzheng
 * @date 2019/10/14
 */
@Configuration
public class RedisTokenStoreConfig {

    @Autowired
    private LettuceConnectionFactory redisConnectionFactory;

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean("redisTokenStore")
    public TokenStore redisTokenStore (){
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Bean("redisAuthorizationCodeServices")
    public RedisAuthorizationCodeServices redisAuthorizationCodeServices(){
        return new RedisAuthorizationCodeServices(redisTemplate);
    }
}
