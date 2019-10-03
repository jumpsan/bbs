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

    Section selectSectionByName(String name);

    Integer selectAllSectionByPlateId(Integer id);
}