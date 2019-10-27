package com.example.bbs.dto;

import com.example.bbs.entity.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserDto {
    private User user;
    private MultipartFile multipartFile;
}
