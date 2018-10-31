package com.yangz.tank.entity;

public class Videostate {
    private Integer id;

    private String name;

    private Integer order;

    private String cssstyle;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getCssstyle() {
        return cssstyle;
    }

    public void setCssstyle(String cssstyle) {
        this.cssstyle = cssstyle == null ? null : cssstyle.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}