package cn.widealpha.train.controller;

import cn.widealpha.train.pojo.dto.ResultEntity;
import cn.widealpha.train.util.StatusCode;
import cn.widealpha.train.pojo.entity.SystemSetting;
import cn.widealpha.train.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("system")
public class SystemController {
    @Autowired
    SystemService systemService;

    @RequestMapping("systemSetting")
    ResultEntity systemSetting(){
        return ResultEntity.data(systemService.getSystemSetting());
    }

    @PreAuthorize("hasRole('ROLE_SYSTEM')")
    @RequestMapping("updateSystemSetting")
    ResultEntity systemSetting(@RequestBody SystemSetting systemSetting){
        try {
            return ResultEntity.data(systemService.updateSystemSetting(systemSetting));
        } catch (Exception e){
            return ResultEntity.error(StatusCode.PARAM_NOT_VALID);
        }
    }
}
