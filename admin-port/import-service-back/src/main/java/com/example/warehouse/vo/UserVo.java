package com.example.warehouse.vo;

import com.example.warehouse.vo.role.PermissionVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 2020年02月28日12:37
 *
 * @author lrq
 */
@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String password;
    private String usercode;
    private String createTime;
    private Integer level;
    private String loginName;
    private Integer countryId; // 国家ID
    private String country; // 国家
    private Integer currencyId; // 货币ID
    private String currency; // 货币名称
    private String exchangeRate; // 汇率

    private Integer commercialAreaId;//区域仓库ID
    private String commercialAreaName;//区域仓库名称
    private Integer roleId;
    private String roleName;

    private List<PermissionVo> permissionVos;
}
