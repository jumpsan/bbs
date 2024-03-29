package com.example.bbs.dto;

import com.example.bbs.entity.Post;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostDto extends Post{
    private List<MultipartFile> multipartFile; //图片或视频
}
