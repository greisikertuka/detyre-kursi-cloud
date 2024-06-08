package com.lab.model.enums;

public enum Transmission {
    MANUAL("MANUAL"),
    AUTOMATIC("AUTOMATIC");

    private final String transmissionName;

    Transmission(String transmissionName) {
        this.transmissionName = transmissionName;
    }

    public String getTransmission() {
        return transmissionName;
    }
}

