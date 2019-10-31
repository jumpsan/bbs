package com.example.bbs.dto;

import com.example.bbs.entity.UserForManager;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserForManagerDto extends UserForManager {
    private MultipartFile multipartFile;
}
