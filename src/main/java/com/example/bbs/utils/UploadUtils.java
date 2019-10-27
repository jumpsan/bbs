package com.example.bbs.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class UploadUtils {
    public static boolean uploadFile(HttpServletRequest request, MultipartFile multipartFile, Integer type, String newName) throws IOException {
        boolean result = false;
        if (!multipartFile.isEmpty() && type != null && newName != null) {    //(1)判断是否为空
            String filePath = request.getSession().getServletContext() .getRealPath("/") + "upload/";
                //获取服务器地址
                String realPath = request.getSession().getServletContext() .getRealPath("/") + "upload/";
                if (type == 0) {
                    realPath = realPath+"image/";  //(2) 存放路径
                } else if (type == 1) {
                    realPath = realPath+"videos/";  //(2) 存放路径
                } else {
                    realPath = realPath+"head/";
                }
                File dir=new File(realPath);
                if(!dir.exists()){
                    dir.mkdirs();
                }
                File file = new File(realPath + newName);  //(5)
                multipartFile.transferTo(file);  //(6)将文件存储到指定路径
                result = true;
        }
        return result;
    }

    public static String getNewName(MultipartFile multipartFile) {
        String newName = null;
        if (!multipartFile.isEmpty()) {    //(1)判断是否为空
            String originalName = multipartFile.getOriginalFilename();  //(3)得到原来的名字
            String uuidName = UUID.randomUUID().toString();  //(4)获得UUID，避免重复
            newName = uuidName + originalName.substring(originalName.lastIndexOf("."));  //(4)得到后缀名
            return newName;  //(7)返回新名字
        }
        return newName;
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                //System.out.println("删除文件" + fileName + "成功！");
                return true;
            } else {
                //System.out.println("删除文件" + fileName + "失败！");
                return false;
            }
        } else {
            //System.out.println("删除文件失败：" + fileName + "不存在！");
            return true;//无此文件
        }
    }

}
