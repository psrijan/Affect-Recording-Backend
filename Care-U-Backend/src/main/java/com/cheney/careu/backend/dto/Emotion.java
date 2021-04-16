// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 23:05.
package com.cheney.careu.backend.dto;

public enum Emotion {
    HAPPY(1),
    SAD(2),
    IRRITATED(3),
    OTHERS(15);

    public final Long index;
    private long strength;

    Emotion (long index) {
        this.index = index;
    }

    public static Emotion valueOf(Long i) {
        for (Emotion e: values()) {
            if (e.index.equals(i))
                return e;
        }
        return OTHERS;
    }

    public void setStrength(long strength) {
        this.strength = strength;
    }
    public long getStrength() {return strength; }
}