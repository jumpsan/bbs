package com.example.bbs.controller;

import com.example.bbs.dto.PostDto;
import com.example.bbs.entity.*;
import com.example.bbs.service.PostService;
import com.example.bbs.utils.FileUtils;
import com.example.bbs.utils.UploadUtils;
import org.apache.commons.codec.Decoder;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * (TPost)表控制层
 *
 * @author makejava
 * @since 2019-09-20 14:00:32
 */
@RestController
public class PostController {
    /**
     * 服务对象
     */
    @Resource
    private PostService postService;

    /**
     * 根据分区编号查询帖子
     *
     * @param id   分区编号
     * @param page 距离第一行的偏移量
     * @param size 行数
     * @return 帖子列表
     */
    @GetMapping("post/select/section/{id}/{page}/{size}")
    public Information selectPostBySectionId(@PathVariable Integer id, @PathVariable Integer page, @PathVariable Integer size) {
        if (id == null) {
            return Information.error(406, "分区编号不能为空");
        } else {
            Integer total = postService.selectAllPostBySectionId(id);
            if (total == null || total == 0) {
                return Information.error(204, "无内容");
            }
            //总页数
            Integer totalPage = total / size + 1;
            Integer start = (page - 1) * size;
            List<Post> posts = postService.selectPostBySectionId(id, start, size);
            if (posts != null) {
                Page<Post> postPage = new Page<>();
                postPage.setDatas(posts);
                postPage.setTotalPage(totalPage);
                postPage.setTotalNum(total);
                return Information.success(200, "帖子列表", postPage);
            } else {

                return Information.error(204, "分页无内容返回");
            }
        }
    }


    /**
     * 根据用户id查询帖子
     *
     * @param id   用户id
     * @param page 距离第一行的偏移量
     * @param size 行数
     * @return 帖子列表
     */
    @GetMapping("post/select/user/{id}/{page}/{size}")
    public Information selectPostByUserId(@PathVariable Integer id, @PathVariable Integer page, @PathVariable Integer size) {
        if (id == null) {
            return Information.error(406, "关键信息不可为空");
        } else {
            Integer total = postService.selectAllPostByUserId(id);
            if (total == null || total == 0) {
                return Information.error(204, "无内容");
            }
            //总页数
            Integer totalPage = total / size + 1;
            Integer start = (page - 1) * size;
            List<Post> posts = postService.selectPostByUserId(id, start, size);
            if (posts != null) {
                Page<Post> postPage = new Page<>();
                postPage.setDatas(posts);
                postPage.setTotalPage(totalPage);
                postPage.setTotalNum(total);
                return Information.success(200, "帖子列表", postPage);
            } else {
                return Information.error(204, "分页无内容返回");
            }
        }
    }

//    /**
//     * 根据类型查找帖子
//     *
//     * @param type  类型编号，0：图片或文字，1：视频
//     * @param page 距离第一行的偏移量
//     * @param size   行数
//     * @return 帖子列表
//     */
//    @GetMapping("post/select/type/{type}/{page}/{size}")
//    public Information selectPostByType(@PathVariable Integer type, @PathVariable Integer page,@PathVariable Integer size) {
//        if(type==null){
//            return Information.error(406,"关键信息不可为空");
//        }else{
//            Integer total=postService.selectAllPostByType(type);
//            if(total==null || total==0){
//                return Information.error(204,"分页无内容返回");
//            }
//            //总页数
//            Integer totalPage=total/size+1;
//            Integer start=(page-1)*size;
//            List<Post> posts= postService.selectPostByType(type,start, size);
//            if(posts!=null) {
//                Page<Post> postPage=new Page<>();
//                postPage.setDatas(posts);
//                postPage.setTotalPage(totalPage);
//                return Information.success(200,"帖子列表",postPage);
//            }else {
//                return Information.error(204,"分页无内容返回");
//            }
//        }
//    }

    /**
     * 根据帖子id查询
     *
     * @param id 帖子id
     * @return 帖子
     */
    @GetMapping("post/select/{id}")
    public Information selectPostByIdForUser(@PathVariable Integer id) {
        if (id == null) {
            return Information.error(406, "关键信息不能为空");
        } else {
            Post post = postService.selectPostByIdForUser(id);
            if (post != null) {
                return Information.success(200, "帖子", post);
            } else {
                return Information.error(204, "无");
            }
        }
    }

    /**
     * 根据标题模糊查询
     *
     * @param title 标题
     * @param page  距离第一行的偏移量
     * @param size  行数
     * @return 帖子列表
     */
    @GetMapping("post/select/title/{title}/{page}/{size}")
    public Information selectPostByTile(@PathVariable String title, @PathVariable Integer page, @PathVariable Integer size) {
        if (title == null) {
            return Information.error(406, "关键信息不可为空");
        } else {
            Integer total = postService.selectAllPostByTitle(title);
            if (total == null || total == 0) {
                return Information.error(204, "无记录返回");
            }
            //总页数
            Integer totalPage = total / size + 1;
            Integer start = (page - 1) * size;
            List<Post> posts = postService.selectPostByTitle(title, start, size);
            if (posts != null) {
                Page<Post> postPage = new Page<>();
                postPage.setDatas(posts);
                postPage.setTotalPage(totalPage);
                postPage.setTotalNum(total);
                return Information.success(200, "帖子列表", postPage);
            } else {
                return Information.error(204, "无记录返回");
            }
        }
    }


    /**
     * 用户添加帖子，status只能为3,plateId必须为null
     * 0 文字、图片 1 视频
     *
     * @param post 帖子传输
     * @return 结果
     */
    @PostMapping("post/add")
    public Information addPost(Post post, HttpServletRequest request) {
        Integer id = (Integer) request.getAttribute("userId");
        //Post post=postDto.getPost();
        post.setUserId(id);
        post.setPlateId(null);
        //先审核
        post.setStatus(3);
        //List<MultipartFile> multipartFiles = post.getMultipartFile();
        boolean available = post.getUserId() == null || post.getContent() == null || post.getSectionId() == null || post.getTitle() == null || post.getType() == null || post.getContent().trim().length() < 15;
        if (available) {
            return Information.error(406, "关键信息不可为空，文字内容不少于15");
        }
        //视频只能有一个
//        if(post.getType()==1 && multipartFiles.size()>1){
//            return Information.error(409,"视频最多一个");
//        }
        //List<String> fileName = new ArrayList<>();
//        if(multipartFiles!=null){
//            for(MultipartFile file:multipartFiles){
//                Integer sameType = FileUtils.judgeType(file);
//                if(sameType!=post.getType())
//                    return Information.error(409,"类型不符合");
//            }
//            for(MultipartFile file:multipartFiles){
//                String newName = UploadUtils.getNewName(file);
//                fileName.add(newName);
//            }
//            if(post.getType()==0){
//                post.setImages(fileName);
//            }else{
//                post.setVideo(fileName.get(0));
//            }
//        }
        if(post.getImages()!=null && post.getImages().size()>0){
            //将图片从临时文件夹移到图片文件夹
            for(String image:post.getImages()){
                UploadUtils.transferFile(0,image);
            }
        }
        Integer postId = postService.addPost(post);
        if (postId == -6) {
            return Information.error(401, "无权发帖");
        } else if (postId == -3) {
            return Information.error(404, "创建用户不存在");
        } else if (postId == -4) {
            return Information.error(405, "板块或分区被禁用");
        } else if (postId == -5) {
            return Information.error(407, "所添加的目标板块或分区不存在");
        } else if (postId == 0) {
            return Information.error(400, "创建失败");
        } else {
//            if(multipartFiles!=null){
//                for(int i=0;i<multipartFiles.size();i++){
//                    try {
//                        UploadUtils.uploadFile(request,multipartFiles.get(i),post.getType(),fileName.get(i));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        return Information.error(410,"上传文件失败");
//                    }
//                }
//            }
            Post newPost = postService.selectPostById(postId);
            return Information.success(200, "发帖成功", newPost);
        }
    }


    /**
     * 上传到临时文件夹
     * @param image
     * @return
     */
    @PostMapping("upload/tempo")
    public String uploadFile(String image) {
        //将图片的字符串解码
//        if (image == null) //图像数据为空
//            return false;
        if(image==null){
            return null;
        }
        //需要去掉前缀
        String imageString = image.replaceAll("data:image/jpeg;base64", "");
        System.out.println(imageString);
        try {
            //Base64解码
            byte[] buffer = Base64.decodeBase64(imageString);
            for (int i = 0; i < buffer.length; ++i) {
                if (buffer[i] < 0) {//调整异常数据
                    buffer[i] += 256;
                }
            }
            //获得名字
            String uuidName = UUID.randomUUID().toString();  //(4)获得UUID，避免重复
            //创建目录
            String dir = "C:/java/tempo/image/";
            File dirFile=new File(dir);
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }
            //生成jpeg图片
            String imgFilePath = dir+uuidName+".jpeg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(buffer);
            out.flush();
            out.close();
            return "/image/"+uuidName+".jpeg";
        } catch (Exception e) {
            e.printStackTrace();
            //上传失败
            return null;
        }
    }

    /**
     * 根据帖子id删除帖子
     * 用户管理员都可以
     *
     * @param postId 帖子编号
     * @return 结果
     */
    @GetMapping("post/delete/{postId}")
    public Information deletePost(@PathVariable Integer postId, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        Post checkPost = postService.selectPostById(postId);
        if (checkPost != null && !userId.equals(checkPost.getUserId())) {
            return Information.error(411, "非法操作");
        }
        Integer result = postService.deletePostById(postId);
        if (result > 0) {
            return Information.success("删除");
        } else if (result == -5) {
            return Information.success(200, "帖子不存在", null);
        } else {
            return Information.error(400, "删除失败");
        }
    }


    /**
     * 修改帖子内容,不能什么都不改
     * 只能修改title, content
     *
     * @return 结果
     */
    @PostMapping("post/update")
    public Information updatePost(Post post, HttpServletRequest request) {
        if (post.getId() == null) {
            return Information.error(411, "主键不可为空");
        } else {
            Integer userId = (Integer) request.getAttribute("userId");
            Post checkPost = postService.selectPostById(post.getId());
            post.setStatus(checkPost.getStatus());//不可更改
            if (!userId.equals(checkPost.getUserId())) {
                return Information.error(411, "非法操作");
            }
            Integer re = postService.updatePost(post);
            if (re == null || re == 0) {
                return Information.error(400, "修改失败");
            } else {
                Post newPost = postService.selectPostById(post.getId());
                return Information.success(200, "更新成功", newPost);
            }
        }
    }


    /**
     * 查找置顶帖子
     *
     * @param plateId 板块编号
     * @return
     */
    @GetMapping("post/select/top/{plateId}")
    public Information selectTopPostByPlateId(@PathVariable Integer plateId) {
        List<Post> posts = postService.selectTopPostByPlateId(plateId);
        if (posts == null || posts.size() == 0) {
            return Information.error(204, "无内容");
        }
        return Information.success(200, "置顶帖子", posts);
    }

    /**
     * 查找公告帖子
     *
     * @param plateId 板块编号
     * @return
     */
    @GetMapping("post/select/announce/{plateId}")
    public Information selectAnnouncedPostByPlateId(@PathVariable Integer plateId) {
        List<Post> posts = postService.selectAnnouncedPostByPlateId(plateId);
        if (posts == null || posts.size() == 0) {
            return Information.error(204, "无内容");
        }
        return Information.success(200, "公告", posts);
    }

    /**
     * 根据板块帖子最新时间查询
     *
     * @param plateId 板块
     * @param page    页码
     * @param size    行数
     * @return
     */
    @GetMapping("post/select/plate/latest/{plateId}/{page}/{size}")
    public Information selectPostInPlateByUpdateTimeForUser(@PathVariable Integer plateId, @PathVariable Integer page, @PathVariable Integer size) {
        Integer total = postService.selectPostCountByPlateId(plateId);
        if (total == null || total == 0) {
            return Information.error(204, "分页无内容");
        }
        Integer totalPage = total / size + 1;
        Integer start = (page - 1) * size;
        List<Post> posts = postService.selectPostInPlateByUpdateTime(plateId, start, size);
        if (posts == null || posts.size() == 0) {
            return Information.error(204, "分页无内容返回");
        }
        Page<Post> postPage = new Page<>();
        postPage.setTotalPage(totalPage);
        postPage.setDatas(posts);
        postPage.setTotalNum(total);
        return Information.success(200, "帖子列表", postPage);
    }

    /**
     * 根据板块名和分区名查询帖子
     *
     * @param plateName   板块名
     * @param sectionName 分区名
     * @return 帖子
     */
    @GetMapping("manager/post/select/name/{plateName}/{sectionName}/{page}/{size}")
    public Information selectPostByPlateNameAndSectionName(@PathVariable String plateName, @PathVariable String sectionName, @PathVariable Integer page, @PathVariable Integer size) {
        //总条数
        Integer total = postService.selectPostCountByPlateNameAndSectionName(plateName, sectionName);
        if (total == null || total == 0) {
            return Information.error(204, "分页无内容返回");
        }
        //总页数
        Integer totalPage = total / size + 1;
        //起始行数
        Integer start = (page - 1) * size;
        List<Post> posts = postService.selectPostByPlateNameAndSectionName(plateName, sectionName, start, size);
        if (posts == null || posts.size() <= 0) {
            return Information.error(204, "分页无内容返回");
        }
        Page<Post> postPage = new Page<>();
        postPage.setTotalPage(totalPage);
        postPage.setDatas(posts);
        postPage.setTotalNum(total);
        return Information.success(200, "帖子分页", postPage);
    }


    /**
     * 根据发布时间降序查找
     *
     * @param plateId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("post/select/plate/sendTime/{plateId}/{page}/{size}")
    public Information selectPostByPlateAndSendTime(@PathVariable Integer plateId, @PathVariable Integer page, @PathVariable Integer size) {
        Integer total = postService.selectPostCountByPlateId(plateId);
        if (total == null || total == 0) {
            return Information.error(204, "分页无内容");
        }
        Integer totalPage = total / size + 1;
        Integer start = (page - 1) * size;
        List<Post> posts = postService.selectPostInPlateBySendTime(plateId, start, size);
        if (posts == null || posts.size() == 0) {
            return Information.error(204, "分页无内容返回");
        }
        Page<Post> postPage = new Page<>();
        postPage.setTotalPage(totalPage);
        postPage.setDatas(posts);
        postPage.setTotalNum(total);
        return Information.success(200, "帖子列表", postPage);
    }

    /**
     * 按照板块热度降序查找
     *
     * @param plateId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("post/select/plate/hot/{plateId}/{page}/{size}")
    public Information selectPostByPlateAndHot(@PathVariable Integer plateId, @PathVariable Integer page, @PathVariable Integer size) {
        Integer total = postService.selectPostCountByPlateId(plateId);
        if (total == null || total == 0) {
            return Information.error(204, "分页无内容");
        }
        Integer totalPage = total / size + 1;
        Integer start = (page - 1) * size;
        List<Post> posts = postService.selectPostInPlateByHot(plateId, start, size);
        if (posts == null || posts.size() == 0) {
            return Information.error(204, "分页无内容返回");
        }
        Page<Post> postPage = new Page<>();
        postPage.setTotalPage(totalPage);
        postPage.setDatas(posts);
        postPage.setTotalNum(total);
        return Information.success(200, "帖子列表", postPage);
    }

    /**
     * 根据分区帖子最新时间查询
     *
     * @param sectionId 板块
     * @param page      页码
     * @param size      行数
     * @return
     */
    @GetMapping("post/select/section/latest/{sectionId}/{page}/{size}")
    public Information selectPostInSectionByUpdateTimeForUser(@PathVariable Integer sectionId, @PathVariable Integer page, @PathVariable Integer size) {
        if (sectionId == null) {
            return Information.error(406, "分区编号不能为空");
        } else {
            Integer total = postService.selectAllPostBySectionId(sectionId);
            if (total == null || total == 0) {
                return Information.error(204, "分页无内容");
            }
            //总页数
            Integer totalPage = total / size + 1;
            Integer start = (page - 1) * size;
            List<Post> posts = postService.selectPostBySectionIdAndUpdateTime(sectionId, start, size);
            if (posts != null) {
                Page<Post> postPage = new Page<>();
                postPage.setDatas(posts);
                postPage.setTotalPage(totalPage);
                postPage.setTotalNum(total);
                return Information.success(200, "帖子列表", postPage);
            } else {

                return Information.error(204, "分页无内容返回");
            }
        }
    }

    /**
     * 根据分区发布时间降序查找
     *
     * @param sectionId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("post/select/section/sendTime/{sectionId}/{page}/{size}")
    public Information selectPostBySectionAndSendTime(@PathVariable Integer sectionId, @PathVariable Integer page, @PathVariable Integer size) {
        if (sectionId == null) {
            return Information.error(406, "分区编号不能为空");
        } else {
            Integer total = postService.selectAllPostBySectionId(sectionId);
            if (total == null || total == 0) {
                return Information.error(204, "分页无内容");
            }
            //总页数
            Integer totalPage = total / size + 1;
            Integer start = (page - 1) * size;
            List<Post> posts = postService.selectPostBySectionIdAndSendTime(sectionId, start, size);
            if (posts != null) {
                Page<Post> postPage = new Page<>();
                postPage.setDatas(posts);
                postPage.setTotalPage(totalPage);
                postPage.setTotalNum(total);
                return Information.success(200, "帖子列表", postPage);
            } else {

                return Information.error(204, "分页无内容返回");
            }
        }
    }

    /**
     * 按照分区热度降序查找
     *
     * @param sectionId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("post/select/section/hot/{sectionId}/{page}/{size}")
    public Information selectPostBySectionAndHot(@PathVariable Integer sectionId, @PathVariable Integer page, @PathVariable Integer size) {
        if (sectionId == null) {
            return Information.error(406, "分区编号不能为空");
        } else {
            Integer total = postService.selectAllPostBySectionId(sectionId);
            if (total == null || total == 0) {
                return Information.error(204, "分页无内容");
            }
            //总页数
            Integer totalPage = total / size + 1;
            Integer start = (page - 1) * size;
            List<Post> posts = postService.selectPostBySectionIdAndHot(sectionId, start, size);
            if (posts != null) {
                Page<Post> postPage = new Page<>();
                postPage.setDatas(posts);
                postPage.setTotalPage(totalPage);
                postPage.setTotalNum(total);
                return Information.success(200, "帖子列表", postPage);
            } else {

                return Information.error(204, "分页无内容返回");
            }
        }
    }


    /**
     * 选择分区需要审核的帖子
     *
     * @param sectionId 分区编号
     * @param page      页码
     * @param size      行数
     * @return 审核帖子
     */
    @GetMapping("manager/post/select/section/check/{sectionId}/{page}/{size}")
    public Information selectCheckPostsBySection(@PathVariable Integer sectionId, @PathVariable Integer page, @PathVariable Integer size) {
        if (sectionId == null || page == null || size == null) {
            return Information.error(406, "关键信息不可为空");
        } else {
            Integer total = postService.selectUncheckPostCountBySectionId(sectionId);
            if (total == null || total == 0) {
                return Information.error(204, "分页无内容返回");
            }
            Integer totalPage = total / size + 1;
            Integer start = (page - 1) * size;
            List<Post> posts = postService.selectUncheckPostBySectionId(sectionId, start, size);
            if (posts == null) {
                return Information.error(204, "分页无内容返回");
            }
            Page<Post> postPage = new Page<>();
            postPage.setDatas(posts);
            postPage.setTotalPage(totalPage);
            postPage.setTotalNum(total);
            return Information.success(200, "审核帖子", postPage);
        }
    }

    /**
     * 选择板块需要审核的帖子
     *
     * @param plateId 分区编号
     * @param page    页码
     * @param size    行数
     * @return 审核帖子
     */
    @GetMapping("manager/post/select/plate/check/{plateId}/{page}/{size}")
    public Information selectCheckPostsByPlate(@PathVariable Integer plateId, @PathVariable Integer page, @PathVariable Integer size) {
        if (plateId == null || page == null || size == null) {
            return Information.error(406, "关键信息不可为空");
        } else {
            Integer total = postService.selectUncheckPostCountByPlateId(plateId);
            if (total == null || total == 0) {
                return Information.error(204, "分页无内容返回");
            }
            Integer totalPage = total / size + 1;
            Integer start = (page - 1) * size;
            List<Post> posts = postService.selectUncheckPostByPlateId(plateId, start, size);
            if (posts == null) {
                return Information.error(204, "分页无内容返回");
            }
            Page<Post> postPage = new Page<>();
            postPage.setDatas(posts);
            postPage.setTotalPage(totalPage);
            postPage.setTotalNum(total);
            return Information.success(200, "审核帖子", postPage);
        }
    }

    /**
     * 所有需要审核的帖子
     *
     * @param page 页码
     * @param size 行数
     * @return 审核帖子
     */
    @GetMapping("manager/post/select/check/{page}/{size}")
    public Information selectUnCheckPost(@PathVariable Integer page, @PathVariable Integer size) {
        if (page == null || size == null) {
            return Information.error(406, "关键信息不可为空");
        } else {
            Integer total = postService.selectUncheckPostCount();
            if (total == null || total == 0) {
                return Information.error(204, "分页无内容返回");
            }
            Integer totalPage = total / size + 1;
            Integer start = (page - 1) * size;
            List<Post> posts = postService.selectUncheckPost(start, size);
            if (posts == null) {
                return Information.error(204, "分页无内容返回");
            }
            Page<Post> postPage = new Page<>();
            postPage.setDatas(posts);
            postPage.setTotalPage(totalPage);
            postPage.setTotalNum(total);
            return Information.success(200, "审核帖子", postPage);
        }
    }


//    /**
//     * 管理员根据板块帖子最新时间查询需要审核的帖子
//     * @param plateId 板块
//     * @param page 页码
//     * @param size 行数
//     * @return
//     */
//    @GetMapping("manager/post/select/plate/latest/{plateId}/{page}/{size}")
//    public Information selectPostInPlateByUpdateTimeForManager(@PathVariable Integer plateId,@PathVariable Integer page,@PathVariable Integer size){
//        Integer total=postService.selectPostCountByPlateIdForManager(plateId);
//        if(total==null || total==0){
//            return Information.error(204,"分页无内容");
//        }
//        Integer totalPage=total/size+1;
//        Integer start=(page-1)*size;
//        List<Post> posts=postService.selectPostInPlateByUpdateTimeForManager(plateId,start,size);
//        if(posts==null || posts.size()==0){
//            return Information.error(204,"分页无内容返回");
//        }
//        Page<Post> postPage=new Page<>();
//        postPage.setTotalPage(totalPage);
//        postPage.setDatas(posts);
//        return Information.success(200,"帖子列表",postPage);
//    }

    /**
     * 管理员根据帖子id查询
     *
     * @param id 帖子id
     * @return 帖子
     */
    @GetMapping("manager/post/select/{id}")
    public Information selectPostByIdForManager(@PathVariable Integer id) {
        if (id == null) {
            return Information.error(406, "关键信息不能为空");
        } else {
            Post post = postService.selectPostById(id);
            if (post != null) {
                return Information.success(200, "帖子", post);
            } else {
                return Information.error(204, "无");
            }
        }
    }


    /**
     * 管理员添加帖子，status为1，2，0
     * 0 文字、图片 1 视频
     *
     * @param post 帖子传输
     * @return 结果
     */
    @PostMapping("manager/post/add")
    public Information addPostForManager(Post post, HttpServletRequest request) {
        //Post post=postDto.getPost();
        //List<MultipartFile> multipartFiles = post.getMultipartFile();
        post.setSectionId(null);
        Integer userId = (Integer) request.getAttribute("userId");
        post.setUserId(userId);
        boolean available = post.getContent() == null || post.getPlateId() == null || post.getStatus() == null || post.getTitle() == null || post.getType() == null ||  post.getContent().trim().length() < 15 ;
        if (available) {
            return Information.error(406, "关键信息不可为空，文字内容不可少于15");
        }
        //视频只能有一个
//        if (post.getType() == 1 && multipartFiles.size() > 1) {
//            return Information.error(409, "视频最多一个");
//        }
//        List<String> fileName = new ArrayList<>();
//        if (multipartFiles != null) {
//            for (MultipartFile file : multipartFiles) {
//                Integer sameType = FileUtils.judgeType(file);
//                if (sameType != post.getType())
//                    return Information.error(409, "类型不符合");
//            }
//            for (MultipartFile file : multipartFiles) {
//                String newName = UploadUtils.getNewName(file);
//                fileName.add(newName);
//            }
//            if (post.getType() == 0) {
//                post.setImages(fileName);
//            } else {
//                post.setVideo(fileName.get(0));
//            }
//        }
        Integer postId = postService.addPostForManager(post);
        if (postId == -6) {
            return Information.error(401, "无权发帖");
        } else if (postId == -3) {
            return Information.error(404, "创建用户不存在");
        } else if (postId == -4) {
            return Information.error(405, "板块或分区被禁用");
        } else if (postId == -5) {
            return Information.error(407, "所添加的板块或目标分区不存在");
        } else if (postId == 0) {
            return Information.error(400, "创建失败");
        } else {
//            if (multipartFiles != null) {
////                for (int i = 0; i < multipartFiles.size(); i++) {
////                    try {
////                        UploadUtils.uploadFile( multipartFiles.get(i), post.getType(), fileName.get(i));
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                        return Information.error(410, "上传文件失败");
////                    }
////                }
////            }
            //将图片从临时文件夹移到目标文件夹
            if(post.getImages()!=null && post.getImages().size()>0){
                for(String image:post.getImages()){
                    UploadUtils.transferFile(0,image);
                }
            }

            Post newPost = postService.selectPostById(postId);
            return Information.success(200, "发帖成功", newPost);
        }
    }

    /**
     * 管理员根据帖子id删除帖子
     * 用户管理员都可以
     *
     * @param postId 帖子编号
     * @return 结果
     */
    @GetMapping("manager/post/delete/{postId}")
    public Information deletePostForManager(@PathVariable Integer postId) {
        Integer result = postService.deletePostById(postId);
        if (result > 0) {
            return Information.success("删除");
        } else if (result == -5) {
            return Information.success(200, "帖子不存在", null);
        } else {
            return Information.error(400, "删除失败");
        }
    }

    /**
     * 管理员修改帖子内容,不能什么都不改
     * 只能修改title, content.status
     *
     * @return 结果
     */
    @PostMapping("manager/post/update")
    public Information updatePostForManager(Post post) {
        if (post.getId() == null) {
            return Information.error(411, "主键不可为空");
        } else {
            Integer re = postService.updatePost(post);
            if (re == null || re == 0) {
                return Information.error(400, "修改失败");
            } else {
                Post newPost = postService.selectPostById(post.getId());
                return Information.success(200, "更新成功", newPost);
            }
        }
    }

    /**
     * 首页推荐的帖子
     *
     * @return 信息
     */
    @GetMapping("post/select/index/{page}/{size}")
    public Information selectIndexPagePost(@PathVariable Integer page, @PathVariable Integer size) {
        Integer total = postService.selectIndexPagePostCount();
        if (total == null || total == 0) {
            return Information.error(204, "分页无内容返回");
        }
        Integer totalPage = total / size + 1;
        Integer start = (page - 1) * size;
        List<Post> posts = postService.selectIndexPagePost(start, size);
        if (posts == null || posts.size() <= 0) {
            return Information.error(400, "查询失败");
        }
        Page<Post> postPage = new Page<>();
        postPage.setTotalNum(total);
        postPage.setTotalPage(totalPage);
        postPage.setDatas(posts);
        return Information.success(200, "帖子列表", postPage);
    }

}