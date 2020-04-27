package com.etkin.app.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

    @SerializedName("cunique")
    @Expose
    private String cunique;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("regdate")
    @Expose
    private Integer regdate;
    @SerializedName("eventstarts")
    @Expose
    private String eventstarts;
    @SerializedName("images")
    @Expose
    private Object images;

    public String getCunique() {
        return cunique;
    }

    public void setCunique(String cunique) {
        this.cunique = cunique;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getEventstarts() {
        return eventstarts;
    }

    public void setEventstarts(String eventstarts) {
        this.eventstarts = eventstarts;
    }

    public Object getImages() {
        return images;
    }

    public void setImages(Object images) {
        this.images = images;
    }

}