package com.coral.mini.program.common.business.vo;



import java.math.BigDecimal;
import java.util.List;


public class DepartmentInitVo {

    private String id;
    /**
     * 部门名称
     */
    private String title;
    /**
     * 父级部门
     */
    private String parent;
    /**
     * 排序字段
     */
    private BigDecimal sortOrder;
    /**
     * 子部门
     */
    private List<DepartmentInitVo> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public BigDecimal getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(BigDecimal sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<DepartmentInitVo> getChildren() {
        return children;
    }

    public void setChildren(List<DepartmentInitVo> children) {
        this.children = children;
    }
}
