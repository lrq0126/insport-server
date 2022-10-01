package com.dwgj.mlxydedicatedline.vo.pin;

import com.dwgj.mlxydedicatedline.entity.ping.PingMain;
import com.dwgj.mlxydedicatedline.entity.ping.PingMainOrder;
import lombok.Data;

import java.util.List;

@Data
public class PingMainRespVo extends PingMain {

    private List<PingMainOrder> pingMainOrderList;

}
