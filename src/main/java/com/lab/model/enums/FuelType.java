package com.lab.model.enums;


public enum FuelType {
    ELECTRIC("ELECTRIC"),
    GAS("GAS"),
    GASOLINE("GASOLINE"),
    GASOLINE_GAS("GASOLINE_GAS"),
    PETROL("PETROL");

    private final String fuelType;

    FuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getFuelType() {
        return fuelType;
    }
}
