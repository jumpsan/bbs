package com.example.bbs.service.impl;

import com.example.bbs.dao.ApproveDao;
import com.example.bbs.dao.PostDao;
import com.example.bbs.dao.UserDao;
import com.example.bbs.entity.Approve;
import com.example.bbs.entity.Post;
import com.example.bbs.entity.User;
import com.example.bbs.service.ApproveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TApprove)表服务实现类
 *
 * @author makejava
 * @since 2019-09-22 14:04:17
 */
@Service("ApproveService")
public class ApproveServiceImpl implements ApproveService {
    @Resource
    private ApproveDao approveDao;
    @Resource
    private PostDao postDao;
    @Resource
    private UserDao userDao;

    /**
     * 根据帖子Id查找
     *
     * @param id 帖子id
     * @return 点赞列表
     */
    @Override
    public Integer selectApproveCountByPostId(Integer id) {
        return approveDao.selectApproveCountByPostId(id);
    }

    /**
     * 添加点赞记录
     *
     * @param approve 点赞信息
     * @return 主键值
     */
    @Override
    public Integer addApprove(Approve approve) {
        Integer postId = approve.getPostId();
        Post post = postDao.selectPostById(postId);
        User user = userDao.selectUserById(approve.getUserId());
        if(post==null || user==null){
            return -5;  //目标帖子不存在,用户不存在
        }
        Approve checkApprove = approveDao.selectApproveByUserIdAndPostId(user.getId(), post.getId());
        if(checkApprove!=null){
            return -2;//重复添加
        }
        post.setApproveNum(post.getApproveNum()+1);
        postDao.updatePost(post);
        approveDao.addApprove(approve);
        return approve.getId();
    }

    /**
     * 删除点赞记录
     *
     * @param id 点赞id
     * @return 结果
     */
    @Override
    public Integer deleteApprove(Integer id) {
        Approve approve = approveDao.selectApproveById(id);
        Post post = postDao.selectPostById(approve.getPostId());
        post.setApproveNum(post.getApproveNum()-1);
        postDao.updatePost(post);
        return approveDao.deleteApprove(id) ;
    }

    @Override
    public Approve selectApproveById(Integer id) {
        return approveDao.selectApproveById(id);
    }

    /**
     * 通过用户编号和帖子编号删除
     *
     * @param id     用户编号
     * @param postId 帖子编号
     * @return 结果
     */
    @Override
    public Integer deleteApproveByUserIdAndPostId(Integer id, Integer postId) {
        return approveDao.deleteApproveByUserIdAndPostId(id,postId);
    }

    /**
     * 根据用户编号，查看点赞过的帖子
     *
     * @param userId
     * @return
     */
    @Override
    public List<Post> selectApprovedPostByUserId(Integer userId,Integer start,Integer size) {
        return approveDao.selectApprovedPostByUserId(userId,start,size);
    }

    /**
     * 根据用户编号，查看点赞过的帖子 总条数
     *
     * @param userId
     * @return
     */
    @Override
    public Integer selectApprovedPostCountByUserId(Integer userId) {
        return approveDao.selectApprovedPostCountByUserId(userId);
    }


}