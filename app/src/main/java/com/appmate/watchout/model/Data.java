package com.appmate.watchout.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
    private String  id;
    private String  userName;
    private String  userId;
    private String  userEmail;
    private String  event;
    private String  video;
    private String  image;
    private Location  location;
    private String severity;
    private String  type;
    private long  alertCount;
    private long  reportCount;
    ArrayList<String>   alerterList;
    ArrayList<String>   reporterList;

    public Data() {
    }

    public Data(String id, String userName, String userId, String userEmail, String event, String video, String image, Location location, String severity, String type, long alertCount, long reportCount, ArrayList<String> alerterList, ArrayList<String> reporterList) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.userEmail = userEmail;
        this.event = event;
        this.video = video;
        this.image = image;
        this.location = location;
        this.severity = severity;
        this.type = type;
        this.alertCount = alertCount;
        this.reportCount = reportCount;
        this.alerterList = alerterList;
        this.reporterList = reporterList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public long getAlertCount() {
        return alertCount;
    }

    public void setAlertCount(long alertCount) {
        this.alertCount = alertCount;
    }

    public long getReportCount() {
        return reportCount;
    }

    public void setReportCount(long reportCount) {
        this.reportCount = reportCount;
    }

    public ArrayList<String> getAlerterList() {
        return alerterList;
    }

    public void setAlerterList(ArrayList<String> alerterList) {
        this.alerterList = alerterList;
    }

    public ArrayList<String> getReporterList() {
        return reporterList;
    }

    public void setReporterList(ArrayList<String> reporterList) {
        this.reporterList = reporterList;
    }
}
