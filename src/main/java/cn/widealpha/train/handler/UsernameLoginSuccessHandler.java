package cn.widealpha.train.handler;

import cn.widealpha.train.bean.ResultEntity;
import cn.widealpha.train.domain.TrainUser;
import cn.widealpha.train.util.JwtUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class UsernameLoginSuccessHandler implements AuthenticationSuccessHandler {


    public UsernameLoginSuccessHandler() { }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        //生成token，并把token加密相关信息缓存
        //生成以','分割的角色信息
        String roles = authentication.getAuthorities().stream()
                .map(Object::toString).collect(Collectors.joining(","));
        String token = JwtUtil.createToken(authentication.getName(), ((TrainUser) authentication.getPrincipal()).getUserId(), roles);
        response.setHeader("Authorization","Bearer " + token);
        response.setHeader("content-type", "application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().print(JSON.toJSONString(ResultEntity.data(token)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}