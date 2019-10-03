package com.example.bbs.controller;

import com.example.bbs.entity.Approve;
import com.example.bbs.service.ApproveService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TApprove)表控制层
 *
 * @author makejava
 * @since 2019-09-22 14:04:17
 */
@RestController
@RequestMapping("tApprove")
public class ApproveController {
    /**
     * 服务对象
     */
    @Resource
    private ApproveService approveService;

    /**
     * 根据帖子Id查找
     *
     * @param id 帖子id
     * @return 点赞列表
     */
    @GetMapping("selectApproveByPostId")
    public List<Approve> selectApproveByPostId(Integer id) {
        return approveService.selectApproveByPostId(id);
    }

    /**
     * 添加点赞记录
     *
     * @param approve 点赞信息
     * @return 主键值
     */
    @GetMapping("addApprove")
    public Integer addApprove(Approve approve) {
        approve = approveService.addApprove(approve);
        return approve.getId();
    }

    /**
     * 删除点赞记录
     *
     * @param id 点赞id
     * @return 结果
     */
    @GetMapping("deleteApproveById")
    public boolean deleteApprove(Integer id) {
        return approveService.deleteApprove(id);
    }


}