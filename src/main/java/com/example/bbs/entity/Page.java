package com.example.bbs.entity;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private List<T> datas;
    private Integer totalPage;
}
