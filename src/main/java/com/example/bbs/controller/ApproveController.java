package com.example.bbs.controller;
import com.example.bbs.annotation.AuthChecker;
import com.example.bbs.entity.Approve;
import com.example.bbs.entity.Information;
import com.example.bbs.entity.Page;
import com.example.bbs.entity.Post;
import com.example.bbs.service.ApproveService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (TApprove)表控制层
 *
 * @author makejava
 * @since 2019-09-22 14:04:17
 */
@RestController
@RequestMapping("approve")
public class ApproveController {
    /**
     * 服务对象
     */
    @Resource
    private ApproveService approveService;

    /**
     * 根据帖子Id查找点赞总数
     *
     * @param id 帖子id
     * @return 点赞列表
     */
    @GetMapping("select/count/post/{id}")
    public Information selectApproveCountByPostId(@PathVariable Integer id) {
        Integer result = approveService.selectApproveCountByPostId(id);
        if(result<0){
            return Information.error(400,"查询失败");
        }else{
            return Information.success(200,"点赞总数",result);
        }
    }

    /**
     * 添加点赞记录
     *
     * @param postId  帖子编号
     * @return 主键值
     */
    @PostMapping("add")
    public Information addApprove(Integer postId, HttpServletRequest request) {
        Integer userId =(Integer) request.getAttribute("userId");
        Approve approve=new Approve();
        approve.setPostId(postId);
        approve.setUserId(userId);
        if(approve.getPostId()==null || approve.getUserId()==null){
            return Information.error(406,"关键信息不为空");
        } else{
            Integer result = approveService.addApprove(approve);
            if(result>0){
                Approve newApprove = approveService.selectApproveById(result);
                return Information.success(200,"点赞成功",newApprove);
            }else if(result==-5){
                return Information.error(407,"目标帖子或用户不存在");
            }else if(result==-2){
                return Information.error(402,"重复点赞");
            }else{
                return Information.error(400,"失败重试");
            }
        }
    }


    /**
     * 删除点赞记录
     *
     * @param postId 帖子编号
     * @return 结果
     */
    @GetMapping("delete/user/post/{postId}")
    public Information deleteApprove(@PathVariable Integer postId,HttpServletRequest request) {
        Integer userId =(Integer) request.getAttribute("userId");
        //System.out.println(userId);
        if(userId==null || postId==null){
            return Information.error(406,"关键信息不可为空");
        }else{
            Integer result = approveService.deleteApproveByUserIdAndPostId(userId,postId);
            if(result<=0){
                return Information.error(400,"失败");
            }else{
                return Information.success("取消点赞");
            }
        }
    }

    /**
     * 根据用户编号，点赞过的帖子
     * @param userId 用户编号
     * @param page 页码
     * @param size 行数
     * @return 信息
     */
    @GetMapping("select/post/user/{userId}/{page}/{size}")
    public Information selectApprovedPostByUserId(@PathVariable Integer userId,@PathVariable Integer page,@PathVariable Integer size){
        if(userId==null){
            return Information.error(406,"关键信息不可为空");
        }
        Integer total = approveService.selectApprovedPostCountByUserId(userId);
        if(total==null || total==0){
            return Information.error(204,"分页无内容返回");
        }
        Integer totalPage=total/size+1;
        Integer start=(page-1)*size;
        List<Post> posts = approveService.selectApprovedPostByUserId(userId,start,size);
        if(posts==null){
            return Information.error(204,"分页无内容返回");
        }
        Page<Post> postPage=new Page<>();
        postPage.setDatas(posts);
        postPage.setTotalPage(totalPage);
        return Information.success(200,"点赞帖子",postPage);
    }


}