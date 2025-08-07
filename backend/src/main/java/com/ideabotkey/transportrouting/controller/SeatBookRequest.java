package com.ideabotkey.transportrouting.controller;

import java.util.List;

public class SeatBookRequest {
    private List<Long> seatIds;

    public List<Long> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<Long> seatIds) {
        this.seatIds = seatIds;
    }
}
