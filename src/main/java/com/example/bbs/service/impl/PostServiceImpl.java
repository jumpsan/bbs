package com.example.bbs.service.impl;

import com.example.bbs.dao.*;
import com.example.bbs.entity.Blacklist;
import com.example.bbs.entity.Post;
import com.example.bbs.entity.Section;
import com.example.bbs.entity.User;
import com.example.bbs.service.PostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.IDN;
import java.util.List;

/**
 * (TPost)表服务实现类
 *
 * @author makejava
 * @since 2019-09-20 14:00:31
 */
@Service("tPostService")
public class PostServiceImpl implements PostService {
    @Resource
    private PostDao postDao;
    @Resource
    private UserDao userDao;
    @Resource
    private SectionDao sectionDao;
    @Resource
    private BlacklistDao blacklistDao;
    @Resource
    private ReplyDao replyDao;
    @Resource
    private ApproveDao approveDao;
    @Resource
    private CollectDao collectDao;
    /**
     * 根据帖子id查询
     *
     * @param id 帖子id
     * @return 帖子
     */
    @Override
    public Post selectPostById(Integer id) {
        return postDao.selectPostById(id);
    }

    /**
     * 添加帖子
     *
     * @param post
     * @return
     */
    @Override
    public Integer addImagePost(Post post) {
        //titile, user_id,image, section_id,  content,type
        User user = userDao.selectUserById(post.getUserId());
        Section section = sectionDao.selectSectionById(post.getSectionId());
        Blacklist blacklist = blacklistDao.selectListByUserIdAndPermission(user.getId(), 1);
        if(user==null){
            return -3;//用户不存在
        }
        if(section==null){
            return -5;//分区不存在
        }
        if(section.getStatus()==0){
            return -4; //分区被禁用
        }
        if(blacklist!=null){
            return -6; //用户在黑名单，无权发帖
        }
        postDao.addImagePost(post);
        return post.getId();
    }

    @Override
    public Integer addWordPost(Post post) {
        //titile, user_id,image, section_id,  content,type
        User user = userDao.selectUserById(post.getUserId());
        Section section = sectionDao.selectSectionById(post.getSectionId());
        Blacklist blacklist = blacklistDao.selectListByUserIdAndPermission(user.getId(), 1);
        if(user==null){
            return -3;//用户不存在
        }
        if(section==null){
            return -5;//分区不存在
        }
        if(section.getStatus()==0){
            return -4; //分区被禁用
        }
        if(blacklist!=null){
            return -6; //用户在黑名单，无权发帖
        }
        postDao.addWordPost(post);
        return post.getId();
    }

    @Override
    public Integer addVideoPost(Post post) {
        //titile, user_id,image, section_id,  content,type
        User user = userDao.selectUserById(post.getUserId());
        Section section = sectionDao.selectSectionById(post.getSectionId());
        Blacklist blacklist = blacklistDao.selectListByUserIdAndPermission(user.getId(), 1);
        if(user==null){
            return -3;//用户不存在
        }
        if(section==null){
            return -5;//分区不存在
        }
        if(section.getStatus()==0){
            return -4; //分区被禁用
        }
        if(blacklist!=null){
            return -6; //用户在黑名单，无权发帖
        }
        postDao.addVideoPost(post);
        return post.getId();
    }


    /**
     * 根据帖子id删除帖子
     *
     * @param id 帖子id
     * @return 结果
     */
    @Override
    public Integer deletePostById(Integer id) {
        replyDao.deleteReplyByPostId(id);
        approveDao.deleteApproveByPostId(id);
        collectDao.deleteAllCollectByPostId(id);
        return postDao.deletePostById(id);
    }

    /**
     * 根据用户Id删除
     *
     * @param id 用户id
     * @return 结果
     */
    @Override
    public Integer deletePostByUserId(Integer id) {
        replyDao.deleteReplyByPostUserId(id);
        approveDao.deleteApproveByPostUserId(id);
        collectDao.deleteAllCollectByPostUserId(id);
        return postDao.deletePostByUserId(id);
    }

    /**
     * 根据分区id删除
     *
     * @param id 分区Id
     * @return 结果
     */
    @Override
    public Integer deletePostBySectionId(Integer id) {
        replyDao.deleteReplyBySectionId(id);
        approveDao.deleteApproveBySectionId(id);
        collectDao.deleteAllCollectBySectionId(id);
        return postDao.deletePostBySectionId(id);
    }

    /**
     * 根据类型删除
     *
     * @param type 类型编号
     * @return 结果
     */
    @Override
    public Integer deletePostByType(Integer type) {
        replyDao.deleteReplyByPostType(type);
        approveDao.deleteApproveByPostType(type);
        collectDao.deleteAllCollectByPostType(type);
        return postDao.deletePostByType(type) ;
    }

    /**
     * 更新帖子,必须传入主键
     *
     * @param post 帖子
     * @return 结果
     */
    @Override
    public Integer updatePost(Post post) {

        return postDao.updatePost(post) ;
    }

    /**
     * 根据分区选择帖子
     *
     * @param id    板块id
     * @param start 偏移量
     * @param num   行数
     * @return 结果
     */
    @Override
    public List<Post> selectPostBySectionId(Integer id, Integer start, Integer num) {
        return postDao.selectPostBySectionId(id, start, num);
    }

    /**
     * 根据用户Id查询
     *
     * @param id    用户id
     * @param start 偏移
     * @param num   行数
     * @return 结果
     */
    @Override
    public List<Post> selectPostByUserId(Integer id, Integer start, Integer num) {
        return postDao.selectPostByUserId(id, start, num);
    }

    /**
     * 根据类型查询帖子
     *
     * @param type  类型编号
     * @param start 偏移
     * @param num   行数
     * @return 结果
     */
    @Override
    public List<Post> selectPostByType(Integer type, Integer start, Integer num) {
        return postDao.selectPostByType(type, start, num);
    }

    /**
     * 根据标题模糊查询
     *
     * @param title 标题
     * @param start 偏移
     * @param num   行数
     * @return 结果
     */
    @Override
    public List<Post> selectPostByTitle(String title, Integer start, Integer num) {
        return postDao.selectPostByTitle(title, start, num);
    }

    @Override
    public Integer selectAllPostBySectionId(Integer id) {
        return postDao.selectAllPostCountBySectionId(id);
    }

    @Override
    public Integer selectAllPostByUserId(Integer id) {
        return postDao.selectAllPostCountByUserId(id);
    }

    @Override
    public Integer selectAllPostByType(Integer type) {
        return postDao.selectAllPostCountByType(type);
    }

    @Override
    public Integer selectAllPostByTitle(String title) {
        return postDao.selectAllPostCountByTitle(title);
    }
}