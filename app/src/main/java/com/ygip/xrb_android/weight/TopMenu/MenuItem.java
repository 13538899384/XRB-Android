package com.ygip.xrb_android.weight.TopMenu;

/**
 * Created by XQM on 2017/9/5.
 */

public class MenuItem {
    private String id;
    private String text;

    public MenuItem(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
