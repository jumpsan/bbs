package com.example.bbs.service.impl;

import com.example.bbs.dao.CollectDao;
import com.example.bbs.dao.PostDao;
import com.example.bbs.dao.UserDao;
import com.example.bbs.entity.Collect;
import com.example.bbs.entity.Post;
import com.example.bbs.entity.User;
import com.example.bbs.service.CollectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TCollect)表服务实现类
 *
 * @author makejava
 * @since 2019-09-20 13:48:47
 */
@Service("CollectService")
public class CollectServiceImpl implements CollectService {
    @Resource
    private CollectDao collectDao;
    @Resource
    private UserDao userDao;
    @Resource
    private PostDao postDao;
    /**
     * 返回某个用户收藏的帖子
     *
     * @param id 用户id
     * @return 收藏列表
     */
    @Override
    public List<Post> selectCollectPostByUserId(Integer id, Integer start, Integer num) {
        return this.collectDao.selectCollectPostByUserId(id, start, num);
    }

    /**
     * 返回收藏该帖的用户
     *
     * @param id 帖子id
     * @param start   偏移量
     * @param num     行数
     * @return 用户列表
     */
    @Override
    public List<User> selectCollectUserByPostId(Integer id, Integer start, Integer num) {
        return this.collectDao.selectCollectUserByPostId(id, start, num);
    }

    /**
     * 添加收藏
     * userId postId
     * @param collect 收藏
     * @return 主键值
     */
    @Override
    public Integer addCollect(Collect collect) {
        User user = userDao.selectUserById(collect.getUserId());
        Post post = postDao.selectPostById(collect.getPostId());
        if(user==null){
            return -3;
        }
        if(post==null)
            return -5;
        Collect checkCollect = collectDao.selectCollectUserIdAndPostId(user.getId(), post.getId());
        if(checkCollect!=null){
            return -2;//添加重复
        }
        post.setCollectNum(post.getCollectNum()+1);
        postDao.updatePost(post);
        user.setCollectNum(user.getCollectNum()+1);
        userDao.updateUserById(user);
        collectDao.addCollect(collect);
        return collect.getId();
    }

    /**
     * 删除收藏
     *
     * @param id 收藏
     * @return 结果
     */
    @Override
    public Integer deleteCollect(Integer id) {
        Collect collect = collectDao.selectCollectById(id);
        Integer postId = collect.getPostId();
        Post post = postDao.selectPostById(postId);
        post.setCollectNum(post.getCollectNum()-1);
        postDao.updatePost(post);
        User user = userDao.selectUserById(collect.getUserId());
        user.setCollectNum(user.getCollectNum()-1);
        userDao.updateUserById(user);
        return this.collectDao.deleteCollect(id);
    }



    @Override
    public Integer selectAllCollectCountByUserId(Integer id) {
        return collectDao.selectAllCollectCountByUserId(id);
    }

    @Override
    public Integer selectAllCollectCountByPostId(Integer id) {
        return collectDao.selectAllCollectCountByPostId(id);
    }

    @Override
    public Collect selectCollectById(Integer id) {
        return collectDao.selectCollectById(id);
    }

    @Override
    public Integer deleteCollectByUserIdAndPostId(Integer userId, Integer postId) {
        Collect collect = collectDao.selectCollectByUserIdAndPostId(userId,postId);
        Post post = postDao.selectPostById(postId);
        post.setCollectNum(post.getCollectNum()-1);
        postDao.updatePost(post);
        User user = userDao.selectUserById(userId);
        user.setCollectNum(user.getCollectNum()-1);
        userDao.updateUserById(user);
        return collectDao.deleteCollectByUserIdAndPostId(userId,postId);
    }
}