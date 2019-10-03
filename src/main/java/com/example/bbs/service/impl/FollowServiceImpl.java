package com.example.bbs.service.impl;

import com.example.bbs.dao.FollowDao;
import com.example.bbs.entity.Follow;
import com.example.bbs.entity.User;
import com.example.bbs.service.FollowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TFollowed)表服务实现类
 *
 * @author makejava
 * @since 2019-09-20 13:59:33
 */
@Service("tFollowedService")
public class FollowServiceImpl implements FollowService {
    @Resource
    private FollowDao followDao;

    /**
     * 某个用户的关注列表
     *
     * @param follow_id 关注者Id
     * @param start     偏移量
     * @param num       行数
     * @return 被关注者列表
     */
    @Override
    public List<User> selectByFollowId(Integer follow_id, Integer start, Integer num) {
        return followDao.selectByFollowId(follow_id, start, num);
    }

    /**
     * 查看粉丝列表
     *
     * @param followed_id 被关注者id
     * @param start       偏移量
     * @param num         行数
     * @return 粉丝列表
     */
    @Override
    public List<User> selectByFollowedId(Integer followed_id, Integer start, Integer num) {
        return followDao.selectByFollowedId(followed_id, start, num);
    }

    /**
     * 添加关注记录
     *
     * @param followed 关注
     * @return 主键值
     */
    @Override
    public Integer addFollow(Follow followed) {
        followed = followDao.addFollow(followed);
        return followed.getId();
    }

    /**
     * 取关
     *
     * @param follow 关注
     * @return 结果
     */
    @Override
    public boolean deleteFollow(Follow follow) {
        return followDao.deleteFollow(follow) > 0;
    }

    /**
     * 根据关注者id删除
     *
     * @param follow_id 关注者Id
     * @return 结果
     */
    @Override
    public boolean deleteByFollowId(Integer follow_id) {
        return followDao.deleteByFollowId(follow_id) > 0;
    }

    /**
     * 根据被关注者id删除
     *
     * @param followed_id 被关注者
     * @return 结果
     */
    @Override
    public boolean deleteByFollowedId(Integer followed_id) {
        return followDao.deleteByFollowedId(followed_id) > 0;
    }
}