package com.example.wastewarriors;

public class Bin {
    private String binId;
    private String issue;
    private String location;

    public Bin(String binId, String issue, String location) {
        this.binId = binId;
        this.issue = issue;
        this.location = location;
    }
    public String getBinId() {
        return binId;
    }
    public void setBinId(String BinId) {
        this.binId = binId;
    }
    public String getIssue() {
        return issue;
    }
    public void setIssue(String Issue) {
        this.issue = issue;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String Location) {
        this.location = location;
    }
}