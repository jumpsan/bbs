package com.example.bbs.utils;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
    public static Integer judgeType(MultipartFile multipartFile){
        String originalFilename = multipartFile.getOriginalFilename();
        if(originalFilename.endsWith(".jpg")|| originalFilename.endsWith(".png")|| originalFilename.endsWith(".gif")){
            return 0;
        }else if(originalFilename.endsWith(".mp4")){
            return 1;
        }else{
            return -1;
        }
    }


}
