package com.shi.client.handler;

import com.shi.common.utils.JSONUtil;
import com.shi.common.common.R;
import com.shi.common.enums.ResultEnum;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shiyakun
 * @Description TODO
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            response.getWriter().write(
                    JSONUtil.objToJson(R.result(ResultEnum.OAUTH_TOKEN_DENIED, false))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
