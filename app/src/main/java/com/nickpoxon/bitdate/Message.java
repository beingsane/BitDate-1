package com.nickpoxon.bitdate;

import java.util.Date;

/**
 * Created by nickpoxon on 17/08/2017.
 */

public class Message {

    private String mText;
    private String mSender;

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    private Date mDate;

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getSender() {
        return mSender;
    }

    public void setSender(String sender) {
        mSender = sender;
    }

}
