package com.yuyuedao.yydwechat.entity;


import java.util.ArrayList;
import java.util.List;

public class Menu {
    private String menuId;
    private String menuSname;
    private String menuType;
    private String menuModule;
    private String menuFile;
    private String menuIcon;
    private Integer menuFlag;
    private List<Menu> menuList =new ArrayList<>();

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuSname() {
        return menuSname;
    }

    public void setMenuSname(String menuSname) {
        this.menuSname = menuSname;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuModule() {
        return menuModule;
    }

    public void setMenuModule(String menuModule) {
        this.menuModule = menuModule;
    }

    public String getMenuFile() {
        return menuFile;
    }

    public void setMenuFile(String menuFile) {
        this.menuFile = menuFile;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Integer getMenuFlag() {
        return menuFlag;
    }

    public void setMenuFlag(Integer menuFlag) {
        this.menuFlag = menuFlag;
    }


    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
}
