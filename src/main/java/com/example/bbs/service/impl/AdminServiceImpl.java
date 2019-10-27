package com.example.bbs.service.impl;

import com.example.bbs.dao.AdminDao;
import com.example.bbs.entity.Admin;
import com.example.bbs.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (TAdmin)表服务实现类
 *
 * @author makejava
 * @since 2019-09-22 13:37:51
 */
@Service("AdminService")
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminDao adminDao;


    /**
     * 根据管理员账号和密码查询
     *
     * @param name       账号
     * @param password 密码
     * @return 管理员
     */
    @Override
    public Admin selectAdminByNameAndPassword(String name, String password) {
        return adminDao.selectAdminByNameAndPassword(name, password);
    }

    /**
     * 根据账号查询
     *
     * @param id 账号
     * @return 结果
     */
    @Override
    public Admin selectAdminById(Integer id) {
        return adminDao.selectAdminById(id);
    }

    /**
     * 添加管理员
     *
     * @param admin 管理员信息
     * @return 账号
     */
    @Override
    public Integer addAdmin(Admin admin) {
        Admin checkAdmin = adminDao.selectAdminByName(admin.getName());
        if(checkAdmin!=null){
            //名称重复
            return -2;
        }
        return adminDao.addAdmin(admin);
    }

    /**
     * 根据名称删除
     * @param name 名称
     * @return 删除条数
     */
    @Override
    public Integer deleteAdminByName(String name) {
        return adminDao.deleteAdminByName(name);
    }

    /**
     * 根据名字查找
     *
     * @param name 名称
     * @return 管理员信息
     */
    @Override
    public Admin selectAdminByName(String name) {
        return adminDao.selectAdminByName(name);
    }

    @Override
    public Integer deleteAdminById(Integer id) {
        return adminDao.deleteAdminById(id);
    }

    //废弃
//    /**
//     * 根据账号删除管理员
//     *
//     * @param id 账号
//     * @return 结果
//     */
//    @Override
//    public boolean deleteAdminById(Integer id) {
//        return adminDao.deleteAdminById(id) > 0;
//    }
}