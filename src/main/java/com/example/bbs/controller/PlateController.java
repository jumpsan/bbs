package com.example.bbs.controller;

import com.example.bbs.entity.*;
import com.example.bbs.service.PlateService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * (TPlate)表控制层
 *
 * @author makejava
 * @since 2019-09-20 14:00:18
 */
@RestController
@RequestMapping("plate")
public class PlateController {
    /**
     * 服务对象
     */
    @Resource
    private PlateService plateService;


    /**
     *
     * @param page 页码
     * @param size 每页个数
     * @return 列表
     */
    @GetMapping("search/{page}/{size}")
    public Information<Page> selectAllPlate(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        //总页数
        Integer totalPage=plateService.selectAllPlate()/size+1;
        Information<Page> information =new Information<>();
        Integer start=(page-1)*size;
        List<Plate> plates = plateService.selectPlate(start, size);
        Page<Plate> pageObject=new Page<>();
        pageObject.setDatas(plates);
        pageObject.setTotalPage(totalPage);
        if(plates!=null) {
            information.setData(pageObject);
            information.setMsg("板块列表");
            information.setStatus(200);
        }else {
            information.setMsg("无");
            information.setStatus(204);
        }
        return information;
    }

    /**
     * 添加板块
     *
     * @param plate 板块信息
     * @return 结果
     */
    @PostMapping("add")
    public Information<Integer> addPlate(Plate plate, HttpSession session) {
        Information<Integer> information =new Information<>();
        Object admin_session = session.getAttribute("admin_session");
        Object role_session = session.getAttribute("role_session");
        String msg="";
        Integer status=202;
        if(admin_session==null && role_session==null){
            msg="权限不足";
            status=401;
        }else if (plate.getUserId()==null && plate.getName()!=null) {
            msg = "创建用户或板块名字不能为空";
            status=406;
        }else{
            Integer plateId = plateService.addPlate(plate);
            information.setData(plateId);
            if(plateId==-2){
                msg="名称重复";
                status=402;
            }
            else if (plateId==-3) {
                msg = "创建用户不存在";
                status=404;
            }
            else if(plateId==0){
                msg="添加失败";
                status=400;
            }
            else {
                msg = "创建成功";
                status=200;
            }
        }
        information.setMsg(msg);
        information.setStatus(status);
        return information;
    }

    /**
     * 删除板块
     *
     * @param id 板块id
     * @return 结果
     */
    @DeleteMapping("delete/{id}")
    public Information<Integer> deletePlate(@PathVariable("id") Integer id, HttpSession session) {
        Admin admin_session =(Admin) session.getAttribute("admin_session");
        Role role_session=(Role)session.getAttribute("role_session");
        Information<Integer> information =new Information<>();
        String msg="";
        Integer status=202;
        if(admin_session==null && role_session==null){
            msg="权限不足";
            status=401;
        }else{
            Integer result = plateService.deletePlate(id);
            if(result>0){
                msg="删除成功";
                status=200;
            }
            else {
                msg = "删除失败";
                status=400;
            }
            information.setData(result);
        }
        information.setStatus(status);
        information.setMsg(msg);
        return information;
    }

    /**
     * 修改板块
     * @param plate 板块信息
     * @return 结果
     */
    @PutMapping("update/{id}")
    public Information<Integer> updatePlate(@PathVariable Integer id, Plate plate, HttpSession session) {
        Admin admin_session =(Admin) session.getAttribute("admin_session");
        Role role_session=(Role)session.getAttribute("role_session");
        Information<Integer> information =new Information<>();
        String msg="";
        Integer status=202;
        if(admin_session==null && role_session==null){
            msg="权限不足";
            status=401;
        }
        else if(plate.getId()==null){
            msg="板块Id不可为空";
            status=403;
        } else{
            Integer result = plateService.updatePlate(plate);
            if(result>0){
                msg="修改成功";
                status=200;
            }
            else if(result==0) {
                msg = "修改失败";
                status=400;
            }
            else {
                msg="名称重复";
                status=402;
            }
            information.setData(result);
        }
        information.setStatus(status);
        information.setMsg(msg);
        return information;
    }
}