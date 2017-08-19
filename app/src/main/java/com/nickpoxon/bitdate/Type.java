package com.nickpoxon.bitdate;

/**
 * Created by nickpoxon on 15/08/2017.
 */

public enum Type {
    Liked("Liked"),
    Matched("Matched"),
    Skipped("Skipped");

    Type(String label){
        this.label = label;
    };

    public String getLabel() {
        return label;
    }

    private String label;

}
