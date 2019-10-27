package com.example.bbs.controller;

import com.example.bbs.entity.*;
import com.example.bbs.service.SectionService;
import com.example.bbs.utils.Authorization;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * (TSection)表控制层
 *
 * @author makejava
 * @since 2019-09-20 14:01:07
 */
@RestController
public class SectionController {
    /**
     * 服务对象
     */
    @Resource
    private SectionService sectionService;

    /**
     * 查询某个板块下的分区
     *
     * @param id 板块编号
     * @return 分区列表
     */
    @GetMapping("section/select/plate/{id}/{page}/{size}")
    public Information selectSectionByPlateId(@PathVariable("id") Integer id,@PathVariable Integer page,@PathVariable Integer size) {
        if(id==null){
            return Information.error(406,"板块编号不可为空");
        }else{
            //总条数
            Integer total=sectionService.selectAllSectionByPlateId(id);
            if(total==null || total==0){
                return Information.error(204,"无内容");
            }
            //总页数
            Integer totalPage=total/size+1;
            Integer start=(page-1)*size;
            List<Section> sections= sectionService.selectSectionByPlateId(id,start, size);
            if(sections!=null) {
                Page<Section> sectionPage=new Page<>();
                sectionPage.setDatas(sections);
                sectionPage.setTotalPage(totalPage);
                return Information.success(200,"分区列表",sectionPage);
            }else {
                return Information.error(204,"无内容");
            }
        }
    }

    /**
     * 查询某个分区
     * @param id
     * @return
     */
    @GetMapping("section/select/{id}")
    public  Information selectSectionById(@PathVariable("id") Integer id){
        if(id==null){
            return Information.error(406,"分区编号不可为空");
        }else{
            Section section = sectionService.selectSectionById(id);
            if(section!=null) {
                return Information.success(200,"分区",section);
            }else {
                return Information.error(204,"无内容");
            }
        }
    }

    /**
     * 添加分区
     *
     * @param section 分区
     * @return 主键值，分区编号
     */
    @PostMapping("manager/section/add")
    public Information addSection(Section section, HttpServletRequest request) {
        Integer userId=(Integer) request.getAttribute("userId");
        section.setUserId(userId);
        if (section.getUserId()==null && section.getName()!=null && section.getPlateId()==null) {
            return Information.error(406,"关键信息不可为空");
        }else{
            Integer sectionId = sectionService.addSection(section);
            if(sectionId==-2){
                return Information.error(402,"名称重复");
            }
            else if (sectionId==-3) {
                return Information.error(404,"创建的用户不存在");
            }
            else if (sectionId==-4) {
                return Information.error(405,"板块被禁用");
            }else if (sectionId==-5) {
                return Information.error(407,"板块不存在");
            }
            else if(sectionId==0){
                return Information.error(400,"添加失败");
            }
            else {
                Section newSection = sectionService.selectSectionById(sectionId);
                return Information.success(200,"创建分区",newSection);
            }
        }
    }

    /**
     * 根据分区编号删除分区
     *
     * @param id 分区编号
     * @return 结果
     */
    @GetMapping("manager/section/delete/{id}")
    public Information deleteSectionById(@PathVariable Integer id) {
        Integer result = sectionService.deleteSectionById(id);
        if(result>0){
            return Information.success("删除分区");
        }else if (result==-3) {
            return Information.error(404,"无权");
        } else {
            return Information.error(400,"删除失败");
        }
    }

    /**
     * 修改分区
     *
     * @param section 分区
     * @return 结果
     */
    @PostMapping("manager/section/update")
    public Information updateSection(Section section,HttpServletRequest request) {
       if(section.getId()==null){
            return Information.error(406,"关键信息不可为空");
        }else {
            Integer userId=(Integer)request.getAttribute("userId");
            section.setUserId(userId);
            Integer re=sectionService.updateSection(section);
            if(re==null || re==0){
                return Information.error(400,"更新失败");
            }else if(re==-2){
                return Information.error(402,"名称重复");
            }else if(re==-4){
                return Information.error(405,"板块被禁用");
            } else if (re==-3) {
                return Information.error(404,"无权");
            }else{
                Section newSection = sectionService.selectSectionById(section.getId());
                return Information.success(200,"更新分区",newSection);
            }
        }
    }

//    /**
//     *
//     * @param id
//     * @param status
//     * @return
//     */
//    @PutMapping("enable/{id}")
//    public Information enableSection(@PathVariable Integer id,Integer status){
//        Section section=new Section();
//        section.setId(id);
//        section.setStatus(status);
//        Integer re=sectionService.updateSection(section);
//        Information<Integer> information=new Information<>();
//        information.setData(re);
//        if(re==null || re<=0){
//            information.setMsg("修改失败");
//            information.setStatus(400);
//        }else{
//            information.setMsg("修改成功");
//            information.setStatus(200);
//        }
//        return information;
//
//    }
}