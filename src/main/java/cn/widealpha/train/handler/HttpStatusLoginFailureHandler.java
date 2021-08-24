package cn.widealpha.train.handler;

import cn.widealpha.train.bean.ResultEntity;
import cn.widealpha.train.bean.StatusCode;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpStatusLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JSON.toJSONString(ResultEntity.error(StatusCode.USER_CREDENTIALS_ERROR)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}