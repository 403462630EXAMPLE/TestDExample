package com.dx168.efsmobile.webview;

import android.content.Context;
import android.content.Intent;

import com.dx168.efsmobile.me.LoginActivity;
import com.dx168.efsmobile.widgets.PictureDialog;
import com.baidao.tools.UserHelper;
import com.ytx.library.provider.Domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bruce on 2/2/15.
 */
public class Navigator {

    static private void track(Context context, String data, WebViewActivity.DataType type) {
        String title = getTitle(data);
        Map<String, String> map = new HashMap<>();

//        if (type == DataType.ACTIVITY) {
//            map.put("activity", title);
//            MobclickAgent.onEvent(context, EventIDS.MESSAGE_ARTICLE_GOTO_ACTIVITY, map);
//            Tracker.getInstance(context).addLog(new LogData.Builder(context).event(EventIDS.MESSAGE_ARTICLE_GOTO_ACTIVITY).append("activity", title));
//        } else if (type == DataType.ARTICLE) {
//            map.put("title", title);
//            MobclickAgent.onEvent(context, EventIDS.MESSAGE_ARTICLE_GOTO_ACTIVITY, map);
//            Tracker.getInstance(context).addLog(new LogData.Builder(context).event(EventIDS.MESSAGE_ARTICLE_GOTO_ACTIVITY).append("title", title));
//        } else if (type == DataType.OPEN_ACCOUNT) {
//            map.put("title", title);
//            MobclickAgent.onEvent(context, EventIDS.MESSAGE_ARTICLE_GOTO_OPENACC, map);
//            Tracker.getInstance(context).addLog(new LogData.Builder(context).event(EventIDS.MESSAGE_ARTICLE_GOTO_OPENACC).append("title", title));
//        } else if(type == DataType.CHAT){
//            map.put("title", title);
//            MobclickAgent.onEvent(context, EventIDS.MESSAGE_ARTICLE_GOTO_IM, map);
//            Tracker.getInstance(context).addLog(new LogData.Builder(context).event(EventIDS.MESSAGE_ARTICLE_GOTO_IM).append("title", title));
//        }
    }

    static private String getTitle(String data) {
        JSONObject jsonObject = null;
        String title = "";
        try {
            jsonObject = new JSONObject(data);
            title = jsonObject.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return title;
    }

    public static void navigate(Navigation navigation, Context context) {
        Intent intent;
        switch (navigation.type) {
            case Chat:
                if(UserHelper.getInstance(context).isLogin()) {
//                    context.startActivity(new Intent(context, ChatActivity.class));
                    track(context, navigation.getDataJson(), WebViewActivity.DataType.CHAT);
                }else{
                    Intent loginIntent = new Intent(context, LoginActivity.class);
                    context.startActivity(loginIntent);
                }
                break;
            case Activity:
                intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(WebViewActivity.INTENT_DATA_TYPE, WebViewActivity.DataType.ACTIVITY);
                intent.putExtra(WebViewActivity.INTENT_DATA, navigation.getDataJson());
                context.startActivity(intent);
                track(context, navigation.getDataJson(), WebViewActivity.DataType.ACTIVITY);
                break;
            case Article:
                intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(WebViewActivity.INTENT_DATA_TYPE, WebViewActivity.DataType.ARTICLE);
                intent.putExtra(WebViewActivity.INTENT_DATA, navigation.getDataJson());
                context.startActivity(intent);
                track(context, navigation.getDataJson(), WebViewActivity.DataType.ARTICLE);
                break;
            case Lotto:
                intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(WebViewActivity.INTENT_DATA_TYPE, WebViewActivity.DataType.LOTTO);
                intent.putExtra(WebViewActivity.INTENT_DATA, navigation.getDataJson());
                context.startActivity(intent);
                break;
            case OpenAccount:
                intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(WebViewActivity.INTENT_DATA_TYPE, WebViewActivity.DataType.OPEN_ACCOUNT);
                JSONObject data = new JSONObject();
                try {
                    String url = Domain.get(Domain.DomainType.OPEN_ACCOUNT_PAGE);
                    data.put("url", url);
                    data.put("title", "极速开户");
                    intent.putExtra(WebViewActivity.INTENT_DATA, data.toString());
                    context.startActivity(intent);
                    track(context, navigation.getDataJson(), WebViewActivity.DataType.OPEN_ACCOUNT);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Image:
                try {
                    JSONArray urls = navigation.data.getJSONArray("urls");
                    int index = navigation.data.getInt("index");
                    if (!urls.isNull(0)) {
                        String url = urls.getString(index);
                        new PictureDialog(context).show(url);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Quote:
//                try {
//                    intent = new Intent(context, QuoteDetailActivity.class);
//                    intent.putExtra(QuoteDetailActivity.KEY_CATEGORY_ID, navigation.data.getString("quoteId"));
//                    context.startActivity(intent);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                break;
            case Login:
                Intent loginIntent = new Intent(context, LoginActivity.class);
                context.startActivity(loginIntent);
                break;
            case BindPhone:
//                Intent bindPhoneIntent = new Intent(context, BindPhoneActivity.class);
//                bindPhoneIntent.putExtra(LoginActivity.SOURCE_TYPE, "news");
//                bindPhoneIntent.putExtra(LoginActivity.SOURCE_ID, getTitle(navigation.getDataJson()));
//                context.startActivity(bindPhoneIntent);
                break;
        }
    }
}
