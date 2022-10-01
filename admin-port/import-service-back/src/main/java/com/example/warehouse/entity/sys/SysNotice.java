package com.example.warehouse.entity.sys;

import lombok.Data;

@Data
public class SysNotice {

  private int id;
  private String noticeTitle;
  private String noticeContent;
  private int noticeType;
  private String publisher;
  private String publisherId;
  private String createTime;
  private String releaseTime;
  private String overdueTime;
  private int status;
  private String readId;
  private int isTimingTask;
  private int isInitiative;

  private int isRead;  // 临时字段，标识是否已读/未读
  private String readName; // 临时字段，通知对象


}
