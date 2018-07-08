package com.ygip.xrb_android.mvp.search.model;

/**
 * Created by XQM on 2017/7/23.
 */

public class Member {
    private String headUrl;
    private String name;
    private String grade;
    private String group;

    public Member(String name, String grade, String group) {
        this.name = name;
        this.grade = grade;
        this.group = group;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Member{" +
                "headUrl='" + headUrl + '\'' +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
