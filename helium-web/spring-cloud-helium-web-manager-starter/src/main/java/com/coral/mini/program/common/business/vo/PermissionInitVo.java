package com.coral.mini.program.common.business.vo;



import java.math.BigDecimal;
import java.util.List;


public class PermissionInitVo {

    private String id;

    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限级别
     */
    private int level;
    /**
     * 权限标题
     */
    private String title;
    /**
     * 权限路径
     */
    private String path;
    /**
     * 权限组件
     */
    private String component;
    /**
     * 权限图标
     */
    private String icon;
    /**
     * 排序字段
     */
    private BigDecimal sortOrder;

    /**
     * 子权限
     */
    private List<PermissionInitVo> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public BigDecimal getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(BigDecimal sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<PermissionInitVo> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionInitVo> children) {
        this.children = children;
    }
}
