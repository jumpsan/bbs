package com.example.bbs.controller;

import com.example.bbs.annotation.AuthChecker;
import com.example.bbs.dto.PlateDto;
import com.example.bbs.entity.*;
import com.example.bbs.service.PlateService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (TPlate)表控制层
 *
 * @author makejava
 * @since 2019-09-20 14:00:18
 */
@RestController
public class PlateController {
    /**
     * 服务对象
     */
    @Resource
    private PlateService plateService;

//
//    /**
//     *
//     * @param page 页码
//     * @param size 每页个数
//     * @return 列表
//     */
//    @GetMapping("plate/search/{page}/{size}")
//    public Information selectAllPlateForUser(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
//        Integer total=plateService.selectAllPlateCountForUser();
//        if(total==null || total==0){
//            return Information.success(204,"分页无内容返回",null);
//        }
//        //总页
//        Integer totalPage=total/size+1;
//        Integer start=(page-1)*size;
//        List<Plate> plates = plateService.selectAllPlateForUser(start, size);
//        Page<Plate> platePage=new Page<>();
//        platePage.setDatas(plates);
//        platePage.setTotalPage(totalPage);
//        return Information.success(200,"板块列表",platePage);
//    }
//
//    /**
//     * 根据板块名模糊查询
//     * @param plateName 板块名
//     * @param page 页码
//     * @param size 每页行数
//     * @return 结果
//     */
//    @GetMapping("plate/search/{plateName}/{page}/{size}")
//    public Information selectPlateForUser(@PathVariable String plateName,@PathVariable Integer page,@PathVariable Integer size){
//        Integer total=plateService.selectPlateByNameCountForUser(plateName);
//        if(total==null || total==0){
//            return Information.success(204,"分页无内容返回",null);
//        }
//        Integer totalPage=total/size+1;
//        Integer start=(page-1)*size;
//        List<Plate> plates = plateService.selectPlateByNameForUser(plateName,start, size);
//        Page<Plate> platePage=new Page<>();
//        platePage.setDatas(plates);
//        platePage.setTotalPage(totalPage);
//        return Information.success(200,"板块列表",platePage);
//    }

    /**
     * 根据指定的名字查询
     * @param name 板块名
     * @return 结果
     */
    @GetMapping("plate/search/fixed/{name}")
    public Information selectPlateByFixNameForUser(@PathVariable String name){
        if(name==null || name.length()<=0){
            return Information.error(406,"关键信息不可为空");
        }
        Plate plate = plateService.selectPlateByFixedNameForUser(name);
        Integer sectionNum=plateService.selectSectionCountByFixedNameForUser(name);
        List<User> roles=plateService.selectUsersByFixedNameForUser(name);
        if(plate==null || sectionNum==null || roles==null){
            return Information.error(400,"查询失败,板块可能被禁用");
        }

        PlateDto plateDto=new PlateDto(plate,sectionNum,roles);
        return Information.success(200,"查询结果",plateDto);

    }

    /**
     * 根据板块id查询
     * @param id 板块编号
     * @return 结果
     */
    @GetMapping("plate/search/id/{id}")
    public Information selectPlateByIdForUser(@PathVariable Integer id){
        if(id==null){
            return Information.error(406,"关键信息不能为空");
        }
        Plate plate=plateService.selectPlateByIdForUser(id);
        Integer sectionNum=plateService.selectSectionCountByIdForUser(id);
        List<User> roles=plateService.selectUsersByIdForUser(id);
        if(plate==null || sectionNum==null || roles==null){
            return Information.error(400,"查询失败,板块可能被禁用");
        }
        PlateDto plateDto=new PlateDto(plate,sectionNum,roles);
        return Information.success(200,"查询结果",plateDto);
    }

    /**
     * 查询所有板块但不分页
     * @return 板块列表
     */
    @GetMapping("plate/search/all")
    public Information selectAllPlateForUser(){
        List<Plate> plates = plateService.selectAllPlateWithoutPageForUser();
        if(plates==null || plates.size()<=0){
            return Information.error(204,"无内容返回");
        }
        return Information.success(200,"板块列表",plates);
    }

    /**
     * 管理员查询所有板块但不分页
     * @return 板块列表
     */
    @GetMapping("manager/plate/search/all")
    public Information selectAllPlateForManager(){
        List<Plate> plates = plateService.selectAllPlateWithoutPageForManager();
        if(plates==null || plates.size()<=0){
            return Information.error(204,"无内容返回");
        }
        return Information.success(200,"板块列表",plates);
    }


    /**
     * 管理员根据板块id查询
     * @param id 板块编号
     * @return 结果
     */
    @GetMapping("manager/plate/search/id/{id}")
    public Information selectPlateByIdForManager(@PathVariable Integer id){
        if(id==null){
            return Information.error(406,"关键信息不能为空");
        }
        Plate plate=plateService.selectPlateByIdForManager(id);
        Integer sectionNum=plateService.selectSectionCountByIdForManager(id);
        List<User> roles=plateService.selectUsersByIdForManager(id);
        if(plate==null || sectionNum==null ||  roles==null){
            return Information.error(400,"查询失败");
        }
        PlateDto plateDto=new PlateDto(plate,sectionNum,roles);
        return Information.success(200,"查询结果",plateDto);
    }


//    /**
//     * 管理员查询，包括禁用的和启用的
//     * @param page 页码
//     * @param size 每页个数
//     * @return 列表
//     */
//    @GetMapping("manager/plate/search/{page}/{size}")
//    public Information selectAllPlateForManager(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
//        Integer total=plateService.selectAllPlateCountForManager();
//        if(total==null || total==0){
//            return Information.success(204,"分页无内容返回",null);
//        }
//        //总页
//        Integer totalPage=total/size+1;
//        Integer start=(page-1)*size;
//        List<Plate> plates = plateService.selectAllPlateForManager(start, size);
//        Page<Plate> platePage=new Page<>();
//        platePage.setDatas(plates);
//        platePage.setTotalPage(totalPage);
//        return Information.success(200,"板块列表",platePage);
//    }
//
//    /**
//     * 管理员查询，包括禁用的和启用的
//     * @param plateName 板块名
//     * @param page 页码
//     * @param size 每页行数
//     * @return 结果
//     */
//    @GetMapping("manager/plate/search/{plateName}/{page}/{size}")
//    public Information selectPlateForManager(@PathVariable String plateName,@PathVariable Integer page,@PathVariable Integer size){
//        Integer total=plateService.selectPlateByNameCountForManager(plateName);
//        if(total==null || total==0){
//            return Information.success(204,"分页无内容返回",null);
//        }
//        Integer totalPage=total/size+1;
//        Integer start=(page-1)*size;
//        List<Plate> plates = plateService.selectPlateByNameForManager(plateName,start, size);
//        Page<Plate> platePage=new Page<>();
//        platePage.setDatas(plates);
//        platePage.setTotalPage(totalPage);
//        return Information.success(200,"板块列表",platePage);
//    }

    /**
     * 管理员根据指定的名字查询
     * @param name 板块名
     * @return 结果
     */
    @GetMapping("manager/plate/search/fixed/{name}")
    public Information selectPlateByFixNameForManager(@PathVariable String name){
        if(name==null || name.length()<=0){
            return Information.error(406,"关键信息不可为空");
        }
        Plate plate = plateService.selectPlateByFixedNameForManager(name);
        Integer sectionNum=plateService.selectSectionCountByFixedNameForManager(name);
        List<User> roles=plateService.selectUsersByFixedNameForManager(name);
        if(plate==null || sectionNum==null || roles==null){
            return Information.error(400,"查询失败");
        }
        PlateDto plateDto=new PlateDto(plate,sectionNum,roles);
        return Information.success(200,"查询结果",plateDto);

    }





    /**
     * 添加板块
     * @param status 状态
     * @param describes 描述
     * @param name 板块名
     * @return 结果
     */
    @PostMapping("manager/plate/add")
    public Information addPlate(HttpServletRequest request, String name, String describes, Integer status) {
        Integer id=(Integer)request.getAttribute("userId");
        Plate plate=new Plate();
        if (id==null || name==null || status==null) {
            return Information.error(406,"关键信息不可为空");
        }else{
            plate.setDescribes(describes);
            plate.setName(name);
            plate.setStatus(status);
            plate.setUserId(id);
            Integer plateId = plateService.addPlate(plate);
            if(plateId==-2){
                return Information.error(402,"名称重复");
            }
            else if (plateId==-3) {
                return Information.error(404,"创建用户不存在");
            }
            else if(plateId==-7){
                return Information.error(400,"创建失败");
            }
            else {
                Plate newPlate = plateService.selectPlateByIdForManager(plateId);
                return Information.success(200,"创建",newPlate);
            }
        }
    }

    /**
     * 删除板块
     *
     * @param id 板块id
     * @return 结果
     */
    @GetMapping("manager/plate/delete/{id}")
    public Information deletePlate(@PathVariable("id") Integer id) {
        Integer result = plateService.deletePlate(id);
        if(result>0){
            return Information.success("删除");
        }
        else {
            return Information.error(400,"删除失败");
        }
    }

    /**
     * 修改板块
     * @param plate 板块信息
     * @return 结果
     */
    @PostMapping("manager/plate/update")
    public Information updatePlate( Plate plate) {
        if(plate.getId()==null){
            return Information.error(406,"关键信息不可为空");
        } else{
            //不可修改创建者
            plate.setUserId(null);
            Integer result = plateService.updatePlate(plate);
            if(result>0){
                Plate newPlate = plateService.selectPlateByIdForManager(plate.getId());
                return Information.success(200,"修改",newPlate);
            }
            else if(result==0) {
                return Information.error(400,"修改失败");
            }
            else{
                return Information.error(402,"名称重复");
            }

        }
    }
}