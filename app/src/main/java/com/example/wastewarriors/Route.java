package com.example.wastewarriors;

public class Route {
    private String routeNo;
    private String binCount;
    private String binZone;

    public Route(String routeNo, String binCount, String binZone) {
        this.routeNo = routeNo;
        this.binCount = binCount;
        this.binZone = binZone;
    }
    public String getRouteNo() {
        return routeNo;
    }
    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }
    public String getBinCount() {
        return binCount;
    }
    public void setIssue(String Issue) {
        this.binCount = binCount;
    }
    public String getBinZone() {
        return binZone;
    }
    public void setBinZone (String binZone) {
        this.binZone = binZone;
    }
}