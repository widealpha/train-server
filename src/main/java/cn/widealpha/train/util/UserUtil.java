package cn.widealpha.train.util;

import cn.widealpha.train.domain.TrainUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户工具类
 * @author kmh
 * @date 2021/7/13
 */
public final class UserUtil {
    /**
     * 获取当前登录的用户ID
     * @return 当前登陆的用户ID,未登录返回null
     */
    public static Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof TrainUser) {
            return ((TrainUser) authentication.getPrincipal()).getUserId();
        }
        return null;
    }

    /**
     * 获取当前登录的用户信息
     * @return 当前登陆的用户,未登录返回null
     */
    public static TrainUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof TrainUser) {
            return (TrainUser) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * 获取当前登录状态
     * @return 是否是登录状态下的操作
     */
    public static boolean isLogin() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    /**
     * 判断当前登陆的用户是否有某个角色
     * @param role 需要判断的角色
     * @return 是否含有角色
     */
    public static boolean hasRole(String role) {
        if (isLogin()){
            return SecurityContextHolder.getContext().getAuthentication()
                    .getAuthorities().contains(new SimpleGrantedAuthority(role));
        }
        return false;
    }
}
