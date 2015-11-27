package com.baidao.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rjhy on 14-12-5.
 */
public class CJCalendarResult {

    public String date;

    @SerializedName("news")
    public ArrayList<CJCalendar> cjCalendars;
}
