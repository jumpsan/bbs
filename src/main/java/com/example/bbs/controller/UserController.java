package com.example.bbs.controller;

import com.example.bbs.dto.UserDto;
import com.example.bbs.entity.Information;
import com.example.bbs.entity.Page;
import com.example.bbs.entity.User;
import com.example.bbs.entity.UserForManager;
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
     * @return
     */
    @GetMapping("user/select")
    public Information selectUserById(HttpServletRequest request) {
        Integer userId=(Integer)request.getAttribute("userId");
        System.out.println(userId);
        if(userId==null){
            return Information.error(406,"关键信息不可为空");
        }else {
            User user = userService.selectUserById(userId);
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
    public Information selectUserByNameAndPassword(String username, String password,HttpServletRequest request) {
        User user = userService.selectUserByNameAndPassword(username, password);
        if (user == null) {
            return Information.error(202,"用户名或密码错误");
        } else {
            try {
                String token = JjwtUtils.createJWT(user.getId(), 15 * 60 * 10000);
                System.out.println(request.getSession().getServletContext() .getRealPath("/"));
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
                newUser.setPassword("*");
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
     * @param user 用户
     * @return 结果
     */
    @PostMapping("user/update")
    public Information updateUser(User user,HttpServletRequest request) {
        Integer id=(Integer)request.getAttribute("userId");
        //User user = userDto.getUser();
        user.setId(id);
        if(user.getId()==null){
            return Information.error(406,"关键信息不可为空");
        }else{
            //如果密码过长
            if((user.getPassword()!=null && (user.getPassword().length()<6 || user.getPassword().length()>=12)) || (user.getUsername()!=null && user.getUsername().trim().length()<3) ){
                return Information.error(400,"密码长度必须在6-12之间;用户名长度不可少于3");
            }
            user.setLimitPost(null);
            user.setLimitReply(null);
            //MultipartFile multipartFile = user.getMultipartFile();
            String newName="";
//            if(multipartFile!=null){
//                newName= UploadUtils.getNewName(multipartFile);
//                if(newName==null){
//                    return Information.error(410,"文件上传失败");
//                }
//                user.setImage(newName);
//            }
            Integer result = userService.updateUserById(user);
            if(result>0){
//                if(!newName.equals("")){
//                    try {
//                        UploadUtils.uploadFile(multipartFile,3,newName);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        return Information.error(410,"上传文件失败");
//                    }
//                }
                //转移图片
                if(user.getImage()!=null){
                    UploadUtils.transferFile(2,user.getImage());
                }
                User newUser = userService.selectUserById(user.getId());
                return Information.success(200,"更新用户资料",newUser);
            }else if(result==-2){
                return Information.error(402,"用户名重复");
            }else if(result==-3){
                return Information.error(404,"用户不存在");
            } else {
                return Information.error(400,"更新失败");
            }
        }
    }

    /**
     * 分页查询不在黑名单中的用户
     * @param page
     * @param size
     * @return
     */
    @GetMapping("manager/select/all/user/{page}/{size}")
    public Information selectAllUserExceptBlacklist(@PathVariable Integer page,@PathVariable Integer size){
        Integer total=userService.selectAllUserCount();
        if(total==null || total==0){
            return Information.error(204,"分页无内容");
        }
        Integer totalPage=total/size+1;
        Integer start=(page-1)*size+1;
        List<User> users= userService.selectAllUser(start, size);
        if(users==null){
            return Information.error(204,"分页无内容");
        }else{
            Page<User> userPage=new Page<>();
            userPage.setDatas(users);
            userPage.setTotalPage(totalPage);
            userPage.setTotalNum(total);
            return Information.success(200,"用户列表",userPage);
        }
    }



    /**
     * 分页查询在黑名单中的用户
     * @param page
     * @param size
     * @return
     */
    @GetMapping("manager/select/blacklist/user/{page}/{size}")
    public Information selectUserWithPermission(@PathVariable Integer page,@PathVariable Integer size){
        Integer total=userService.selectUserInBlacklistCount();
        if(total==null || total==0){
            return Information.error(204,"分页无内容");
        }
        Integer totalPage=total/size+1;
        Integer start=(page-1)*size+1;
        List<User> users= userService.selectUserInBlacklist(start, size);
        if(users==null){
            return Information.error(204,"分页无内容");
        }else{
            Page<User> userPage=new Page<>();
            userPage.setDatas(users);
            userPage.setTotalPage(totalPage);
            userPage.setTotalNum(total);
            return Information.success(200,"用户列表",userPage);
        }
    }

    /**
     * manager根据账号删除用户
     *
     * @return 结果
     */
    @GetMapping("manager/user/delete/{userId}")
    public Information deleteUserForManager(@PathVariable Integer userId) {
        Integer result = userService.deleteUserById(userId);
        if(result>0){
            return Information.success("删除");
        }else if(result==-3){
            return Information.error(404,"用户不存在");
        }
        else {
            return Information.error(400,"删除失败");
        }
    }


    /**
     * 修改用户信息
     *
     * @param user 用户
     * @return 结果
     */
    @PostMapping("manager/user/update")
    public Information updateUserForManager(UserForManager user, HttpServletRequest request) {
        //Integer id=(Integer)request.getAttribute("userId");
        //User user = userDto.getUser();
        if(user.getId()==null){
            return Information.error(406,"关键信息不可为空");
        }else{
            //如果密码过长
            if((user.getPassword()!=null && (user.getPassword().length()<6 || user.getPassword().length()>=12)) || (user.getUsername()!=null && user.getUsername().trim().length()<3) ){
                return Information.error(400,"密码长度必须在6-12之间;用户名长度不可少于3");
            }
            //MultipartFile multipartFile = user.getMultipartFile();
            //           String newName="";
//            if(multipartFile!=null){
//                newName= UploadUtils.getNewName(multipartFile);
//                if(newName==null){
//                    return Information.error(410,"文件上传失败");
//                }
//                user.setImage(newName);
//            }
            Integer result = userService.updateUserByIdForManager(user);
            if(result>0){
//                if(!newName.equals("")){
//                    try {
//                        UploadUtils.uploadFile(multipartFile,3,newName);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        return Information.error(410,"上传文件失败");
//                    }
//                }
                if(user.getImage()!=null){
                    UploadUtils.transferFile(2,user.getImage());
                }
                User newUser = userService.selectUserByIdForManager(user.getId());
                return Information.success(200,"更新用户资料",newUser);
            }else if(result==-2){
                return Information.error(402,"用户名重复");
            }else if(result==-3){
                return Information.error(404,"用户不存在");
            }else if(result==-7){
                return Information.error(400,"修改黑名单失败");
            } else {
                return Information.error(400,"更新失败");
            }
        }
    }

    /**
     * 根据账号查询
     *
     * @param id
     * @return
     */
    @GetMapping("manager/user/select/{id}")
    public Information selectUserByIdForManager(@PathVariable Integer id) {
        if(id==null){
            return Information.error(406,"关键信息不可为空");
        }else {
            User user = userService.selectUserByIdForManager(id);
            if(user==null){
                return Information.error(204,"无内容");
            }else{
                return Information.success(200,"用户",user);
            }
        }
    }


    /**
     * 修改用户信息
     *
     * @param username 用户
     * @return 结果
     */
    @PostMapping("user/update/username")
    public Information updateUserForName(String username, HttpServletRequest request) {
        Integer userId=(Integer)request.getAttribute("userId");
        if(userId==null){
            return Information.error(406,"关键信息不可为空");
        }else{
            //如果密码过长
            if( (username!=null && username.trim().length()<3) ){
                return Information.error(400,"密码长度必须在6-12之间;用户名长度不可少于3");
            }
            User user=new User();
            user.setUsername(username);
            Integer result = userService.updateUserByIdForManager(user);
            if(result>0){
                User newUser = userService.selectUserByIdForManager(user.getId());
                return Information.success(200,"更新用户资料",newUser);
            }else if(result==-2){
                return Information.error(402,"用户名重复");
            }else if(result==-3){
                return Information.error(404,"用户不存在");
            }else {
                return Information.error(400,"更新失败");
            }
        }
    }

//
//    /**
//     * 修改用户信息
//     *
//     * @param introduce 用户
//     * @return 结果
//     */
//    @PostMapping("user/update/introduce")
//    public Information updateUserForIntroduce(String introduce, HttpServletRequest request) {
//        Integer userId=(Integer)request.getAttribute("userId");
//        if(userId==null){
//            return Information.error(406,"关键信息不可为空");
//        }else{
//            //如果密码过长
//            if( (introduce!=null && introduce.trim().length()<3) ){
//                return Information.error(400,"密码长度必须在6-12之间;用户名长度不可少于3");
//            }
//            User user=new User();
//            user.setUsername(username);
//            Integer result = userService.updateUserByIdForManager(user);
//            if(result>0){
//                User newUser = userService.selectUserByIdForManager(user.getId());
//                return Information.success(200,"更新用户资料",newUser);
//            }else if(result==-2){
//                return Information.error(402,"用户名重复");
//            }else if(result==-3){
//                return Information.error(404,"用户不存在");
//            }else {
//                return Information.error(400,"更新失败");
//            }
//        }
//    }

}