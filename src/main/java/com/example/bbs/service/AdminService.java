package com.example.bbs.service;

import com.example.bbs.entity.Admin;
import org.springframework.transaction.annotation.Transactional;

/**
 * (TAdmin)表服务接口
 *
 * @author makejava
 * @since 2019-09-22 13:37:50
 */
@Transactional
public interface AdminService {

    /**
     * 根据管理员账号和密码查询
     *
     * @param name       账号
     * @param password 密码
     * @return 管理员
     */
    Admin selectAdminByNameAndPassword(String name, String password);

    /**
     * 根据账号查询
     *
     * @param id 账号
     * @return 结果
     */
    Admin selectAdminById(Integer id);

    /**
     * 添加管理员
     *
     * @param admin 管理员信息
     * @return 账号
     */
    Integer addAdmin(Admin admin);

    //废弃
//    /**
//     * 根据账号删除管理员
//     *
//     * @param id 账号
//     * @return 结果
//     */
//    boolean deleteAdminById(Integer id);

    /**
     * 根据名称删除管理员
     * @param name 名称
     * @return 修改的行数
     */
    Integer deleteAdminByName(String name);

    /**
     * 根据名字查找
     * @param name 名称
     * @return 管理员信息
     */
    Admin selectAdminByName(String name);

    Integer deleteAdminById(Integer id);
}