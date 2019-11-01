package com.example.bbs.controller;


import com.example.bbs.annotation.AuthChecker;
import com.example.bbs.entity.Follow;
import com.example.bbs.entity.Information;
import com.example.bbs.entity.Page;
import com.example.bbs.entity.User;
import com.example.bbs.service.FollowService;
import com.example.bbs.utils.Authorization;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * (TFollowed)表控制层
 *
 * @author makejava
 * @since 2019-09-20 13:59:33
 */
@RestController
@RequestMapping("follow")  //用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
public class FollowController {
    /**
     * 服务对象
     */
    @Resource
    private FollowService followService;

    /**
     * 查找某个用户所关注的人
     *
     * @param followId 关注人
     * @return 关注列表
     */
    @GetMapping("select/follow/{followId}/{page}/{size}")
    public Information  selectByFollowId(@PathVariable Integer followId, @PathVariable Integer page, @PathVariable Integer size) {
        if(followId==null || page==null || size==null){
            return Information.error(406,"关键信息为空");
        }else{
            Integer total=followService.selectAllCountByFollowId(followId);
            if(total==null || total==0){
                return Information.success(204,"分页无内容返回",null);
            }
            Page<User> followList=new Page<>();
            Integer start=(page-1)*size+1;
            followList.setDatas(followService.selectByFollowId(followId, start, size));
            Integer totalPage =total /size+1;
            followList.setTotalPage(totalPage);
            return Information.success(200,"关注列表",followList);
        }
    }

    /**
     * 查询关注总数
     * @param followId
     * @return
     */
    @GetMapping("select/follow/num/{followId}")
    public Information selectCountByFollowId(@PathVariable Integer followId){
        if(followId==null){
            return Information.error(406,"关键信息不可为空");
        }else{
            Integer count = followService.selectAllCountByFollowId(followId);
            return Information.success(200,"关注总数",count);
        }
    }

    /**
     * 查找粉丝
     *
     * @param followedId 被关注人
     * @return 粉丝列表
     */
    @GetMapping("select/followed/{followedId}/{page}/{size}")
    public Information selectByFollowedId(@PathVariable Integer followedId,@PathVariable Integer page,@PathVariable Integer size) {
        if(followedId==null || page==null || size==null){
            return Information.error(406,"关键信息不可为空");
        }else{
            Integer total=followService.selectAllCountByFollowedId(followedId);
            if(total==null|| total==0){
                return Information.success(204,"分页无内容返回",null);
            }else{
                Page<User> userPage=new Page<>();
                Integer start=(page-1)*size+1;
                userPage.setDatas(followService.selectByFollowedId(followedId, start, size));
                Integer totalPage =total /size+1;
                userPage.setTotalPage(totalPage);
                return Information.success(200,"粉丝列表",userPage);
            }
        }
    }

    /**
     * 粉丝总数
     * @param followedId
     * @return
     */
    @GetMapping("select/followed/num/{followedId}")
    public Information selectCountByFollowedId(@PathVariable Integer followedId){
        if(followedId==null){
            return Information.error(406,"关键信息不可为空");
        }else{
            Integer count = followService.selectAllCountByFollowedId(followedId);
            return Information.success(200,"粉丝总数",count);
        }
    }




    /**
     * 添加关注记录
     * @param followedId 被关注者
     * @return 返回主键值
     */
    @PostMapping("add")
    public Information addFollow( Integer followedId,HttpServletRequest request){
        Integer id=(Integer)request.getAttribute("userId");
        Follow follow=new Follow();
        follow.setFollowedId(followedId);
        follow.setFollowId(id);
        if(id==null || followedId==null){
            return Information.error(406,"关键信息不可为空");
        }else{
            Integer result = followService.addFollow(follow);
            if(result==-7){
                return Information.error(400,"关注失败");
            }else if(result==-3){
                return Information.error(402,"用户不存在");
            }else if(result==-2){
                return Information.error(402,"重复关注");
            }else{
                Follow newFollow = followService.selectById(result);
                return Information.success(200,"关注成功",newFollow);
            }
        }
    }

    /**
     * 取消关注时
     * @param followedId 被关注者
     * @return 结果
     */
    @GetMapping("delete/{followedId}")
    public Information deleteFollow(@PathVariable Integer followedId,HttpServletRequest request) {
        Integer id=(Integer)request.getAttribute("userId");
        Follow follow=new Follow();
        follow.setFollowId(id);
        follow.setFollowedId(followedId);
        if(id==null && followedId==null){
            return Information.error(406,"关键信息不可为空");
        }else{
            Integer result = followService.deleteFollow(follow);
            if(result<=0){
                return Information.error(400,"删除失败");
            }else{
                return Information.success("删除");
            }
        }
    }
}

//    /**
//     * 某个用户取关所有人
//     *
//     * @param followId 关注人
//     * @return 结果
//     */
//    @DeleteMapping("delete/all/follow/{followId}")
//    public Information<Integer> deleteByFollowId(@PathVariable Integer followId,HttpServletRequest request) {
//        Information<Integer> information=new Information<>();
//        boolean verify = Authorization.verify(request, followId);
//        if(followId==null){
//            information.setStatus(406);
//            information.setMsg("关键信息不可为空");
//        }else if(!verify){
//            information.setMsg("非法操作");
//            information.setStatus(411);
//        }else{
//            Integer result = followService.deleteByFollowId(followId);
//            if(result<=0){
//                information.setMsg("删除失败");
//                information.setStatus(400);
//            }else{
//                information.setStatus(200);
//                information.setMsg("删除成功");
//                information.setData(result);
//            }
//        }
//        return information;
//    }

//    /**
//     * 某个用户除所有粉丝
//     *
//     * @param followedId 被关注人
//     * @return 结果
//     */
//    @DeleteMapping("delete/all/followed/{followedId}")
//    public Information<Integer> deleteByFollowedId(@PathVariable Integer followedId,HttpServletRequest request) {
//        Information<Integer> information=new Information<>();
//        boolean verify = Authorization.verify(request, followedId);
//        if(followedId==null){
//            information.setStatus(406);
//            information.setMsg("关键信息不可为空");
//        }else if(!verify){
//            information.setMsg("非法操作");
//            information.setStatus(411);
//        }else{
//            Integer result = followService.deleteByFollowedId(followedId);
//            if(result<=0){
//                information.setMsg("删除失败");
//                information.setStatus(400);
//            }else{
//                information.setStatus(200);
//                information.setMsg("删除成功");
//                information.setData(result);
//            }
//        }
//        return information;
//    }
//}