package com.ideabotkey.transportrouting.dto;

public class RouteStatsDto {
    public String routeName;
    public int utilization;
    public int totalTrips;
    public String status;

    public RouteStatsDto(String routeName, int utilization, int totalTrips, String status) {
        this.routeName = routeName;
        this.utilization = utilization;
        this.totalTrips = totalTrips;
        this.status = status;
    }

    public RouteStatsDto() {
        //TODO Auto-generated constructor stub
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public int getUtilization() {
        return utilization;
    }

    public void setUtilization(int utilization) {
        this.utilization = utilization;
    }

    public int getTotalTrips() {
        return totalTrips;
    }

    public void setTotalTrips(int totalTrips) {
        this.totalTrips = totalTrips;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RouteStatsDto{" +
                "routeName='" + routeName + '\'' +
                ", utilization=" + utilization +
                ", totalTrips=" + totalTrips +

                ", status='" + status + '\'' +
                '}';

    }
}
