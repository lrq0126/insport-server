package com.example.warehouse.vo.role;

import com.example.warehouse.entity.role.Permission;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 2020年02月25日18:49
 *
 * @author lrq
 */
@Data
public class PermissionVo extends Permission implements Serializable {

    private String lable;

    /**
     * 是否拥有该权限
     * 1、拥有该权限   0、未拥有该权限
     */
    private Integer possessed;

    /**
     * 角色 所有的权限集合
     */
    private List<PermissionVo> children;

    private static final long serialVersionUID = 1L;

}
