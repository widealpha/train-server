package cn.widealpha.train.service;

import cn.widealpha.train.dao.SystemSettingMapper;
import cn.widealpha.train.domain.SystemSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemService {
    @Autowired
    SystemSettingMapper systemSettingMapper;

    public SystemSetting getSystemSetting() {
        return systemSettingMapper.selectSystemSetting();
    }

    public boolean updateSystemSetting(SystemSetting systemSetting) {
        return systemSettingMapper.updateSystemSetting(systemSetting);
    }
}
