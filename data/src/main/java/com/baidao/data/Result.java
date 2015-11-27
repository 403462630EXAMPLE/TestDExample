package com.baidao.data;

/**
 * Created by rjhy on 14-11-28.
 */
public class Result<T> {
    public String code;
    public boolean succes;
    public boolean success;
    public T datas;
    public T data;
    public String msg;

    //ytxcrm need
    public int result;
    public String ytxUserName;
    public long cusId;

    //访客登录需要
    public String platform;

    public boolean isSucces() {
        return success || succes;
    }
}
