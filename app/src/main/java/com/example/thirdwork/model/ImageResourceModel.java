package com.example.thirdwork.model;

public class ImageResourceModel {
    private String edit_diy_name;
    private String name;

    public ImageResourceModel(String edit_diy_name, String name) {
        this.edit_diy_name = edit_diy_name;
        this.name = name;
    }

    public String getEdit_diy_name() {
        return edit_diy_name;
    }

    public void setEdit_diy_name(String edit_diy_name) {
        this.edit_diy_name = edit_diy_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
