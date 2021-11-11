package com.example.demo.model;

public enum VehicleType {

    BUS(VehicleSize.LARGE, 5, false),
    CAR(VehicleSize.SMALL, 3, false),
    TRUCK(VehicleSize.LARGE, 6, true),
    VAN(VehicleSize.SMALL, 4, true);

    private final VehicleSize size;
    private final long ticketPrice;
    private final boolean shouldGoThroughCustoms;

    VehicleType(VehicleSize size, long ticketPrice, boolean shouldGoThroughCustoms) {
        this.size = size;
        this.ticketPrice = ticketPrice;
        this.shouldGoThroughCustoms = shouldGoThroughCustoms;
    }

    public VehicleSize getSize() {
        return size;
    }

    public long getTicketPrice() {
        return ticketPrice;
    }

    public boolean shouldGoThroughCustoms() {
        return shouldGoThroughCustoms;
    }
}
