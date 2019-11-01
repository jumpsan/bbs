package com.example.bbs.controller;

import com.example.bbs.annotation.AuthChecker;
import com.example.bbs.entity.*;
import com.example.bbs.service.CollectService;
import com.example.bbs.utils.Authorization;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * (TCollect)表控制层
 *
 * @author makejava
 * @since 2019-09-20 13:48:52
 */
@RestController
@RequestMapping("collect")
public class CollectController {
    /**
     * 服务对象
     */
    @Resource  //按照名称寻找Bean
    private CollectService collectService;

    /**
     * 查询某个用户所收藏的帖子
     *
     * @param id 收藏的用户
     * @return 帖子列表
     */
    @GetMapping("select/post/user/{id}/{page}/{size}")
    public Information selectByUserId(@PathVariable Integer id, @PathVariable Integer page,@PathVariable Integer size) {
        if(id==null){
            return Information.error(406,"关键信息不可为空和");
        }else{
            //总页数
            Integer total=collectService.selectAllCollectCountByUserId(id);
            if(total==null || total==0){
                return Information.success(204,"无记录",null);
            }else{
                Integer totalPage=total/size+1;
                Integer start=(page-1)*size+1;
                List<Post> posts= collectService.selectCollectPostByUserId(id,start, size);
                Page<Post> pageObject=new Page<>();
                pageObject.setDatas(posts);
                pageObject.setTotalPage(totalPage);
                if(posts!=null) {
                    return Information.success(200,"收藏的帖子列表",pageObject);
                }else {
                    return Information.success(204,"无记录",null);
                }
            }
        }
    }

    /**
     * 查询收藏某个帖子的用户
     *
     * @param id 帖子id
     * @return 用户列表
     */
    @GetMapping("select/user/post/{id}/{page}/{size}")
    public Information selectByPostId(@PathVariable Integer id,@PathVariable Integer page,@PathVariable Integer size) {
        if(id==null || page==null || size==null || page<0 || size<0){
            return Information.error(406,"关键信息不可为空");
        }else{
            //总页数
            Integer total=collectService.selectAllCollectCountByPostId(id);
            if(total==null || total==0){
                return Information.success(204,"无记录",null);
            }
            Integer totalPage=total/size+1;
            Integer start=(page-1)*size+1;
            List<User> users= collectService.selectCollectUserByPostId(id,start, size);
            Page<User> userPage=new Page<>();
            userPage.setDatas(users);
            userPage.setTotalPage(totalPage);
            if(users!=null) {
                return Information.success(200,"收藏者列表",userPage);
            }else {
                return Information.success(204,"无记录",null);
            }
        }
    }

    /**
     * 根据状态码修改收藏状态
     * @param status
     * @param postId
     * @param request
     * @return
     */
    @PostMapping("update")
    public Information updateCollect( Integer status, Integer postId,HttpServletRequest request){
        Integer userId =(Integer) request.getAttribute("userId");
        if (userId==null  && postId==null) {
            return Information.error(406,"必须信息为空");
        }
        //判断状态
        if(status==1){
            Collect collect=new Collect();
            collect.setPostId(postId);
            collect.setUserId(userId);
            Integer collectId = collectService.addCollect(collect);
            if(collectId==null || collectId==0){
                return Information.error(400,"添加失败");
            }
            else if (collectId==-5) {
                return Information.error(407,"所添加目标帖子不存在");
            }
            else if (collectId==-3) {
                return Information.error(404,"创建用户不存在");
            }else if(collectId==-2){
                return Information.error(402,"添加重复");
            }
            else {
                return Information.success(200,"添加成功",1);
            }
        }else{
            Integer result = collectService.deleteCollectByUserIdAndPostId(userId,postId);
            if(result>0){
                return Information.success(200,"删除成功",0);
            }
            else {
                return Information.error(400,"删除失败");
            }
        }
    }



//    /**
//     * 添加收藏记录
//     * 帖子的收藏数量也要增加1
//     * userId postId
//     * @param postId 收藏记录
//     * @return 返回主键值
//     */
//    @PostMapping("add")
//    public Information addCollect(Integer postId,HttpServletRequest request){
//        Integer userId =(Integer) request.getAttribute("userId");
//        if (userId==null  && postId==null) {
//            return Information.error(406,"必须信息为空");
//        }else{
//            Collect collect=new Collect();
//            collect.setPostId(postId);
//            collect.setUserId(userId);
//            Integer collectId = collectService.addCollect(collect);
//            if(collectId==null || collectId==0){
//                return Information.error(400,"添加失败");
//            }
//            else if (collectId==-5) {
//                return Information.error(407,"所添加目标帖子不存在");
//            }
//            else if (collectId==-3) {
//                return Information.error(404,"创建用户不存在");
//            }else if(collectId==-2){
//                return Information.error(402,"添加重复");
//            }
//            else {
//                Collect newCollect = collectService.selectCollectById(collectId);
//                return Information.success(200,"添加成功",newCollect);
//            }
//        }
//    }

//    /**
//     * 删除单个收藏记录
//     *
//     * @param id 收藏记录
//     * @return 结果
//     */
//    @DeleteMapping("delete/{id}")
//    public Information deleteCollect(@PathVariable Integer id, HttpServletRequest request) {
//        Information<Integer> information =new Information<>();
//        String msg="";
//        Integer status=202;
//        Collect collect = collectService.selectCollectById(id);
//        boolean verify = Authorization.verify(request, collect.getUserId());
//        if(!verify){
//            msg="非法操作";
//            status=411;
//        }else{
//            Integer result = collectService.deleteCollect(id);
//            if(result>0){
//                msg="删除成功";
//                status=200;
//                information.setData(result);
//            }
//            else {
//                msg = "删除失败";
//                status=400;
//            }
//        }
//        information.setStatus(status);
//        information.setMsg(msg);
//        return information;
//    }

//    /**
//     * 根据用户以及帖子删除收藏记录
//     * @param postId 帖子
//     * @return 信息
//     */
//    @GetMapping("delete/user/post/{postId}")
//    public Information deleteCollectByUserIdAndPostId(@PathVariable Integer postId,HttpServletRequest request) {
//        Integer id=(Integer)request.getAttribute("userId");
//        if(id==null || postId==null){
//            return Information.error(406,"关键信息为空");
//        }
//        Integer result = collectService.deleteCollectByUserIdAndPostId(id,postId);
//        if(result>0){
//            return Information.success("删除");
//        }
//        else {
//            return Information.error(400,"删除失败");
//        }
//    }
}