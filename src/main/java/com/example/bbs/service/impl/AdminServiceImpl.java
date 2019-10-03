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
@Service("tAdminService")
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
    public Admin addAdmin(Admin admin) {
        return adminDao.addAdmin(admin);
    }

    /**
     * 根据账号删除管理员
     *
     * @param id 账号
     * @return 结果
     */
    @Override
    public boolean deleteAdminById(Integer id) {
        return adminDao.deleteAdminById(id) > 0;
    }
}