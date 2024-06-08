package com.lab.model.enums;

public enum Brand {
    ALFA_ROMEO("ALFA_ROMEO"),
    AUDI("AUDI"),
    BMW("BMW"),
    BUGATTI("BUGATTI"),
    FERRARI("FERRARI"),
    FIAT("FIAT"),
    FORD("FORD"),
    MERCEDES_BENZ("MERCEDES_BENZ"),
    TOYOTA("TOYOTA"),
    VOLKSWAGEN("VOLKSWAGEN");

    private final String brandName;

    Brand(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }
}

