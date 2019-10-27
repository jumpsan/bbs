package com.example.bbs.service.impl;

import com.example.bbs.dao.*;
import com.example.bbs.entity.*;
import com.example.bbs.service.SectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TSection)表服务实现类
 *
 * @author makejava
 * @since 2019-09-20 14:01:07
 */
@Service("SectionService")
public class SectionServiceImpl implements SectionService {
    @Resource
    private SectionDao sectionDao;
    @Resource
    private PostDao postDao;
    @Resource
    private ReplyDao replyDao;
    @Resource
    private ApproveDao approveDao;
    @Resource
    private PlateDao plateDao;
    @Resource
    private  AdminDao adminDao;
    @Resource
    private RoleDao roleDao;
    @Resource CollectDao collectDao;

    /**
     * 根据板块id查询
     *
     * @param id 板块id
     * @return 分区列表
     */
    @Override
    public List<Section> selectSectionByPlateId(Integer id,Integer start,Integer num) {
        return sectionDao.selectSectionByPlateId(id,start,num);
    }

    /**
     * 添加分区
     *
     * @param section 分区
     * @return 主键值
     */
    @Override
    public Integer addSection(Section section) {

        String name=section.getName();
        Section sec=selectSectionByNameAndPlateId(name,section.getPlateId());
        if(sec!=null)
            return -2;//名称重复
        Plate plate  =plateDao.selectPlateByIdForManager(section.getPlateId());
        if(plate == null)
            return -5;//板块不存在
        if(plate.getStatus()==0){
            return -4;//该板块禁用
        }
        Admin admin = adminDao.selectAdminById(section.getUserId());
        //判断是否是该版的版主
        Role role = roleDao.selectRoleByUserIdAndPlateId(section.getUserId(), section.getPlateId());
        if(admin==null && role==null)
            return -3;//管理员或版主不存在
        sectionDao.addSection(section);//返回影响行数
        return section.getId();//返回主键值
    }

    /**
     * 删除分区
     *
     * @param id 分区编号
     * @return 结果
     */
    @Override
    public Integer deleteSectionById(Integer id) {
        Section section = sectionDao.selectSectionById(id);
        Admin admin = adminDao.selectAdminById(section.getUserId());
        Role role = roleDao.selectRoleByUserIdAndPlateId(section.getUserId(), section.getPlateId());
        if(admin==null && role==null)
            return -3;//管理员或版主不存在
        postDao.deletePostBySectionId(id);
        replyDao.deleteReplyBySectionId(id);
        approveDao.deleteApproveBySectionId(id);
        collectDao.deleteAllCollectBySectionId(id);
        return sectionDao.deleteSectionById(id);
    }

    /**
     * 根据主键更新分区
     *
     * @param section 分区
     * @return 结果
     */
    @Override
    public Integer updateSection(Section section) {
        Section originSection=sectionDao.selectSectionById(section.getId());
        if(section.getName()!=null && selectSectionByNameAndPlateId(section.getName(),originSection.getPlateId())!=null){
            return -2;
        }
        Admin admin = adminDao.selectAdminById(section.getUserId());
        Role role = roleDao.selectRoleByUserIdAndPlateId(section.getUserId(), originSection.getPlateId());
        if(admin==null && role==null)
            return -3;//管理员或版主不存在
        Plate plate  =plateDao.selectPlateByIdForManager(originSection.getPlateId());
        if(plate.getStatus()==0){
            return -4;//该板块禁用
        }
        return sectionDao.updateSection(section) ;
    }

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    @Override
    public Section selectSectionByNameAndPlateId(String name,Integer plateId) {
            return sectionDao.selectSectionByNameAndPlateId(name,plateId);
    }

    /**
     * 查询板块所有分区数量
     * @return
     */
    @Override
    public Integer selectAllSectionByPlateId(Integer id) {
        return sectionDao.selectAllSectionByPlateId(id);
    }

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    @Override
    public Section selectSectionById(Integer id) {
        return sectionDao.selectSectionById(id);
    }
}