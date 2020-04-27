package com.etkin.app.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("commentid")
    @Expose
    private Integer commentid;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("avatarpath")
    @Expose
    private Object avatarpath;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("createddate")
    @Expose
    private Integer createddate;
    @SerializedName("editdate")
    @Expose
    private Integer editdate;

    public Integer getCommentid() {
        return commentid;
    }

    public void setCommentid(Integer commentid) {
        this.commentid = commentid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Object getAvatarpath() {
        return avatarpath;
    }

    public void setAvatarpath(Object avatarpath) {
        this.avatarpath = avatarpath;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Integer createddate) {
        this.createddate = createddate;
    }

    public Integer getEditdate() {
        return editdate;
    }

    public void setEditdate(Integer editdate) {
        this.editdate = editdate;
    }

}