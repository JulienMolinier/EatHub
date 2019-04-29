package com.example.eathub.models;
public enum Diet {
    none, gluten_free, vegan;

    @Override
    public String toString() {
        return "" + this.name();
    }

    public static Diet fromName(String name) {
        for (Diet d: Diet.values()) {
            if (d.toString().equals(name)) {
                return d;
            }
        }
        return null;
    }
}
