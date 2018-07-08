package com.ygip.xrb_android.mvp.find.model;

import java.io.Serializable;

/**
 * Created by lockyluo on 2017/7/28.
 */

public class Project implements Serializable{
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String[] getMembers() {
        return members;
    }

    public void setMembers(String[] members) {
        this.members = members;
    }

    public String getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(String finishedDate) {
        this.finishedDate = finishedDate;
    }

    public String[] getScreenShots() {
        return screenShots;
    }

    public void setScreenShots(String[] screenShots) {
        this.screenShots = screenShots;
    }

    public int getProjectType() {
        return projectType;
    }

    public void setProjectType(int projectType) {
        this.projectType = projectType;
    }
    private String logo;
    private String projectName;
    private String[] members;
    private String finishedDate;
    private String[] screenShots;
    private int projectType;

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    private String finishDate;
}
