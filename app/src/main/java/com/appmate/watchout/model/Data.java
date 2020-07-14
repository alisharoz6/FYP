package com.appmate.watchout.model;

import java.io.Serializable;

public class Data implements Serializable {
    private String  userName;
    private String  userId;
    private String  userEmail;
    private String  event;
    private String  video;
    private String  image;
    private Location  location;
    private String severity;
    private String  type;
    private int  alertCount;
    private int  reportCount;

    public Data() {
    }

    public Data(String userName, String userId, String userEmail, String event, String video, String image, Location location, String serverity, String type, int alertCount, int reportCount) {
        this.userName = userName;
        this.userId = userId;
        this.userEmail = userEmail;
        this.event = event;
        this.video = video;
        this.image = image;
        this.location = location;
        this.severity = serverity;
        this.type = type;
        this.alertCount = alertCount;
        this.reportCount = reportCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAlertCount() {
        return alertCount;
    }

    public void setAlertCount(int alertCount) {
        this.alertCount = alertCount;
    }

    public int getReportCount() {
        return reportCount;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }
}
