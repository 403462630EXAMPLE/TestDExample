package com.baidao.data;

/**
 * Created by chengxin on 4/20/15.
 */
public class GetuiMessage {
    public class Body {
        public String after_open;
        public String play_vibrate;
        public String play_sound;
        public String play_lights;
        public String sound;
        public String text;
        public String ticker;
        public String title;
    }

    public class Extra {
        public int dataType;
        public int detailId;
        public int contentId;
        public int quoteId;
        public String id;
        public String target;
        public String time;
        public String sid;
        public String klineType;
    }

    public String display_type;
    public Body body;
    public Extra extra;

    public boolean isPlaySound() {
        return (body.play_sound != null && body.play_sound.equals("true")) ? true :false;
    }

    public boolean isPlayVibrate() {
        return (body.play_vibrate != null && body.play_vibrate.equals("true")) ? true :false;
    }

    public boolean isPlayLights() {
        return (body.play_lights !=null && body.play_lights.equals("true")) ? true :false;
    }

}
