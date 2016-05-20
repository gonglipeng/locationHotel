package com.example.administrator.locationhotel.entity;

import java.io.Serializable;

/**
 * 评论相关的类
 *
 * Created by Administrator on 2016/5/18 0018.
 */
public class CommentEntity implements Serializable{
    private int ivItemIcon;
    private String tvItemUser;
    private String lv;
    private String tvItemTime;
    private int startNums;
    private String tvItemComments;

    public CommentEntity(int ivItemIcon, String tvItemUser, String lv, String tvItemTime, int startNums, String tvItemComments) {
        this.ivItemIcon = ivItemIcon;
        this.tvItemUser = tvItemUser;
        this.lv = lv;
        this.tvItemTime = tvItemTime;
        this.startNums = startNums;
        this.tvItemComments = tvItemComments;
    }

    public int getIvItemIcon() {
        return ivItemIcon;
    }

    public String getTvItemUser() {
        return tvItemUser;
    }

    public String getLv() {
        return lv;
    }

    public String getTvItemTime() {
        return tvItemTime;
    }

    public int getStartNums() {
        return startNums;
    }

    public String getTvItemComments() {
        return tvItemComments;
    }

    public void setIvItemIcon(int ivItemIcon) {
        this.ivItemIcon = ivItemIcon;
    }

    public void setTvItemUser(String tvItemUser) {
        this.tvItemUser = tvItemUser;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public void setTvItemTime(String tvItemTime) {
        this.tvItemTime = tvItemTime;
    }

    public void setStartNums(int startNums) {
        this.startNums = startNums;
    }

    public void setTvItemComments(String tvItemComments) {
        this.tvItemComments = tvItemComments;
    }


}
