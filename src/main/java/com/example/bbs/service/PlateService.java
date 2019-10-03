package com.example.bbs.service;

import com.example.bbs.entity.Plate;
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

    List<Plate> selectPlate(Integer start, Integer num);

    Integer addPlate(Plate plate);

    Integer deletePlate(Integer id);

    Integer updatePlate(Plate plate);

    Plate selectPlateByName(String name);

    Integer selectAllPlate();
}