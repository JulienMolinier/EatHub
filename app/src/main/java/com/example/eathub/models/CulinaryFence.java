package com.example.eathub.models;

public enum CulinaryFence {
    none, vegan, italian, asian, mexican, indian, american;

    @Override
    public String toString() {
        return "" + this.name();
    }

    public static CulinaryFence fromName(String name) {
        for (CulinaryFence c: CulinaryFence.values()) {
            if (c.toString().equals(name)) {
                return c;
            }
        }
        return null;
    }
}
