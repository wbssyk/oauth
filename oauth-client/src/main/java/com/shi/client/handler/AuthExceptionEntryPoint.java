package com.shi.client.handler;

import com.shi.common.utils.JSONUtil;
import com.shi.common.common.R;
import com.shi.common.enums.ResultEnum;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shiyakun
 * @Description 无效token处理
 */
@Component
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException {
        Throwable cause = authException.getCause();
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            if (cause instanceof InvalidTokenException) {
                response.getWriter().write(
                        JSONUtil.objToJson(R.result(ResultEnum.OAUTH_TOKEN_ILLEGAL, false))
                );
            } else {
                response.getWriter().write(
                        JSONUtil.objToJson(R.result(ResultEnum.OAUTH_TOKEN_MISSING, false))
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
