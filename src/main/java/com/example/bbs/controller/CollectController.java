package com.example.bbs.controller;

import com.example.bbs.entity.*;
import com.example.bbs.service.CollectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    @GetMapping("select/post/user/{id}")
    public Information<Page> selectByUserId(@PathVariable Integer id, Integer page, Integer size) {
        Information<Page> information =new Information<>();
        if(id==null){
            information.setMsg("板块id不能为空");
            information.setStatus(406);
        }else{
            //总页数
            Integer totalPage=collectService.selectAllCollectCountByUserId(id)/size+1;

            Integer start=(page-1)*size;
            List<Post> posts= collectService.selectCollectPostByUserId(id,start, size);
            Page<Post> pageObject=new Page<>();
            pageObject.setDatas(posts);
            pageObject.setTotalPage(totalPage);
            if(posts!=null) {
                information.setData(pageObject);
                information.setMsg("收藏的帖子列表");
                information.setStatus(200);
            }else {
                information.setMsg("无");
                information.setStatus(204);
            }
        }
        return information;
    }

    /**
     * 查询收藏某个帖子的用户
     *
     * @param id 帖子id
     * @return 用户列表
     */
    @GetMapping("select/user/post/{id}")
    public Information selectByPostId(@PathVariable Integer id, Integer page, Integer size) {
        Information<Page> information =new Information<>();
        if(id==null){
            information.setMsg("板块id不能为空");
            information.setStatus(406);
        }else{
            //总页数
            Integer totalPage=collectService.selectAllCollectCountByPostId(id)/size+1;

            Integer start=(page-1)*size;
            List<User> users= collectService.selectCollectUserByPostId(id,start, size);
            Page<User> pageObject=new Page<>();
            pageObject.setDatas(users);
            pageObject.setTotalPage(totalPage);
            if(users!=null) {
                information.setData(pageObject);
                information.setMsg("收藏者列表");
                information.setStatus(200);
            }else {
                information.setMsg("无");
                information.setStatus(204);
            }
        }
        return information;
    }

    /**
     * 添加收藏记录
     * 帖子的收藏数量也要增加1
     * userId postId
     * @param collect 收藏记录
     * @return 返回主键值
     */
    @PostMapping("add")
    public Information addCollect(Collect collect, HttpSession session){
        Information<Integer> information =new Information<>();
        Object admin_session = session.getAttribute("admin_session");
        Object role_session = session.getAttribute("role_session");
        String msg="";
        Integer status=202;
        if(admin_session==null && role_session==null){
            msg="权限不足";
            status=401;
        }else if (collect.getUserId()==null  && collect.getPostId()==null) {
            msg = "必需信息为空";
            status=406;
        }else{
            Integer collectId = collectService.addCollect(collect);
            information.setData(collectId);
            if(collectId==null || collectId==0){
                msg="添加失败";
                status=400;
            }
            else if (collectId==-5) {
                msg = "所添加目标帖子不存在";
                status=407;
            }
            else if (collectId==-3) {
                msg = "创建用户不存在";
                status=404;
            }
            else {
                msg = "创建成功";
                status=200;
            }
        }
        information.setMsg(msg);
        information.setStatus(status);
        return information;
    }

    /**
     * 删除单个收藏记录
     *
     * @param id 收藏记录
     * @return 结果
     */
    @DeleteMapping("delete/{id}")
    public Information deleteCollect(@PathVariable Integer id,HttpSession session) {
        Admin admin_session =(Admin) session.getAttribute("admin_session");
        Role role_session=(Role)session.getAttribute("role_session");
        Information<Integer> information =new Information<>();
        String msg="";
        Integer status=202;
        if(admin_session==null && role_session==null){
            msg="权限不足";
            status=401;
        }else{
            Integer result = collectService.deleteCollect(id);
            if(result>0){
                msg="删除成功";
                status=200;
            }
            else {
                msg = "删除失败";
                status=400;
            }
            information.setData(result);
        }
        information.setStatus(status);
        information.setMsg(msg);
        return information;
    }

    /**
     * 删除某个用户所有的记录
     *
     * @param id 用户id
     * @return 结果
     */
    @GetMapping("deleteAllCollectByUserId")
    public Information deleteAllCollect(Integer id,HttpSession session) {
        Admin admin_session =(Admin) session.getAttribute("admin_session");
        Role role_session=(Role)session.getAttribute("role_session");
        Information<Integer> information =new Information<>();
        String msg="";
        Integer status=202;
        if(admin_session==null && role_session==null){
            msg="权限不足";
            status=401;
        }else{
            Integer result = collectService.deleteCollectByUserId(id);
            if(result>0){
                msg="删除成功";
                status=200;
            }
            else {
                msg = "删除失败";
                status=400;
            }
            information.setData(result);
        }
        information.setStatus(status);
        information.setMsg(msg);
        return information;
    }




}