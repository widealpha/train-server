package cn.widealpha.train.service;

import cn.widealpha.train.util.FileUtil;
import cn.widealpha.train.util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    String uploadImage(MultipartFile multipartFile) {
        if (UserUtil.getCurrentUserId() != null) {
            if (multipartFile.isEmpty()) {
                return null;
            }
            return FileUtil.saveImage(multipartFile, "train");
        }
        return null;
    }
}
