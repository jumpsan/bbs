package com.example.bbs.service;

import com.example.bbs.entity.Information;
import com.example.bbs.entity.Section;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (TSection)表服务接口
 *
 * @author makejava
 * @since 2019-09-20 14:01:07
 */
@Transactional
public interface SectionService {


    List<Section> selectSectionByPlateId(Integer id, Integer start,Integer num);

    Integer addSection(Section section);

    Integer deleteSectionById(Integer id);

    Integer updateSection(Section section);

    Section selectSectionById(Integer id);

    Section selectSectionByName(String name);

    Integer selectAllSectionByPlateId(Integer id);
}