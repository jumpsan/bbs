package com.example.bbs.dao;

import com.example.bbs.entity.Plate;
import com.example.bbs.entity.Role;
import com.example.bbs.entity.User;

import java.util.List;

/**
 * (TPlate)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-20 14:00:18
 */
public interface PlateDao {
    List<Plate> selectAllPlateForUser(Integer start, Integer num);

    Integer addPlate(Plate plate);

    Integer deletePlate(Integer id);

    Integer updatePlate(Plate plate);

    List<Plate> selectPlateByNameForUser(String name,Integer start,Integer size);

    Integer selectAllPlateCountForUser();

    //Plate selectPlateById(Integer plateId);

    Integer selectPlateByNameCountForUser(String plateName);

    Plate selectPlateByFixNameForUser(String name);
    /**
     * 管理员 查询所有板块
     *
     * @return 数量
     */
    Integer selectAllPlateCountForManager();

    List<Plate> selectAllPlateForManager(Integer start, Integer size);

    Integer selectPlateByNameCountForManager(String plateName);

    List<Plate> selectPlateByNameForManager(String plateName, Integer start, Integer size);
    /**
     * 根据名字查找板块分区数
     *
     * @param name 板块名
     * @return 分区数
     */
    Integer selectSectionCountByNameForUser(String name);
    /**
     * 根据名字查找帖子数
     *
     * @param name 板块名
     * @return 贴子数
     */
    Integer selectPostCountByNameForUser(String name);
    /**
     * 根据名字查找版主
     *
     * @param name 板块名
     * @return 版主列表
     */
    List<User> selectUsersByNameForUser(String name);

    /**
     * 所有板块（包括禁用与不禁用的）
     * @param name 板块名
     * @return 板块
     */
    Plate selectPlateByFixNameForManager(String name);
    /**
     * 管理员根据名字查询特定板块
     *
     * @param name 板块名
     * @return 板块信息
     */
    Plate selectPlateByFixedNameForManager(String name);
    /**
     * 管理员根据名字查找板块分区数
     *
     * @param name 板块名
     * @return 分区数
     */
    Integer selectSectionCountByFixedNameForManager(String name);
    /**
     * 管理员根据名字查找帖子数
     *
     * @param name 板块名
     * @return 贴子数
     */
    Integer selectPostCountByFixedNameForManager(String name);
    /**
     * 管理员根据名字查找版主
     *
     * @param name 板块名
     * @return 版主列表
     */
    List<User> selectUsersByFixedNameForManager(String name);
    /**
     * 根据板块编号查询
     *
     * @param id 板块编号
     * @return 板块
     */
    Plate selectPlateByIdForUser(Integer id);
    /**
     * 根据板块编号查询分区数量
     *
     * @param id 板块编号
     * @return 分区数量
     */
    Integer selectSectionCountByIdForUser(Integer id);
    /**
     * 根据板块编号查询帖子数
     *
     * @param id 板块编号
     * @return 帖子数量
     */
    Integer selectPostCountByIdForUser(Integer id);
    /**
     * 根据板块编号查询版主
     *
     * @param id 板块编号
     * @return 版主信息
     */
    List<User> selectUsersByIdForUser(Integer id);
    /**
     * 根据板块编号查询
     *
     * @param id 板块编号
     * @return 板块
     */
    Plate selectPlateByIdForManager(Integer id);
    /**
     * 根据板块编号查询分区数量
     *
     * @param id 板块编号
     * @return 分区数量
     */
    Integer selectSectionCountByIdForManager(Integer id);
    /**
     * 根据板块编号查询帖子数
     *
     * @param id 板块编号
     * @return 贴子数
     */
    Integer selectPostCountByIdForManager(Integer id);
    /**
     * 根据板块编号查询版主
     *
     * @param id 板块编号
     * @return 版主信息
     */
    List<User> selectUsersByIdForManager(Integer id);

    /**
     * 根据分区编号查找
     * @param id 分区编号
     * @return
     */
    Plate selectPlateBySectionId(Integer id);

    /**
     * 查询所有板块但不分页
     * @return 板块
     */
    List<Plate> selectAllPlateWithoutPageForUser();
    /**
     * 查询所有板块但不分页
     * @return 板块
     */
    List<Plate> selectAllPlateWithoutPageForManager();

    /**
     * 查询指定名字的分区数量
     * @param name
     * @return
     */
    Integer selectSectionCountByFixedNameForUser(String name);
    /**
     * 查询指定名字的帖子
     * @param name
     * @return
     */
    Integer selectPostCountByFixedNameForUser(String name);

    /**
     *
     * @param name
     * @return
     */
    List<User> selectUsersByFixedNameForUser(String name);
}