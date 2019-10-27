package com.example.bbs.dao;

import com.example.bbs.entity.Section;

import java.util.List;

/**
 * (TSection)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-20 14:01:07
 */
public interface SectionDao {
    List<Section> selectSectionByPlateId(Integer id,Integer start,Integer num);

    Integer addSection(Section section);

    Integer deleteSectionById(Integer id);

    Integer updateSection(Section section);

    Integer deleteSectionByPlateId(Integer id);

    Section selectSectionById(Integer id);

    Section selectSectionByNameAndPlateId(String name,Integer plateId);

    Integer selectAllSectionByPlateId(Integer id);

    /**
     * 根据板块禁用/启用分区
     * @param plateId 板块编号
     * @param status 板块状态
     * @return
     */
    Integer enableSectionByPlateId(Integer plateId, Integer status);
}