package cn.widealpha.train.service;

import cn.widealpha.train.dao.UserInfoMapper;
import cn.widealpha.train.domain.UserInfo;
import cn.widealpha.train.util.ObjectUtil;
import cn.widealpha.train.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;

    public UserInfo getUserInfo() {
        if (UserUtil.getCurrentUserId() != null) {
            return userInfoMapper.selectByUserId(UserUtil.getCurrentUserId());
        }
        return null;
    }

    public boolean updateUserInfo(UserInfo userInfo) {
        if (UserUtil.getCurrentUserId() != null) {
            UserInfo info = userInfoMapper.selectByUserId(UserUtil.getCurrentUserId());
            ObjectUtil.copyOnNotNull(userInfo, info);
            return userInfoMapper.updateUserInfo(info);
        }
        return false;
    }
}
