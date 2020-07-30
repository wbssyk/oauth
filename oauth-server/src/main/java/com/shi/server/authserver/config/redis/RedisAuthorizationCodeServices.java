package com.shi.server.authserver.config.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
import org.springframework.util.Assert;

/**
 * @author shiyakun
 * @Description 认证服务集群部署 实现redis存储授权码
 */

public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    private final RedisTemplate redisTemplate;

    private final JdkSerializationStrategy serializationStrategy = new JdkSerializationStrategy();

    public RedisAuthorizationCodeServices(RedisTemplate redisTemplate){
        Assert.notNull(redisTemplate, "redis connection error");
        this.redisTemplate = redisTemplate;
    }
    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        redisTemplate.opsForValue().set(code,serializationStrategy.serialize(authentication));
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        OAuth2Authentication oAuth2Authentication = serializationStrategy.deserialize((byte[]) redisTemplate.opsForValue().get(code), OAuth2Authentication.class);
        redisTemplate.delete(code);
        return oAuth2Authentication;
    }
}
