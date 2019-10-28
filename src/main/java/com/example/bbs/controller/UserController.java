package com.example.bbs.controller;

import com.example.bbs.dto.UserDto;
import com.example.bbs.entity.Information;
import com.example.bbs.entity.Page;
import com.example.bbs.entity.User;
import com.example.bbs.service.UserService;
import com.example.bbs.utils.JjwtUtils;
import com.example.bbs.utils.UploadUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * (TUser)表控制层
 *
 * @author makejava
 * @since 2019-09-20 14:01:19
 */
@RestController
//@CrossOrigin
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;


    /**
     * 根据账号查询
     *
     * @param id
     * @return
     */
    @GetMapping("user/select/{id}")
    public Information selectUserById(@PathVariable Integer id) {
        if(id==null){
            return Information.error(406,"关键信息不可为空");
        }else {
            User user = userService.selectUserById(id);
            if(user==null){
                return Information.error(204,"无内容");
            }else{
                user.setPassword("*");
                return Information.success(200,"用户",user);
            }
        }
    }

    /**
     * 根据用户名密码查询
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户
     */
    @PostMapping("user/login")
    public Information selectUserByNameAndPassword(String username, String password) {
        User user = userService.selectUserByNameAndPassword(username, password);
        if (user == null) {
            return Information.error(202,"用户名或密码错误");
        } else {
            try {
                String token = JjwtUtils.createJWT(user.getId(), 15 * 60 * 10000);
                return Information.success(200,"token",token);
            } catch (Exception e) {
                e.printStackTrace();
                return Information.error(500,"服务器错误");
            }
        }
    }

    /**
     * 添加用户
     * 返回新用户
     * @param user 用户信息
     * @return 账号
     */
    @PostMapping("user/register")
    public Information addUser(User user,HttpServletRequest request) {
//        User user = userDto.getUser();
//        MultipartFile multipartFile = userDto.getMultipartFile();
//        String newName="";
//        if(multipartFile!=null){
//             newName= UploadUtils.getNewName(multipartFile);
//            if(newName==null){
//                return Information.error(410,"文件上传失败");
//            }
//            user.setImage(newName);
//        }
        if(user==null || user.getPassword()==null || user.getUsername()==null || user.getUsername().trim().equals("") || user.getPassword().trim().equals("")){
            return Information.error(406,"关键信息不可为空");
        }else{
            //如果密码过长
            if(user.getPassword().length()<6 || user.getPassword().length()>=12 || user.getUsername().trim().length()<3 ){
                return Information.error(400,"密码长度必须在6-12之间;用户名长度不可少于3");
            }
            Integer userId = userService.addUser(user);
            if( userId>0){
                User newUser = userService.selectUserById(userId);
                return Information.success(200,"新用户",newUser);
//                if(multipartFile!=null){
//                    boolean result = false;
//                    try {
//                        result = UploadUtils.uploadFile(request,multipartFile, 2, newName);
//                        if(!result){
//                            return Information.error(410,"文件上传失败");
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        user.setImage(null);
//                        userService.updateUserById(user);
//                        return Information.error(410,"文件上传失败");
//                    }
//                    return Information.success("注册");
//
//                }

    //
            }else if(userId==-2) {
                return Information.error(402, "用户名不能重复");
            }else{
                return Information.error(400,"注册失败");
            }
        }
    }

    /**
     * 根据账号删除用户
     *
     * @return 结果
     */
    @GetMapping("user/delete")
    public Information deleteUser(HttpServletRequest request) {
        Integer userId=(Integer) request.getAttribute("userId");
        Integer result = userService.deleteUserById(userId);
        if(result>0){
            return Information.success("删除");
        }
        else {
            return Information.error(400,"删除失败");
        }
    }

    /**
     * 修改用户信息
     *
     * @param userDto 用户
     * @return 结果
     */
    @PostMapping("user/update")
    public Information updateUser(UserDto userDto,HttpServletRequest request) {
        Integer id=(Integer)request.getAttribute("userId");
        User user = userDto.getUser();
        user.setId(id);
        if(user.getId()==null){
            return Information.error(406,"关键信息不可为空");
        }else{
            //如果密码过长
            if(user.getPassword().length()<6 || user.getPassword().length()>=12 || user.getUsername().trim().length()<3 ){
                return Information.error(400,"密码长度必须在6-12之间;用户名长度不可少于3");
            }
            MultipartFile multipartFile = userDto.getMultipartFile();
            String newName="";
            if(multipartFile!=null){
                newName= UploadUtils.getNewName(multipartFile);
                if(newName==null){
                    return Information.error(410,"文件上传失败");
                }
                user.setImage(newName);
            }
            Integer result = userService.updateUserById(user);
            if(result>0){
                if(!newName.equals("")){
                    try {
                        UploadUtils.uploadFile(request,multipartFile,3,newName);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return Information.error(410,"上传文件失败");
                    }
                }
                User newUser = userService.selectUserById(user.getId());
                return Information.success(200,"更新用户资料",newUser);
            }else if(result==-2){
                return Information.error(402,"用户名重复");
            }
            else {
                return Information.error(400,"更新失败");
            }
        }
    }

    /**
     * 分页查询用户
     * @param page
     * @param size
     * @return
     */
    @GetMapping("manager/select/all/user/{page}/{size}")
    public Information selectAllUser(@PathVariable Integer page,@PathVariable Integer size){
        Integer total=userService.selectAllUserCount();
        if(total==null || total==0){
            return Information.error(204,"分页无内容");
        }
        Integer totalPage=total/size+1;
        Integer start=(page-1)*size;
        List<User> users= userService.selectAllUser(start, size);
        if(users==null){
            return Information.error(204,"分页无内容");
        }else{
            Page<User> userPage=new Page<>();
            userPage.setDatas(users);
            userPage.setTotalPage(totalPage);
            return Information.success(200,"用户列表",userPage);
        }
    }

}