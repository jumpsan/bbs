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
@Service("PlateService")
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
    public List<Plate> selectPlate(Integer start, Integer num) {
        return plateDao.selectPlate(start,num);
    }

    /**
     * 添加版块
     *
     * @param tPlate
     * @return
     */
    @Override
    public Integer addPlate(Plate tPlate) {
        Integer userId = tPlate.getUserId();
        Plate plate=selectPlateByName(tPlate.getName());
        Admin admin = adminDao.selectAdminById(tPlate.getUserId());
        List<Role> roles = roleDao.selectRoleByUserId(tPlate.getUserId());
        if(admin==null && roles==null)
            return -3;//管理员或版主不存在
        if(plate!=null)
            return -2; //板块名重复
        plateDao.addPlate(tPlate);//返回影响行数
        return tPlate.getId();
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
        if(plate.getName()!=null && selectPlateByName(plate.getName())!=null)
            return -2;
        return plateDao.updatePlate(plate);
    }

    /**
     * 根据板块名查询
     * @param name
     * @return
     */
    @Override
    public Plate selectPlateByName(String name) {
        return plateDao.selectPlateByName(name);
    }

    /**
     * 查询所有板块的数量
     * @return
     */
    @Override
    public Integer selectAllPlate() {
        return plateDao.selectAllPlate();
    }
}