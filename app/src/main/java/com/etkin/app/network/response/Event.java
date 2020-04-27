package com.etkin.app.network.response;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("eventid")
    @Expose
    private Integer eventid;
    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("maxattendee")
    @Expose
    private Integer maxattendee;
    @SerializedName("currattendee")
    @Expose
    private Integer currattendee;
    @SerializedName("createddate")
    @Expose
    private Long createddate;
    @SerializedName("eventstarts")
    @Expose
    private Long eventstarts;
    @SerializedName("eventends")
    @Expose
    private Long eventends;
    @SerializedName("deadline")
    @Expose
    private Long deadline;
    @SerializedName("editdate")
    @Expose
    private Long editdate;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("aocomment")
    @Expose
    private Integer aocomment;
    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("avatarpath")
    @Expose
    private List<Image> avatarpath = null;

    public Integer getEventid() {
        return eventid;
    }

    public void setEventid(Integer eventid) {
        this.eventid = eventid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getMaxattendee() {
        return maxattendee;
    }

    public void setMaxattendee(Integer maxattendee) {
        this.maxattendee = maxattendee;
    }

    public Integer getCurrattendee() {
        return currattendee;
    }

    public void setCurrattendee(Integer currattendee) {
        this.currattendee = currattendee;
    }

    public Long getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Long createddate) {
        this.createddate = createddate;
    }

    public Long getEventstarts() {
        return eventstarts;
    }

    public void setEventstarts(Long eventstarts) {
        this.eventstarts = eventstarts;
    }

    public Long getEventends() {
        return eventends;
    }

    public void setEventends(Long eventends) {
        this.eventends = eventends;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public Long getEditdate() {
        return editdate;
    }

    public void setEditdate(Long editdate) {
        this.editdate = editdate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAocomment() {
        return aocomment;
    }

    public void setAocomment(Integer aocomment) {
        this.aocomment = aocomment;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}