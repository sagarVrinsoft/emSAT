package com.vrinsoft.emsat.model.drawer_menu;

public class BinDrawerMenu {

    int menuImg;
    String menuTitle;

    public BinDrawerMenu(int menuImg, String menuTitle) {
        this.menuImg = menuImg;
        this.menuTitle = menuTitle;
    }

    public int getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(int menuImg) {
        this.menuImg = menuImg;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }
}
