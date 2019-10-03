package com.example.bbs.dao;

import com.example.bbs.entity.Plate;

import java.util.List;

/**
 * (TPlate)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-20 14:00:18
 */
public interface PlateDao {
    List<Plate> selectPlate(Integer start, Integer num);

    Integer addPlate(Plate plate);

    Integer deletePlate(Integer id);

    Integer updatePlate(Plate plate);

    Plate selectPlateByName(String name);

    Integer selectAllPlate();

    Plate selectPlateById(Integer plateId);
}