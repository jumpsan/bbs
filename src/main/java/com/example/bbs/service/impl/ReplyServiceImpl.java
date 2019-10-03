package com.example.bbs.service.impl;

import com.example.bbs.dao.PostDao;
import com.example.bbs.dao.UserDao;
import com.example.bbs.entity.Post;
import com.example.bbs.entity.Reply;
import com.example.bbs.dao.ReplyDao;
import com.example.bbs.entity.User;
import com.example.bbs.service.ReplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TReply)表服务实现类
 *
 * @author makejava
 * @since 2019-09-20 14:00:45
 */
@Service("tReplyService")
public class ReplyServiceImpl implements ReplyService {
    @Resource
    private ReplyDao replyDao;
    @Resource
    private UserDao userDao;
    @Resource
    private PostDao postDao;

    /**
     * 根据帖子id查询
     *
     * @param id    帖子id
     * @param start 偏移
     * @param num   行数
     * @return 帖子列表
     */
    @Override
    public List<Reply> selectReplyByPostId(Integer id, Integer start, Integer num) {
        return replyDao.selectReplyByPostId(id, start, num);
    }

    /**
     * 根据用户Id查询
     *
     * @param id    用户id
     * @param start
     * @param num
     * @return
     */
    @Override
    public List<Reply> selectReplyByUserId(Integer id, Integer start, Integer num) {
        return replyDao.selectReplyByUserId(id, start, num);
    }

    /**
     * 添加回复
     * user_id post_id
     * @param reply
     * @return
     */
    @Override
    public Integer addReply(Reply reply) {
        User user = userDao.selectUserById(reply.getUserId());
        Post post = postDao.selectPostById(reply.getPostId());
        if(user==null){
            return -3;
        }
        if(post==null){
            return -5;
        }
        //添加帖子的回复数
        Integer replyNum = post.getReplyNum();
        post.setReplyNum(++replyNum);
        postDao.updatePost(post);
        replyDao.addReply(reply);
        return reply.getId();
    }

    /**
     * 删除回复
     *
     * @param id 主键
     * @return
     */
    @Override
    public Integer deleteReplyById(Integer id) {
        //删除帖子回复数
        Reply reply = replyDao.selectReplyById(id);
        Post post = postDao.selectPostById(reply.getPostId());
        Integer replyNum = post.getReplyNum();
        post.setReplyNum(--replyNum);
        postDao.updatePost(post);
        return replyDao.deleteReplyById(id) ;
    }

    /**
     * 修改回复
     *
     * @param reply 回复
     * @return
     */
    @Override
    public Integer updateReply(Reply reply) {
        return replyDao.updateReply(reply) ;
    }

    /**
     * 根据回复id查询
     *
     * @param id 回复id
     * @return 结果
     */
    @Override
    public Reply selectReplyById(Integer id) {
        return replyDao.selectReplyById(id);
    }

    @Override
    public Integer selectAllReplyCountByPostId(Integer id) {
        return replyDao.selectAllReplyCountByPostId(id);
    }

    @Override
    public Integer selectAllReplyCountByUserId(Integer id) {
        return replyDao.selectAllReplyCountByUserId(id);
    }
}