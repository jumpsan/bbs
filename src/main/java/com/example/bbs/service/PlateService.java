package com.example.bbs.service;

import com.example.bbs.entity.Plate;
import com.example.bbs.entity.Role;
import com.example.bbs.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (TPlate)表服务接口
 *
 * @author makejava
 * @since 2019-09-20 14:00:18
 */
@Transactional
public interface PlateService {

    /**
     * 管理员 查询所有板块
     * @return 数量
     */
    Integer selectAllPlateCountForManager();
    /**
     * 管理员查询，包括禁用的和启用的
     * @param start 页码
     * @param size 每页个数
     * @return 列表
     */
    List<Plate> selectAllPlateForManager(Integer start, Integer size);

    /**
     * 管理查询 数量
     * @param plateName 名字
     * @return 数量
     */
    Integer selectPlateByNameCountForManager(String plateName);
    /**
     * 管理员查询，包括禁用的和启用的
     * @param plateName 板块名
     * @param start 起始
     * @param size 每页行数
     * @return 结果
     */
    List<Plate> selectPlateByNameForManager(String plateName, Integer start, Integer size);

    /**
     * 根据名字查询特定板块
     * @param name 板块名
     * @return 板块信息
     */
    Plate selectPlateByFixedNameForUser(String name);

    /**
     * 根据名字查找板块分区数
     * @param name 板块名
     * @return 分区数
     */
    Integer selectSectionCountByFixedNameForUser(String name);

    /**
     * 根据名字查找帖子数
     * @param name 板块名
     * @return 贴子数
     */
    Integer selectPostCountByFixedNameForUser(String name);

    /**
     * 根据名字查找版主
     * @param name 板块名
     * @return 版主列表
     */
    List<User> selectUsersByFixedNameForUser(String name);

    /**
     * 根据名字模糊查询板块
     * @param name
     * @param start
     * @param size
     * @return
     */
    List<Plate> selectPlateByNameForUser(String name, Integer start, Integer size);

    /**
     * 查询所有板块的数量
     * @return
     */
    Integer selectAllPlateCountForUser();

    /**
     * 名字模糊查询得到的结果的板块数量
     * @param plateName
     * @return
     */
    Integer selectPlateByNameCountForUser(String plateName);

    /**
     * 所有的帖子,不包括禁用的
     * @param start
     * @param num
     * @return
     */
    List<Plate> selectAllPlateForUser(Integer start, Integer num);
    /**
     * 根据板块编号查询
     * @param id 板块编号
     * @return 板块
     */
    Plate selectPlateByIdForUser(Integer id);
    /**
     * 根据板块编号查询分区数量
     * @param id 板块编号
     * @return 分区数量
     */
    Integer selectSectionCountByIdForUser(Integer id);
    /**
     * 根据板块编号查询帖子数
     * @param id
     * @return
     */
    Integer selectPostCountByIdForUser(Integer id);
    /**
     * 根据板块编号查询版主
     * @param id 板块编号
     * @return 版主信息
     */
    List<User> selectUsersByIdForUser(Integer id);
    /**
     * 添加板块
     * @param plate
     * @return
     */
    Integer addPlate(Plate plate);

    /**
     * 删除板块
     * @param id
     * @return
     */
    Integer deletePlate(Integer id);

    /**
     * 修改板块
     * @param plate
     * @return
     */
    Integer updatePlate(Plate plate);

    /**
     * 管理员根据名字查询特定板块
     * @param name 板块名
     * @return 板块信息
     */
    Plate selectPlateByFixedNameForManager(String name);

    /**
     * 管理员根据名字查找板块分区数
     * @param name 板块名
     * @return 分区数
     */
    Integer selectSectionCountByFixedNameForManager(String name);

    /**
     * 管理员根据名字查找帖子数
     * @param name 板块名
     * @return 贴子数
     */
    Integer selectPostCountByFixedNameForManager(String name);

    /**
     * 管理员根据名字查找版主
     * @param name 板块名
     * @return 版主列表
     */
    List<User> selectUsersByFixedNameForManager(String name);






    /**
     * 管理员根据板块编号查询
     * @param id 板块编号
     * @return 板块
     */
    Plate selectPlateByIdForManager(Integer id);
    /**
     * 管理员根据板块编号查询分区数量
     * @param id 板块编号
     * @return 分区数量
     */
    Integer selectSectionCountByIdForManager(Integer id);
    /**
     * 管理员根据板块编号查询帖子数
     * @param id
     * @return
     */
    Integer selectPostCountByIdForManager(Integer id);
    /**
     * 管理员根据板块编号查询版主
     * @param id 板块编号
     * @return 版主信息
     */
    List<User> selectUsersByIdForManager(Integer id);

    /**
     * 查询所有板块但不分页
     * @return
     */
    List<Plate> selectAllPlateWithoutPageForUser();
    /**
     * 管理员查询所有板块但不分页
     * @return
     */
    List<Plate> selectAllPlateWithoutPageForManager();
}