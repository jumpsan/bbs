package com.example.bbs.service.impl;

import com.example.bbs.dao.FollowDao;
import com.example.bbs.dao.UserDao;
import com.example.bbs.entity.Follow;
import com.example.bbs.entity.Information;
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
@Service("followedService")
public class FollowServiceImpl implements FollowService {
    @Resource
    private FollowDao followDao;
    @Resource
    private UserDao userDao;
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
     * @param follow 关注
     * @return 主键值
     */
    @Override
    public Integer addFollow(Follow follow) {
        Integer followedId = follow.getFollowedId();
        User user = userDao.selectUserById(followedId);
        if(user==null){
            return -3;
        }
        Follow checkFollow = followDao.selectAllCountByFollowIdAndFollowedId(follow.getFollowId(), followedId);
        if(checkFollow!=null){
            return -2;//重复关注
        }
        Integer result = followDao.addFollow(follow);
        if(result<=0){
            return -7;
        }
        return follow.getId();
    }

    /**
     * 取关
     *
     * @param follow 关注
     * @return 结果
     */
    @Override
    public Integer deleteFollow(Follow follow) {
        return followDao.deleteFollow(follow);
    }

    /**
     * 根据关注者id删除
     *
     * @param followId 关注者Id
     * @return 结果
     */
    @Override
    public Integer deleteByFollowId(Integer followId) {
        return followDao.deleteByFollowId(followId);
    }

    /**
     * 根据被关注者id删除
     *
     * @param followedId 被关注者
     * @return 结果
     */
    @Override
    public Integer deleteByFollowedId(Integer followedId) {
        return followDao.deleteByFollowedId(followedId) ;
    }

    @Override
    public Integer selectAllCountByFollowId(Integer id) {
        return followDao.selectAllCountByFollowId(id);
    }

    @Override
    public Integer selectAllCountByFollowedId(Integer id) {
        return followDao.selectAllCountByFollowedId(id);
    }

    /**
     * 根据编号查询
     *
     * @param id
     * @return
     */
    @Override
    public Follow selectById(Integer id) {
        return followDao.selectById(id);
    }
}