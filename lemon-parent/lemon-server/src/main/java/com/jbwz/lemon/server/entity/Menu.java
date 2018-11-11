package com.jbwz.lemon.server.entity;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "jbwz_menu")
public class Menu {
    // 菜单编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String menuId;

    // 菜单名称
    private String menuName;

    // 菜单路径
    private String menuUrl;

    // 状态 0-可用 1-锁定 9 删除
    private String status;

    // 创建人
    private Integer createBy;

    // 创建时间
    private Date createTime;

    // 更新人
    private Integer updateBy;

    // 更新时间
    private Date updateTime;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}