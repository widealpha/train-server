package cn.widealpha.train.service;

import cn.widealpha.train.bean.StatusCode;
import cn.widealpha.train.dao.RoleMapper;
import cn.widealpha.train.dao.UserInfoMapper;
import cn.widealpha.train.dao.UserMapper;
import cn.widealpha.train.domain.TrainUser;
import cn.widealpha.train.domain.User;
import cn.widealpha.train.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class JwtUserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RoleMapper roleMapper;

    public JwtUserService() {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByUsername(username);
        if (user != null) {
            List<String> roles = roleMapper.selectRolesByUserId(user.getId());
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String role : roles){
                authorities.add(new SimpleGrantedAuthority(role));
            }
            //所有经过验证的用户均携带COMMON身份
            authorities.add(new SimpleGrantedAuthority("ROLE_COMMON"));
            TrainUser trainUser = new TrainUser(username, user.getPassword(), authorities);
            trainUser.setUserId(user.getId());
            return trainUser;
        } else {
            throw new UsernameNotFoundException(username + "Not Find");
        }
    }

    @Transactional
    public StatusCode createUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return StatusCode.PARAM_EMPTY;
        }
        // 保存用户名和加密后密码到数据库
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        try {
            if (userMapper.insert(user)) {
                UserInfo userInfo = new UserInfo();
                userInfo.setNickname(username);
                //插入数据库中产生的主键id
                userInfo.setUserId(user.getId());
                if (userInfoMapper.insertUserInfo(userInfo)) {
                    return StatusCode.SUCCESS;
                }
            }
            return StatusCode.COMMON_FAIL;
        } catch (DuplicateKeyException e) {
            return StatusCode.USER_ACCOUNT_ALREADY_EXIST;
        }

    }
}
