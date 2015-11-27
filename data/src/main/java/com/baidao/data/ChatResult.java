package com.baidao.data;

import java.util.ArrayList;

/**
 * Created by rjhy on 14-12-1.
 */
public class ChatResult{
    public long readId;
    public ArrayList<Chat> chatlist;

    public long getReadId() {
        return readId;
    }

    public void setReadId(long readId) {
        this.readId = readId;
    }

    public ArrayList<Chat> getChatlist() {
        return chatlist;
    }

    public void setChatlist(ArrayList<Chat> chatlist) {
        this.chatlist = chatlist;
    }
}
