package com.etkin.app.network.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Search {

    @SerializedName("eventid")
    @Expose
    private Integer eventid;
    @SerializedName("eventstarts")
    @Expose
    private Long eventstarts;
    @SerializedName("deadline")
    @Expose
    private Long deadline;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;

    public Integer getEventid() {
        return eventid;
    }

    public void setEventid(Integer eventid) {
        this.eventid = eventid;
    }

    public Long getEventstarts() {
        return eventstarts;
    }

    public void setEventstarts(Long eventstarts) {
        this.eventstarts = eventstarts;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

}