package com.example.bbs.dto;

import com.example.bbs.entity.Plate;
import com.example.bbs.entity.Role;
import com.example.bbs.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class PlateDto {
    //板块信息
    private Plate plate;
//    //帖子数
//    private Integer postNum;
    //分区数
    private Integer sectionNum;
    //版主信息
    private List<User> roles;
    public PlateDto(){}
    public PlateDto(Plate plate,Integer sectionNum,List<User> roles){
        this.plate=plate;
        this.sectionNum=sectionNum;
        this.roles=roles;
    }
}
