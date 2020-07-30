package com.shi.server.authserver.config.auth;

import com.shi.server.handler.CustomWebResponseExceptionTranslator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;

/**
 * @author shiyakun
 * @Description 认证服务配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    public final PasswordEncoder passwordEncoder;

    public final KiteUserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final TokenStore redisTokenStore;

    private final AuthorizationCodeServices redisAuthorizationCodeServices;
    private final CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator;

    public AuthorizationServerConfig(PasswordEncoder passwordEncoder, KiteUserDetailsService userDetailsService, AuthenticationManager authenticationManager,
                                     @Qualifier("redisTokenStore") TokenStore redisTokenStore,
                                     @Qualifier("redisAuthorizationCodeServices") AuthorizationCodeServices redisAuthorizationCodeServices,
                                     @Qualifier("customWebResponseExceptionTranslator") CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.redisTokenStore = redisTokenStore;
        this.redisAuthorizationCodeServices = redisAuthorizationCodeServices;
        this.customWebResponseExceptionTranslator = customWebResponseExceptionTranslator;
    }

//    @Autowired
//    private DataSource dataSource;

//    @Autowired
//    private TokenStore jwtTokenStore;
//
//    @Autowired
//    private JwtAccessTokenConverter jwtAccessTokenConverter;
//
//    @Autowired
//    private TokenEnhancer jwtTokenEnhancer;


    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        /**
         * 配置系统可以获取用户信息
         */
        DefaultAccessTokenConverter converter = new DefaultAccessTokenConverter();
        DefaultUserAuthenticationConverter userAuthenticationConverter
                = new DefaultUserAuthenticationConverter();
        userAuthenticationConverter.setUserDetailsService(userDetailsService);
        converter.setUserTokenConverter(userAuthenticationConverter);
        endpoints.accessTokenConverter(converter);

        /**
         * redis token 方式
         */
        endpoints
                .authenticationManager(authenticationManager)
                .authorizationCodeServices(redisAuthorizationCodeServices)
                .tokenStore(redisTokenStore)
                .userDetailsService(userDetailsService)
                .exceptionTranslator(customWebResponseExceptionTranslator)
        ;
        /**
         *   配置自定义的授权页面跳转
         *   spring security默认页面授权是请求/oauth/confirm_access 然后跳转页面
         *   这里我们重写/oauth/confirm_access跳转页面
         * @see GrantController
         */
        endpoints.pathMapping("/oauth/confirm_access",
                "/custom/confirm_access");

        /**
         * jwt 增强模式
         */
//        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
//        List<TokenEnhancer> enhancerList = new ArrayList<>();
//        enhancerList.add(jwtTokenEnhancer);
//        enhancerList.add(jwtAccessTokenConverter);
//        enhancerChain.setTokenEnhancers(enhancerList);
//        endpoints.tokenStore(jwtTokenStore)
//                .userDetailsService(kiteUserDetailsService)
//                /**
//                 * 支持 password 模式
//                 */
//                .authenticationManager(authenticationManager)
//                .tokenEnhancer(enhancerChain)
//                .accessTokenConverter(jwtAccessTokenConverter);

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.jdbc(dataSource);
        //客户端信息放在内存中
        clients.inMemory()
                .withClient("order-client")
                .secret(passwordEncoder.encode("order-secret-8888"))
                .authorizedGrantTypes("refresh_token", "authorization_code", "password")
                .accessTokenValiditySeconds(3600)
                .scopes("all");
        //表单模式
        clients.inMemory().withClient("user-client")
                .secret(passwordEncoder.encode("user-secret-8888"))
                .authorizedGrantTypes("refresh_token", "authorization_code", "password")
                .accessTokenValiditySeconds(3600)
                .scopes("all");
        //授权码模式
        clients.inMemory().withClient("code-client")
                .secret(passwordEncoder.encode("code-secret-8888"))
                .authorizedGrantTypes("refresh_token", "authorization_code")
                .accessTokenValiditySeconds(3600)
                .redirectUris("http://localhost:9001/login")
                .scopes("all");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        security.checkTokenAccess("isAuthenticated()");
        security.tokenKeyAccess("isAuthenticated()");
    }
}