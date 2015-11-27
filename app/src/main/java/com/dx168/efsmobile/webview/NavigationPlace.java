package com.dx168.efsmobile.webview;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bruce on 2/2/15.
 */
public enum NavigationPlace {
    @SerializedName("0") None(0),
    @SerializedName("1") Chat(1),
    @SerializedName("2") Activity(2),
    @SerializedName("3") Article(3),
    @SerializedName("4") Lotto(4),
    @SerializedName("5") OpenAccount(5),
    @SerializedName("8") FinancialCalendar(8),
    @SerializedName("101") Image(101),
    @SerializedName("102") Quote(102),
    @SerializedName("103") Login(103),
    @SerializedName("104") BindPhone(104);

    private int value;
    private NavigationPlace(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static NavigationPlace fromValue(int value) {
        switch (value) {
            case 1:
                return Chat;
            case 2:
                return Activity;
            case 3:
                return  Article;
            case 4:
                return Lotto;
            case 5:
                return OpenAccount;
            case 8:
                return FinancialCalendar;
            case 101:
                return Image;
            case 102:
                return Quote;
            case 103:
                return Login;
            case 104:
                return BindPhone;
            default:
                return None;
        }
    }
}
