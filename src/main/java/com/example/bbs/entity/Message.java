package com.example.bbs.entity;

import lombok.Data;

@Data
public class Message {

    private Integer id;
    private String content;
    private java.sql.Timestamp sendTime;
    private Integer receiveId;
    private Integer sendId;


}
