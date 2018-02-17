package com.vrinsoft.emsat.utils.dialog.model;

import java.util.Comparator;


public class BinItems implements Comparator<BinItems> {
    String name;
    String ID;
    boolean isSelected;

    public BinItems() {

    }

    public BinItems(String ID, String name, boolean isSelected) {
        this.ID = ID;
        this.name = name;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public int compare(BinItems o1, BinItems o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
