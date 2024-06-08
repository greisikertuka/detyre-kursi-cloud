package com.lab.model.enums;

public enum BookingStatus {
    PENDING("PENDING"),
    ACTIVE("ACTIVE"),
    COMPLETED("COMPLETED"),
    CANCELED("CANCELED");

    private final String BookingS;

    BookingStatus(String BookingS) {
        this.BookingS = BookingS;
    }

    public String getBookingStatus() {
        return BookingS;
    }
}

