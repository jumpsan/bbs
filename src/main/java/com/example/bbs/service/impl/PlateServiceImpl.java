package com.example.bbs.service.impl;

import com.example.bbs.dao.*;
import com.example.bbs.entity.Admin;
import com.example.bbs.entity.Plate;
import com.example.bbs.entity.Role;
import com.example.bbs.entity.User;
import com.example.bbs.service.PlateService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * (TPlate)表服务实现类
 *
 * @author makejava
 * @since 2019-09-20 14:00:18
 */
@Service("plateService")
public class PlateServiceImpl implements PlateService {
    @Resource
    private PlateDao plateDao;
    @Resource
    private SectionDao sectionDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private ReplyDao replyDao;
    @Resource
    private PostDao postDao;
    @Resource
    private ApproveDao approveDao;
    @Resource
    private AdminDao adminDao;
    @Resource
    private CollectDao collectDao;



    /**
     * 查询所有版块
     *
     * @return
     */
    @Override
    public List<Plate> selectAllPlateForUser(Integer start, Integer num) {
        return plateDao.selectAllPlateForUser(start,num);
    }

    /**
     * 根据板块编号查询
     *
     * @param id 板块编号
     * @return 板块
     */
    @Override
    public Plate selectPlateByIdForUser(Integer id) {
        return plateDao.selectPlateByIdForUser(id);
    }

    /**
     * 根据板块编号查询分区数量
     *
     * @param id 板块编号
     * @return 分区数量
     */
    @Override
    public Integer selectSectionCountByIdForUser(Integer id) {
        return  plateDao.selectSectionCountByIdForUser(id);
    }

    /**
     * 根据板块编号查询帖子数
     *
     * @param id
     * @return
     */
    @Override
    public Integer selectPostCountByIdForUser(Integer id) {
        return  plateDao.selectPostCountByIdForUser(id);
    }

    /**
     * 根据板块编号查询版主
     *
     * @param id 板块编号
     * @return 版主信息
     */
    @Override
    public List<User> selectUsersByIdForUser(Integer id) {
        return  plateDao.selectUsersByIdForUser(id);
    }

    /**
     * 添加版块
     *
     * @param plate
     * @return
     */
    @Override
    public Integer addPlate(Plate plate) {
        Integer userId = plate.getUserId();
        //名称不能重复
        Plate checkPlate=plateDao.selectPlateByFixNameForManager(plate.getName());
        Admin admin = adminDao.selectAdminById(plate.getUserId());
        List<Plate> plates = roleDao.selectRoleByUserId(userId);
        if(admin==null && plates==null)
            return -3;//管理员或版主不存在
        if(checkPlate!=null)
            return -2; //板块名重复
        Integer result=plateDao.addPlate(plate);//返回影响行数
        if(result<=0){
            return -7;
        }
        //添加版主
        Role role=new Role();
        role.setPlateId(plate.getId());
        role.setUserId(userId);
        roleDao.addRole(role);
        return plate.getId();
    }

    /**
     * 删除版块
     *
     * @param id
     * @return
     */
    @Override
    public Integer deletePlate(Integer id) {
        sectionDao.deleteSectionByPlateId(id);
        roleDao.deleteRoleByPlateId(id);
        replyDao.deleteReplyByPlateId(id);
        postDao.deletePostByPlateId(id);
        approveDao.deleteApproveByPlateId(id);
        collectDao.deleteAllCollectByPlateId(id);
        return plateDao.deletePlate(id);
    }

    /**
     * 修改版块
     *
     * @param plate
     * @return
     */
    @Override
    public Integer updatePlate(Plate plate) {
        Integer userId = plate.getUserId();
        //名称不能重复
        if(plate.getName()!=null){
            Plate checkPlate=plateDao.selectPlateByFixNameForManager(plate.getName());
            if(checkPlate!=null && checkPlate.getId()!=plate.getId())
                return -2; //板块名重复
        }
        Plate originPlate = plateDao.selectPlateByIdForManager(plate.getId());
        if(originPlate!=null && originPlate.getStatus()!=plate.getStatus()){
            //关联分区,如果从禁用改为启用，则所有分区都会启用
            sectionDao.enableSectionByPlateId(plate.getId(),plate.getStatus());
        }
        return plateDao.updatePlate(plate);
    }

    /**
     * 管理员根据名字查询特定板块
     *
     * @param name 板块名
     * @return 板块信息
     */
    @Override
    public Plate selectPlateByFixedNameForManager(String name) {
        return plateDao.selectPlateByFixedNameForManager(name);
    }

    /**
     * 管理员根据名字查找板块分区数
     *
     * @param name 板块名
     * @return 分区数
     */
    @Override
    public Integer selectSectionCountByFixedNameForManager(String name) {
        return plateDao.selectSectionCountByFixedNameForManager(name);
    }

    /**
     * 管理员根据名字查找帖子数
     *
     * @param name 板块名
     * @return 贴子数
     */
    @Override
    public Integer selectPostCountByFixedNameForManager(String name) {
        return plateDao.selectPostCountByFixedNameForManager(name);
    }

    /**
     * 管理员根据名字查找版主
     *
     * @param name 板块名
     * @return 版主列表
     */
    @Override
    public List<User> selectUsersByFixedNameForManager(String name) {
        return plateDao.selectUsersByFixedNameForManager(name);
    }

    /**
     * 管理员根据板块编号查询
     *
     * @param id 板块编号
     * @return 板块
     */
    @Override
    public Plate selectPlateByIdForManager(Integer id) {
        return plateDao.selectPlateByIdForManager(id);
    }

    /**
     * 管理员根据板块编号查询分区数量
     *
     * @param id 板块编号
     * @return 分区数量
     */
    @Override
    public Integer selectSectionCountByIdForManager(Integer id) {
        return plateDao.selectSectionCountByIdForManager(id);
    }

    /**
     * 管理员根据板块编号查询帖子数
     *
     * @param id
     * @return
     */
    @Override
    public Integer selectPostCountByIdForManager(Integer id) {
        return plateDao.selectPostCountByIdForManager(id);
    }

    /**
     * 管理员根据板块编号查询版主
     *
     * @param id 板块编号
     * @return 版主信息
     */
    @Override
    public List<User> selectUsersByIdForManager(Integer id) {
        return plateDao.selectUsersByIdForManager(id);
    }

    /**
     * 查询所有板块但不分页
     *
     * @return
     */
    @Override
    public List<Plate> selectAllPlateWithoutPageForUser() {
        return plateDao.selectAllPlateWithoutPageForUser();
    }

    /**
     * 管理员查询所有板块但不分页
     *
     * @return
     */
    @Override
    public List<Plate> selectAllPlateWithoutPageForManager() {
        return plateDao.selectAllPlateWithoutPageForManager();
    }

    /**
     * 根据板块名模糊查询
     * @param name
     * @return
     */
    @Override
    public List<Plate> selectPlateByNameForUser(String name, Integer start, Integer size) {
        return plateDao.selectPlateByNameForUser(name,start,size);
    }

    /**
     * 查询所有板块的数量
     * @return
     */
    @Override
    public Integer selectAllPlateCountForUser() {
        return plateDao.selectAllPlateCountForUser();
    }

    @Override
    public Integer selectPlateByNameCountForUser(String plateName) {
        return plateDao.selectPlateByNameCountForUser(plateName);
    }

    /**
     * 管理员 查询所有板块
     *
     * @return 数量
     */
    @Override
    public Integer selectAllPlateCountForManager() {
        return plateDao.selectAllPlateCountForManager();
    }

    /**
     * 管理员查询，包括禁用的和启用的
     *
     * @param start 页码
     * @param size  每页个数
     * @return 列表
     */
    @Override
    public List<Plate> selectAllPlateForManager(Integer start, Integer size) {
        return plateDao.selectAllPlateForManager(start,size);
    }

    /**
     * 管理查询 数量
     *
     * @param plateName 名字
     * @return 数量
     */
    @Override
    public Integer selectPlateByNameCountForManager(String plateName) {
        return plateDao.selectPlateByNameCountForManager(plateName);
    }

    /**
     * 管理员查询，包括禁用的和启用的
     *
     * @param plateName 板块名
     * @param start     起始
     * @param size      每页行数
     * @return 结果
     */
    @Override
    public List<Plate> selectPlateByNameForManager(String plateName, Integer start, Integer size) {
        return plateDao.selectPlateByNameForManager(plateName,start,size);
    }

    /**
     * 根据名字查询特定板块
     *
     * @param name 板块名
     * @return 板块信息
     */
    @Override
    public Plate selectPlateByFixedNameForUser(String name) {

        return plateDao.selectPlateByFixNameForUser(name);
    }

    /**
     * 根据名字查找板块分区数
     *
     * @param name 板块名
     * @return 分区数
     */
    @Override
    public Integer selectSectionCountByFixedNameForUser(String name) {
        return plateDao.selectSectionCountByFixedNameForUser(name);
    }

    /**
     * 根据名字查找帖子数
     *
     * @param name 板块名
     * @return 贴子数
     */
    @Override
    public Integer selectPostCountByFixedNameForUser(String name) {
        return plateDao.selectPostCountByFixedNameForUser(name);
    }

    /**
     * 根据名字查找版主
     *
     * @param name 板块名
     * @return 版主列表
     */
    @Override
    public List<User> selectUsersByFixedNameForUser(String name) {
        return plateDao.selectUsersByFixedNameForUser(name);
    }

}