package com.jbwz.lemon.server.entity;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "jbwz_dict")
public class Dict {
    // 默认主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dictId;

    // 字典编号
    private Integer itemId;

    // 字典名称
    private String itemName;

    // 子项编号
    private Integer subItemId;

    // 子项名称
    private String subItemName;

    // 是否启用 0-启用、1-禁用
    private String status;

    // 更新人
    private Integer updateBy;

    // 更新时间
    private Date updateTime;

    public Integer getDictId() {
        return dictId;
    }

    public void setDictId(Integer dictId) {
        this.dictId = dictId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(Integer subItemId) {
        this.subItemId = subItemId;
    }

    public String getSubItemName() {
        return subItemName;
    }

    public void setSubItemName(String subItemName) {
        this.subItemName = subItemName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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