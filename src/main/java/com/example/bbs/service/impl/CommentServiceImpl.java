package com.example.bbs.service.impl;

import com.example.bbs.dao.*;
import com.example.bbs.entity.*;
import com.example.bbs.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.ref.PhantomReference;
import java.util.List;

/**
 * (Comment)表服务实现类
 *
 * @author jj
 * @since 2019-10-26 14:54:23
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentDao commentDao;
    @Resource
    private BlacklistDao blacklistDao;
    @Resource
    private PlateDao plateDao;
    @Resource
    private SectionDao sectionDao;
    @Resource
    private ReplyDao replyDao;
    @Resource
    private PostDao postDao;
    @Resource
    private UserDao userDao;

    /**
     * 修改数据
     *
     * @param comment 实例对象
     * @return 实例对象
     */
    @Override
    public Comment update(Comment comment) {
        return null;
    }

    /**
     * 添加回复
     *
     * @param comment 回复
     * @return 主键
     */
    @Override
    public Integer addComment(Comment comment) {
        //回复人是否在黑名单
        //分区是否可用
        //板块是否可用
        Blacklist blacklist = blacklistDao.selectListByUserIdAndPermission(comment.getUserId(), 0);
        if(blacklist!=null){
            return -6;//用户在黑名单
        }
        //查找用户名
        if(comment.getToUserId()!=null){
            User user = userDao.selectUserById(comment.getToUserId());
            if(user==null){
                System.out.println("user");
                return -5;
            }
            comment.setToUsername(user.getUsername());
        }
        Reply reply = replyDao.selectReplyById(comment.getReplyId());
        if(reply==null){
            System.out.println("reply");
            return -5;
        }
        Post post = postDao.selectPostById(reply.getPostId());
        if(post==null){
            System.out.println("post");
            return -5;
        }
        if(post.getSectionId()!=null){
            Section section = sectionDao.selectSectionById(post.getSectionId());
            if(section.getStatus()==0){
                return -4;
            }
        }
        if(post.getPlateId()!=null){
            Plate plate = plateDao.selectPlateByIdForManager(post.getPlateId());
            if(plate.getStatus()==0){
                return -4;//禁用
            }
        }
        Integer result = commentDao.addComment(comment);
        if(result<=0)
            return -7;
        //TODO 通知被回复的人
        reply.setCommentNum(reply.getCommentNum()+1);
        replyDao.updateReply(reply);
        //帖子回复数加一
        post.setReplyNum(post.getReplyNum()+1);
        postDao.updatePost(post);
        return comment.getId();
    }

    /**
     * 根据编号查询楼中楼
     *
     * @param commentId
     * @return
     */
    @Override
    public Comment selectCommentById(Integer commentId) {
        return commentDao.selectCommentById(commentId);
    }

    /**
     * 删除楼中楼
     *
     * @param commentId 编号
     * @return 结果
     */
    @Override
    public Integer deleteComment(Integer commentId) {
        Comment comment=commentDao.selectCommentById(commentId);
        if(comment!=null){
            Reply reply=replyDao.selectReplyById(comment.getReplyId());
            if(reply!=null){
                reply.setCommentNum(reply.getCommentNum()-1);
                replyDao.updateReply(reply);
            }
            Post post=postDao.selectPostById(reply.getPostId());
            if(post!=null){
                //回复数减一
                post.setReplyNum(post.getReplyNum()-1);
                postDao.updatePost(post);
            }
        }

        return commentDao.deleteComment(commentId);
    }
}