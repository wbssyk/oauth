package com.shi.server.authserver.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author shiyakun
 * @Description 安全服务配置
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) {
//        // 定义认证的provider用于实现用户名和密码认证
//        auth.authenticationProvider(new UsernamePasswordAuthenticationProvider(usernamePasswordUserDetailService));
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 允许匿名访问所有接口 主要是 oauth 接口
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单登录
        http.formLogin()
                // 登录页面
                .loginPage("/index.html")
                // 登录处理url
                .loginProcessingUrl("/test/login");

        // 必须配置，不然OAuth2的http配置不生效
        http.requestMatchers()
                .antMatchers("/index.html", "/test/login","/oauth/authorize")
                .and()
                .authorizeRequests()
                // 自定义页面或处理url是，如果不配置全局允许，浏览器会提示服务器将页面转发多次
                .antMatchers("/index.html", "/test/login","/oauth/token")
                //permitAll()表示这个不需要验证
                .permitAll()

                .anyRequest()
                .authenticated()
                ;

        http.csrf().disable();
//        http.httpBasic().disable();
    }
}
