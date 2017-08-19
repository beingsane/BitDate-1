package com.nickpoxon.bitdate;

/**
 * Created by nickpoxon on 15/08/2017.
 */

public class Action {

    private String byUser;
    private String toUser;
    private Type type;

    public Action(){
    };

    public Action(String byUser, String toUser, Type type) {
        this.byUser = byUser;
        this.toUser = toUser;
        this.type = type;
    }

    public String getByUser() {
        return byUser;
    }

    public String getToUser() {
        return toUser;
    }

    public Type getType() {
        return type;
    }

}
