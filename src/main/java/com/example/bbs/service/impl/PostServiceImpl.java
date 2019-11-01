package com.example.bbs.service.impl;

import com.example.bbs.annotation.RightChecker;
import com.example.bbs.dao.*;
import com.example.bbs.entity.*;
import com.example.bbs.service.PostService;
import com.example.bbs.utils.UploadUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import java.net.IDN;
import java.util.List;

/**
 * (TPost)表服务实现类
 *
 * @author makejava
 * @since 2019-09-20 14:00:31
 */
@Service("postService")
public class PostServiceImpl implements PostService {
    @Resource
    private PostDao postDao;
    @Resource
    private UserDao userDao;
    @Resource
    private SectionDao sectionDao;
    @Resource
    private ReplyDao replyDao;
    @Resource
    private ApproveDao approveDao;
    @Resource
    private CollectDao collectDao;
    @Resource
    private PlateDao plateDao;
    @Resource
    private PostImageDao postImageDao;
    @Resource
    private AdminDao adminDao;
    @Resource
    private RoleDao roleDao;
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
    public Integer addPost(Post post) {
        //title, user_id,section_id,  content,type
        User user = userDao.selectUserById(post.getUserId());
        Section section = sectionDao.selectSectionById(post.getSectionId());
        post.setType(0);
        if(user==null){
            return -3;//用户不存在
        }
        if(section==null){
            return -5;//分区不存在
        }
        if(section.getStatus()==0){
            return -4; //分区被禁用
        }
        //Blacklist blacklist = blacklistDao.selectListByUserIdAndPermission(user.getId(), 1);
        Plate plate = plateDao.selectPlateBySectionId(section.getId());
        if(plate==null){
            return -5;
        }
        if(plate.getStatus()==0){
            return -4;//板块被禁用
        }
        if(user.getLimitPost()!=0){
            return -6; //用户在黑名单，无权发帖
        }
        //分区帖子数量加一
        section.setPostNum(section.getPostNum()+1);
        sectionDao.updateSection(section);
        //用户发的帖子数量加一
        user.setPostNum(user.getPostNum()+1);
        userDao.updateUserById(user);
        //板块帖子加一
        plate.setPostNum(plate.getPostNum()+1);
        plateDao.updatePlate(plate);
        //添加图片记录
        List<String> images = post.getImages();
        postDao.addPost(post);
        if(images!=null){
            for(String image:images){
                PostImage postImage=new PostImage();
                postImage.setImage(image);
                postImage.setPostId(post.getId());
                postImageDao.addImages(postImage);
            }
        }
        return post.getId();
    }

   


    /**
     * 根据帖子id删除帖子,包括正在审核的
     *
     * @param id 帖子id
     * @return 结果
     */
    @Override
    public Integer deletePostById(Integer id) {
        Post post = postDao.selectPostById(id);
        if(post==null){
            return -5;//帖子不存在
        }
        //删除文件
        if(post.getVideo()!=null){
            UploadUtils.deleteFile(1,post.getVideo());
        }
        if(post.getImages()!=null && post.getImages().size()>0){
            for(String file:post.getImages()){
                UploadUtils.deleteFile(0,file);
                //删除图片记录
                postImageDao.deleteImageByPostId(id);
            }
        }
        User user = userDao.selectUserById(post.getUserId());
        if(user!=null){
            //用户发的帖子数量减一
            user.setPostNum(user.getPostNum()-1);
            userDao.updateUserById(user);
        }
        Section section = sectionDao.selectSectionById(post.getSectionId());
        if(section!=null){
            //分区帖子数量减一
            section.setPostNum(section.getPostNum()-1);
            sectionDao.updateSection(section);
            //板块帖子数量减一
            Plate plate = plateDao.selectPlateBySectionId(section.getId());
            if(plate!=null){
                plate.setPostNum(plate.getPostNum()-1);
                plateDao.updatePlate(plate);
            }
        }
        //删除相关的回复，赞，收藏
        replyDao.deleteReplyByPostId(id);
        approveDao.deleteApproveByPostId(id);
        collectDao.deleteAllCollectByPostId(id);
        return postDao.deletePostById(id);
    }

//    /**
//     * 根据用户Id删除
//     *
//     * @param id 用户id
//     * @return 结果
//     */
//    @Override
//    public Integer deletePostByUserId(Integer id) {
//        replyDao.deleteReplyByPostUserId(id);
//        approveDao.deleteApproveByPostUserId(id);
//        collectDao.deleteAllCollectByPostUserId(id);
//        return postDao.deletePostByUserId(id);
//    }
//
//    /**
//     * 根据分区id删除
//     *
//     * @param id 分区Id
//     * @return 结果
//     */
//    @Override
//    public Integer deletePostBySectionId(Integer id) {
//        replyDao.deleteReplyBySectionId(id);
//        approveDao.deleteApproveBySectionId(id);
//        collectDao.deleteAllCollectBySectionId(id);
//        return postDao.deletePostBySectionId(id);
//    }
//
//    /**
//     * 根据类型删除
//     *
//     * @param type 类型编号
//     * @return 结果
//     */
//    @Override
//    public Integer deletePostByType(Integer type) {
//        replyDao.deleteReplyByPostType(type);
//        approveDao.deleteApproveByPostType(type);
//        collectDao.deleteAllCollectByPostType(type);
//        return postDao.deletePostByType(type) ;
//    }

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

    @Override
    public List<Post> selectUncheckPostBySectionId(Integer sectionId, Integer start, Integer size) {
        return postDao.selectUncheckPostBySectionId(sectionId,start,size);
    }

    @Override
    public Integer selectUncheckPostCountBySectionId(Integer sectionId) {
        return postDao.selectUncheckPostCountBySectionId(sectionId);
    }

    /**
     * 只有已经审核完成的
     * @param id
     * @return
     */
    @Override
    public Post selectPostByIdForUser(Integer id) {
        Post post = postDao.selectPostByIdForUser(id);
        //添加浏览量
        if(post!=null){
            post.setViewNum(post.getViewNum()+1);
            //System.out.println(post.getId());
            postDao.updatePost(post);
        }
        return post;
    }

    /**
     * @param plateName   板块名
     * @param sectionName 分区名
     * @param start
     * @param size
     * @return 帖子
     */
    @Override
    public List<Post> selectPostByPlateNameAndSectionName(String plateName, String sectionName, Integer start, Integer size) {
        return postDao.selectPostByPlateNameAndSectionName(plateName,sectionName,start,size);
    }

    /**
     * @param plateName   板块名
     * @param sectionName 分区名
     * @return 帖子总数
     */
    @Override
    public Integer selectPostCountByPlateNameAndSectionName(String plateName, String sectionName) {
        return postDao.selectPostCountByPlateNameAndSectionName(plateName,sectionName);
    }

    /**
     * 置顶帖子
     *
     * @param plateId
     * @return
     */
    @Override
    public List<Post> selectTopPostByPlateId(Integer plateId) {
        return postDao.selectTopPostByPlateId(plateId);
    }

    /**
     * 查找公告帖子
     *
     * @param plateId
     * @return
     */
    @Override
    public List<Post> selectAnnouncedPostByPlateId(Integer plateId) {
        return postDao.selectAnnouncedPostByPlateId(plateId);
    }

    /**
     * 查找一个板块下的所有帖子数量
     *
     * @param plateId 板块编号
     * @return 帖子数量
     */
    @Override
    public Integer selectPostCountByPlateId(Integer plateId) {
        return postDao.selectPostCountByPlateId(plateId);
    }

    /**
     * 根据最新更新时间查找帖子
     *
     * @param plateId 板块
     * @param start   起始
     * @param size    行数
     * @return
     */
    @Override
    public List<Post> selectPostInPlateByUpdateTime(Integer plateId, Integer start, Integer size) {
        return postDao.selectPostInPlateByUpdateTime(plateId,start,size);
    }


    /**
     * 查找一个板块下的所有帖子数量
     *
     * @param plateId 板块编号
     * @return 帖子数量
     */
    @Override
    public Integer selectPostCountByPlateIdForManager(Integer plateId) {
        return postDao.selectPostCountByPlateIdForManager(plateId);
    }

    /**
     * 根据最新更新时间查找帖子
     *
     * @param plateId 板块
     * @param start   起始
     * @param size    行数
     * @return
     */
    @Override
    public List<Post> selectPostInPlateByUpdateTimeForManager(Integer plateId, Integer start, Integer size) {
        return postDao.selectPostInPlateByUpdateTimeForManager(plateId,start,size);
    }

    /**
     * 板块下的审核帖子
     *
     * @param plateId
     * @return
     */
    @Override
    public Integer selectUncheckPostCountByPlateId(Integer plateId) {
        return postDao.selectUncheckPostCountByPlateId(plateId);
    }

    /**
     * 板块下需审核帖子
     *
     * @param plateId
     * @param start
     * @param size
     * @return
     */
    @Override
    public List<Post> selectUncheckPostByPlateId(Integer plateId, Integer start, Integer size) {
        return postDao.selectUncheckPostByPlateId(plateId,start,size);
    }

    /**
     * 添加帖子
     *
     * @param post
     * @return
     */
    @Override
    public Integer addPostForManager(Post post) {
        //title, user_id,image, section_id,  content,type
        Admin admin = adminDao.selectAdminById(post.getUserId());
        Plate plate = plateDao.selectPlateByIdForManager(post.getPlateId());
        Role role=roleDao.selectRoleByUserIdAndPlateId(post.getUserId(),plate.getId());
        if(admin==null && role==null){
            return -3;//用户不存在
        }
        if(plate==null){
            return -5;
        }
        if(plate.getStatus()==0){
            return -4;//板块被禁用
        }
        if(role!=null){
            User user=userDao.selectUserById(post.getUserId());
            //用户发的帖子数量加一
            user.setPostNum(user.getPostNum()+1);
            userDao.updateUserById(user);
        }
        //板块帖子加一
        plate.setPostNum(plate.getPostNum()+1);
        plateDao.updatePlate(plate);
        //添加图片记录
        List<String> images = post.getImages();
        postDao.addPost(post);
        if(images!=null){
            for(String image:images){
                PostImage postImage=new PostImage();
                postImage.setImage(image);
                postImage.setPostId(post.getId());
                postImageDao.addImages(postImage);
            }
        }
        return post.getId();
    }

    /**
     * 根据发布时间查找 降序
     *
     * @param plateId
     * @param start
     * @param size
     * @return
     */
    @Override
    public List<Post> selectPostInPlateBySendTime(Integer plateId, Integer start, Integer size) {
        return postDao.selectPostInPlateBySendTime(plateId,start,size);
    }

    /**
     * 根据热度查询
     *
     * @param plateId
     * @param start
     * @param size
     * @return
     */
    @Override
    public List<Post> selectPostInPlateByHot(Integer plateId, Integer start, Integer size) {
        return postDao.selectPostInPlateByHot(plateId,start,size);
    }

    /**
     * 根据分区最新更长帖子
     *
     * @param sectionId
     * @param start
     * @param size
     * @return
     */
    @Override
    public List<Post> selectPostBySectionIdAndUpdateTime(Integer sectionId, Integer start, Integer size) {
        return postDao.selectPostBySectionIdAndUpdateTime(sectionId,start,size);
    }

    /**
     * 分区根据发布时间查找 降序
     *
     * @param sectionId
     * @param start
     * @param size
     * @return
     */
    @Override
    public List<Post> selectPostBySectionIdAndSendTime(Integer sectionId, Integer start, Integer size) {
        return postDao.selectPostBySectionIdAndSendTime(sectionId,start,size);
    }

    /**
     * 分区根据热度查询
     *
     * @param sectionId
     * @param start
     * @param size
     * @return
     */
    @Override
    public List<Post> selectPostBySectionIdAndHot(Integer sectionId, Integer start, Integer size) {
        return postDao.selectPostBySectionIdAndHot(sectionId,start,size);
    }

    /**
     * 查找首页帖子
     *
     * @param start
     * @param size
     * @return
     */
    @Override
    public List<Post> selectIndexPagePost(Integer start, Integer size) {
        return postDao.selectIndexPagePost(start,size);
    }

    /**
     * 查找所有帖子的数量
     *
     * @return
     */
    @Override
    public Integer selectIndexPagePostCount() {
        return postDao.selectIndexPagePostCount();
    }

    /**
     * 所有需要审核的帖子数量
     *
     * @return
     */
    @Override
    public Integer selectUncheckPostCount() {
        return postDao.selectUncheckPostCount();
    }

    /**
     * 所有需要审核的帖子
     *
     * @param start
     * @param size
     * @return
     */
    @Override
    public List<Post> selectUncheckPost(Integer start, Integer size) {
        return postDao.selectUncheckPost(start,size);
    }
}