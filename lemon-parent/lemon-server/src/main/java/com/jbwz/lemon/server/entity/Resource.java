package com.jbwz.lemon.server.entity;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "jbwz_resource")
public class Resource {
    // 资源类型
    @Id
    private Integer resouceId;

    // 资源名称
    private String resouceName;

    // 资源路径
    private String resouceUrl;

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

    public Integer getResouceId() {
        return resouceId;
    }

    public void setResouceId(Integer resouceId) {
        this.resouceId = resouceId;
    }

    public String getResouceName() {
        return resouceName;
    }

    public void setResouceName(String resouceName) {
        this.resouceName = resouceName;
    }

    public String getResouceUrl() {
        return resouceUrl;
    }

    public void setResouceUrl(String resouceUrl) {
        this.resouceUrl = resouceUrl;
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