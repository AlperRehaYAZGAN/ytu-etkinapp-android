package com.etkin.app.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ticket {

    @SerializedName("eventid")
    @Expose
    private Integer eventid;
    @SerializedName("cunique")
    @Expose
    private String cunique;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("regdate")
    @Expose
    private Integer regdate;
    @SerializedName("eventstarts")
    @Expose
    private Integer eventstarts;
    @SerializedName("images")
    @Expose
    private List<Image> images;
    @SerializedName("avatarpath")
    @Expose
    private List<Image> avatarpath;
    @SerializedName("username")
    @Expose
    private String username;

    public Integer getEventid() {
        return eventid;
    }

    public void setEventid(Integer eventid) {
        this.eventid = eventid;
    }

    public String getCunique() {
        return cunique;
    }

    public void setCunique(String cunique) {
        this.cunique = cunique;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRegdate() {
        return regdate;
    }

    public void setRegdate(Integer regdate) {
        this.regdate = regdate;
    }

    public Integer getEventstarts() {
        return eventstarts;
    }

    public void setEventstarts(Integer eventstarts) {
        this.eventstarts = eventstarts;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Image> getAvatarpath() {
        return avatarpath;
    }
    public void setAvatarpath(List<Image> avatarpath) {
        this.avatarpath = avatarpath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}