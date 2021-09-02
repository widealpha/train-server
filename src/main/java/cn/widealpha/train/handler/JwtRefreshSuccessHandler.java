package cn.widealpha.train.handler;

import cn.widealpha.train.domain.TrainUser;
import cn.widealpha.train.util.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;


public class JwtRefreshSuccessHandler implements AuthenticationSuccessHandler {

    private static final int tokenRefreshInterval = 24;  //刷新间隔1天

    public JwtRefreshSuccessHandler() {
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String tokenHeader = request.getHeader(JwtUtil.TOKEN_HEADER);
        boolean shouldRefresh = shouldTokenRefresh(JwtUtil.getIssuedAt(tokenHeader.replace(JwtUtil.TOKEN_PREFIX, "")));
        if (shouldRefresh) {
            String roles = authentication.getAuthorities().stream()
                    .map(Object::toString).collect(Collectors.joining(","));
            String newToken = JwtUtil.createToken(authentication.getName(), ((TrainUser) authentication.getPrincipal()).getUserId(), roles);
            response.setHeader("content-type", "application/json");
            response.setHeader("Authorization", newToken);
        }
    }

    protected boolean shouldTokenRefresh(Date issueAt) {
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusHours(tokenRefreshInterval).isAfter(issueTime);
    }

}