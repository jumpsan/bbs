package com.example.bbs.controller;

import com.example.bbs.entity.*;
import com.example.bbs.service.PostService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * (TPost)表控制层
 *
 * @author makejava
 * @since 2019-09-20 14:00:32
 */
@RestController
@RequestMapping("post")
public class PostController {
    /**
     * 服务对象
     */
    @Resource
    private PostService postService;

    /**
     * 根据分区编号查询帖子
     *
     * @param id    分区编号
     * @param page 距离第一行的偏移量
     * @param size   行数
     * @return 帖子列表
     */
    @GetMapping("select/section/{id}/{page}/{size}")
    public Information<Page> selectPostBySectionId(@PathVariable Integer id, @PathVariable Integer page, @PathVariable Integer size) {
        Information<Page> information =new Information<>();
        if(id==null){
            information.setMsg("分区id不能为空");
            information.setStatus(406);
        }else{
            //总页数
            Integer totalPage=postService.selectAllPostBySectionId(id)/size+1;

            Integer start=(page-1)*size;
            List<Post> posts= postService.selectPostBySectionId(id,start, size);
            Page<Post> pageObject=new Page<>();
            pageObject.setDatas(posts);
            pageObject.setTotalPage(totalPage);
            if(posts!=null) {
                information.setData(pageObject);
                information.setMsg("帖子列表");
                information.setStatus(200);
            }else {
                information.setMsg("无");
                information.setStatus(204);
            }
        }
        return information;
    }


    /**
     * 根据用户id查询帖子
     *
     * @param id    用户id
     * @param page 距离第一行的偏移量
     * @param size   行数
     * @return 帖子列表
     */
    @GetMapping("select/user/{id}/{page}/{size}")
    public Information<Page> selectPostByUserId(@PathVariable Integer id, @PathVariable Integer page, @PathVariable Integer size) {
        Information<Page> information =new Information<>();
        if(id==null){
            information.setMsg("用户id不能为空");
            information.setStatus(406);
        }else{
            //总页数
            Integer totalPage=postService.selectAllPostByUserId(id)/size+1;

            Integer start=(page-1)*size;
            List<Post> posts= postService.selectPostByUserId(id,start, size);
            Page<Post> pageObject=new Page<>();
            pageObject.setDatas(posts);
            pageObject.setTotalPage(totalPage);
            if(posts!=null) {
                information.setData(pageObject);
                information.setMsg("帖子列表");
                information.setStatus(200);
            }else {
                information.setMsg("无");
                information.setStatus(204);
            }
        }
        return information;
    }

    /**
     * 根据类型查找帖子
     *
     * @param type  类型编号，0：图片或文字，1：视频
     * @param page 距离第一行的偏移量
     * @param size   行数
     * @return 帖子列表
     */
    @GetMapping("select/type/{type}/{page}/{size}")
    public Information<Page> selectPostByType(@PathVariable Integer type, @PathVariable Integer page,@PathVariable Integer size) {
        Information<Page> information =new Information<>();
        if(type==null){
            information.setMsg("类型不能为空");
            information.setStatus(406);
        }else{
            //总页数
            Integer totalPage=postService.selectAllPostByType(type)/size+1;

            Integer start=(page-1)*size;
            List<Post> posts= postService.selectPostByType(type,start, size);
            Page<Post> pageObject=new Page<>();
            pageObject.setDatas(posts);
            pageObject.setTotalPage(totalPage);
            if(posts!=null) {
                information.setData(pageObject);
                information.setMsg("帖子列表");
                information.setStatus(200);
            }else {
                information.setMsg("无");
                information.setStatus(204);
            }
        }
        return information;
    }

    /**
     * 根据帖子id查询
     *
     * @param id 帖子id
     * @return 帖子
     */
    @GetMapping("select/{id}")
    public Information<Post> selectPostById(@PathVariable Integer id) {
        Information<Post> information=new Information<>();
        if(id==null){
            information.setMsg("帖子id不能为空");
            information.setStatus(406);
        }else{
            Post post = postService.selectPostById(id);
            if(post!=null) {
                information.setData(post);
                information.setStatus(200);
                information.setMsg("分区");
            }else {
                information.setMsg("无");
                information.setStatus(204);
            }
        }
        return information;

    }

    /**
     * 根据标题模糊查询
     *
     * @param title 标题
     * @param page 距离第一行的偏移量
     * @param size   行数
     * @return 帖子列表
     */
    @GetMapping("select/title")
    public Information<Page> selectPostByTile(String title, Integer page, Integer size) {
        Information<Page> information =new Information<>();
        if(title==null){
            information.setMsg("标题不能为空");
            information.setStatus(406);
        }else{
            //总页数
            Integer totalPage=postService.selectAllPostByTitle(title)/size+1;

            Integer start=(page-1)*size;
            List<Post> posts= postService.selectPostByTitle(title, start, size);
            Page<Post> pageObject=new Page<>();
            pageObject.setDatas(posts);
            pageObject.setTotalPage(totalPage);
            if(posts!=null) {
                information.setData(pageObject);
                information.setMsg("帖子列表");
                information.setStatus(200);
            }else {
                information.setMsg("无");
                information.setStatus(204);
            }
        }
        return information;
    }

    /**
     * 添加图片类型帖子
     * 0 文字、图片 1 视频
     * @param post 帖子
     * @return 结果
     */
    @PostMapping("add/image")
    public Information addImagePost(Post post, HttpSession session) {
        Information<Integer> information =new Information<>();
        Object admin_session = session.getAttribute("admin_session");
        Object role_session = session.getAttribute("role_session");
        String msg="";
        Integer status=202;
        if(admin_session==null && role_session==null){
            msg="权限不足";
            status=401;
        }else if (post.getUserId()==null && post.getSectionId()!=null && post.getTitile()==null  && post.getStatus()!=null) {
            msg = "关键信息缺失，无法添加";
            status=406;
        }else if(post.getVideo()!=null && post.getImage()==null){
            msg="上传内容与帖子类型不符合";
            status=409;
        }else{
            post.setType(0);
            Integer postId = postService.addImagePost(post);
            information.setData(postId);
            if(postId==-6){
                msg = "无权发帖";
                status=401;
            }
            else if (postId==-3) {
                msg = "创建用户不存在";
                status=404;
            }
            else if (postId==-4) {
                msg = "分区被禁用，不允许操作";
                status=405;
            }else if (postId==-5) {
                msg = "所添加目标分区不存在";
                status=407;
            } else if(postId==0){
                msg="添加失败";
                status=400;
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

    @PostMapping("add/word")
    public Information addWordPost(Post post, HttpSession session) {
        Information<Integer> information =new Information<>();
        Object admin_session = session.getAttribute("admin_session");
        Object role_session = session.getAttribute("role_session");
        String msg="";
        Integer status=202;
        if(admin_session==null && role_session==null){
            msg="权限不足";
            status=401;
        }else if (post.getUserId()==null && post.getSectionId()!=null && post.getTitile()==null  && post.getStatus()!=null) {
            msg = "关键信息缺失，无法添加";
            status=406;
        }else if(post.getVideo()!=null && post.getImage()!=null){
            msg="上传内容与帖子类型不符合";
            status=409;
        }else{
            post.setType(0);
            Integer postId = postService.addWordPost(post);
            information.setData(postId);
            if(postId==-6){
                msg = "无权发帖";
                status=401;
            }
            else if (postId==-3) {
                msg = "创建用户不存在";
                status=404;
            }
            else if (postId==-4) {
                msg = "分区被禁用，不允许操作";
                status=405;
            }else if (postId==-5) {
                msg = "所添加目标分区不存在";
                status=407;
            } else if(postId==0){
                msg="添加失败";
                status=400;
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


    @PostMapping("add/video")
    public Information addVideoPost(Post post, HttpSession session) {
        Information<Integer> information =new Information<>();
        Object admin_session = session.getAttribute("admin_session");
        Object role_session = session.getAttribute("role_session");
        String msg="";
        Integer status=202;
        if(admin_session==null && role_session==null){
            msg="权限不足";
            status=401;
        }else if (post.getUserId()==null && post.getSectionId()!=null && post.getTitile()==null  && post.getStatus()!=null) {
            msg = "关键信息缺失，无法添加";
            status=406;
        }else if(post.getVideo()==null && post.getImage()!=null){
            msg="上传内容与帖子类型不符合";
            status=409;
        }else{
            post.setType(1);
            Integer postId = postService.addVideoPost(post);
            information.setData(postId);
            if(postId==-6){
                msg = "无权发帖";
                status=401;
            }
            else if (postId==-3) {
                msg = "创建用户不存在";
                status=404;
            }
            else if (postId==-4) {
                msg = "分区被禁用，不允许操作";
                status=405;
            }else if (postId==-5) {
                msg = "所添加目标分区不存在";
                status=407;
            } else if(postId==0){
                msg="添加失败";
                status=400;
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
     * 根据帖子id删除帖子
     *
     * @param id 帖子id
     * @return 结果
     */
    @DeleteMapping("delete/{id}")
    public Information deletePost(@PathVariable Integer id,HttpSession session) {
        Admin admin_session =(Admin) session.getAttribute("admin_session");
        Role role_session=(Role)session.getAttribute("role_session");
        Information<Integer> information =new Information<>();
        String msg="";
        Integer status=202;
        if(admin_session==null && role_session==null){
            msg="权限不足";
            status=401;
        }else{
            Integer result = postService.deletePostById(id);
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
     * 根据用户id删除帖子
     *
     * @param id 用户id
     * @return 结果
     */
    @DeleteMapping("delete/user/{id}")
    public Information deletePostByUserId(@PathVariable Integer id, HttpSession session) {
        Admin admin_session =(Admin) session.getAttribute("admin_session");
        Role role_session=(Role)session.getAttribute("role_session");
        Information<Integer> information =new Information<>();
        String msg="";
        Integer status=202;
        if(admin_session==null && role_session==null){
            msg="权限不足";
            status=401;
        }else{
            Integer result = postService.deletePostByUserId(id);
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
     * 根据分区id删除帖子
     *
     * @param id 分区id
     * @return 结果
     */
    @DeleteMapping("delete/section/{id}")
    public Information deletePostBySectionId(@PathVariable Integer id,HttpSession session) {
        Admin admin_session =(Admin) session.getAttribute("admin_session");
        Role role_session=(Role)session.getAttribute("role_session");
        Information<Integer> information =new Information<>();
        String msg="";
        Integer status=202;
        if(admin_session==null && role_session==null){
            msg="权限不足";
            status=401;
        }else{
            Integer result = postService.deletePostBySectionId(id);
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
     * 根据类型删除
     *
     * @param type 类型
     * @return 结果
     */
    @DeleteMapping("delete/type/{type}")
    public Information deletePostByType(@PathVariable Integer type,HttpSession session) {
        Admin admin_session =(Admin) session.getAttribute("admin_session");
        Role role_session=(Role)session.getAttribute("role_session");
        Information<Integer> information =new Information<>();
        String msg="";
        Integer status=202;
        if(admin_session==null && role_session==null){
            msg="权限不足";
            status=401;
        }else{
            Integer result = postService.deletePostByType(type);
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
     * 修改帖子内容
     * 只能修改title, content
     * @param post 帖子
     * @return 结果
     */
    @PutMapping("update/{id}")
    public Information updatePost(@PathVariable Integer id,Post post, HttpSession session) {
        Information<Integer> information=new Information<>();
        Admin admin_session =(Admin) session.getAttribute("admin_session");
        Role role_session=(Role)session.getAttribute("role_session");
        if(admin_session==null && role_session==null){
            information.setMsg("权限不足");
            information.setStatus(401);
        }else if(post.getId()==null){
            information.setMsg("帖子id不能为空");
            information.setStatus(403);
        }else {
            Integer re=postService.updatePost(post);
            if(re==null || re==0){
                information.setMsg("更新失败");
                information.setStatus(400);
            }else{
                information.setMsg("更新成功");
                information.setStatus(200);
            }
            information.setData(re);
        }
        return information;
    }


}