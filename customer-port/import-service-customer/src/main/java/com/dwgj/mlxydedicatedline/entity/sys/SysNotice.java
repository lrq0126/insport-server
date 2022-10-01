package com.dwgj.mlxydedicatedline.entity.sys;

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
  private String overdueTime;
  private int status;
  private int isTimingTask;
  private int isInitiative;
  private String readId;
  private int isRead;  // 临时字段，标识是否已读/未读

}
