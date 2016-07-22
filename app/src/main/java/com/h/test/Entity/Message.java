package cn.hdmoney.hdy.Entity;

/**
 * Created by Administrator on 2016/6/27 0027.
 */
public class Message {
    public int id; //消息id
    public String title; //消息标题
    public String content; //消息内容
    public int stauts; //1：未读，2：已读
    public int category; //类型（1：平台公告，2：帐户消息）
    public int url; //消息外链
    public String createTime; //消息时间
}
