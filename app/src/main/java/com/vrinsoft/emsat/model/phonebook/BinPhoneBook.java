package com.vrinsoft.emsat.model.phonebook;

import java.util.List;

public class BinPhoneBook {

    String name;
    List<String> numberList;

    String ContactNumberMobile;

    public String getContactNumberMobile() {
        return ContactNumberMobile;
    }

    public void setContactNumberMobile(String contactNumberMobile) {
        ContactNumberMobile = contactNumberMobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BinPhoneBook(String name, List<String> numberList) {
        this.name = name;
        this.numberList = numberList;
    }

    public List<String> getNumberList() {
        return numberList;
    }

    public void setNumberList(List<String> numberList) {
        this.numberList = numberList;
    }
}
