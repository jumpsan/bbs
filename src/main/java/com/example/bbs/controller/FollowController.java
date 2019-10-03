package com.example.bbs.controller;


import com.example.bbs.entity.Follow;
import com.example.bbs.entity.User;
import com.example.bbs.service.FollowService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TFollowed)表控制层
 *
 * @author makejava
 * @since 2019-09-20 13:59:33
 */
@RestController
@RequestMapping("follow")  //用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
public class FollowController {
    /**
     * 服务对象
     */
    @Resource
    private FollowService followService;

    /**
     * 查找某个用户所关注的人
     *
     * @param follow_id 关注人
     * @return 关注列表
     */
    @GetMapping("selectByFollowId")
    public List<User> selectByFollowId(Integer follow_id, Integer start, Integer num) {

        return followService.selectByFollowId(follow_id, start, num);
    }

    /**
     * 查找粉丝
     *
     * @param followed_id 被关注人
     * @return 粉丝列表
     */
    @GetMapping("selectByFollowedId")
    public List<User> selectByFollowedId(Integer followed_id, Integer start, Integer num) {

        return followService.selectByFollowedId(followed_id, start, num);
    }

    /**
     * 添加关注记录
     *
     * @param followed 关注记录
     * @return 返回主键值
     */
    @GetMapping("addFollow")
    public Integer addFollow(Follow followed) {

        return followService.addFollow(followed);
    }

    /**
     * 取消关注时
     *
     * @param followed 关注记录
     * @return 结果
     */
    @GetMapping("deleteFollow")
    public boolean deleteFollow(Follow followed) {
        return followService.deleteFollow(followed);
    }

    /**
     * 某个用户取关所有人
     *
     * @param follow_id 关注人
     * @return 结果
     */
    @GetMapping("deleteByFollowId")
    public boolean deleteByFollowId(Integer follow_id) {
        return followService.deleteByFollowId(follow_id);
    }

    /**
     * 某个用户除所有粉丝
     *
     * @param followed_id 被关注人
     * @return 结果
     */
    @GetMapping("deleteByFollowedId")
    public boolean deleteByFollowedId(Integer followed_id) {
        return followService.deleteByFollowedId(followed_id);
    }


}