package com.lab.model.enums;

public enum Color {
    BEIGE("BEIGE"),
    BLACK("BLACK"),
    CHROME("CHROME"),
    GRAY("GRAY"),
    GREEN("GREEN"),
    RED("RED"),
    YELLOW("YELLOW"),
    WHITE("WHITE");

    private final String colorName;

    Color(String colorName) {
        this.colorName = colorName;
    }

    public String getColorName() {
        return colorName;
    }
}

