package com.asodesunidos.tag;

public enum SavingType {
    NAVIDENO("Ahorro Navideño"),
    ESCOLAR("Ahorro Escolar"),
    MARCHAMO("Ahorro Marchamo"),
    EXTRAORDINARIO("Ahorro Extraordinario");

    private final String displayName;

    SavingType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
