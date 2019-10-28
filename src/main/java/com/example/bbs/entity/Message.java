package com.example.bbs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Message {

    private Integer id;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss ")
    private java.sql.Timestamp sendTime;
    private Integer receiveId;
    private Integer sendId;
    private User sendUser;
    private User receiveUser;


}
