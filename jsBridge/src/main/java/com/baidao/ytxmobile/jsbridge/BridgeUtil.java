package com.baidao.ytxmobile.jsbridge;

import android.content.Context;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;

public class BridgeUtil {
	
	final static String YY_OVERRIDE_SCHEMA = "yy://";
	final static String YY_RETURN_DATA = "yy://return/";//格式为   yy://return/{function}/returncontent
	final static String EMPTY_STR = "";
	final static String UNDERLINE_STR = "_";
	final static String SPLIT_MARK = "/";
	
	final static String CALLBACK_ID_FORMAT = "JAVA_CB_%s";
	final static String JS_HANDLE_MESSAGE_FROM_JAVA = "javascript:YTXBridge._handleMessageFromYTX('%s');";
	final static String JS_FETCH_QUEUE_FROM_JAVA = "javascript:YTXBridge._fetchQueue();";
	public final static String JAVASCRIPT_STR = "javascript:";
	
	public static String parseFunctionName(String jsUrl){
		return jsUrl.replace("javascript:YTXBridge.", "").replaceAll("\\(.*\\);", "");
	}
	
	
	public static String getDataFromReturnUrl(String url) {
		String temp = url.replace(YY_RETURN_DATA, EMPTY_STR);
        int start = temp.indexOf(SPLIT_MARK);
        String data = null;
        try {
            data = temp.substring(start + 1);
        } catch (IndexOutOfBoundsException e) {}
        return data;
	}

	public static String getFunctionFromReturnUrl(String url) {
		String temp = url.replace(YY_RETURN_DATA, EMPTY_STR);
		String[] fuctionAndData = temp.split(SPLIT_MARK);
		if(fuctionAndData != null && fuctionAndData.length >= 1){
			return fuctionAndData[0];
		}
		return null;
	}

	
	
	/**
	 * js 文件将注入为第一个script引用
	 * @param view
	 * @param url
	 */
	public static void webViewLoadJs(WebView view, String url){
		String js = "var newscript = document.createElement(\"script\");";
		js += "newscript.src=\"" + url + "\";";
		js += "document.scripts[0].parentNode.insertBefore(newscript,document.scripts[0]);";
		view.loadUrl("javascript:" + js);
	}

    public static void webViewLoadLocalJs(WebView view, String path) {
        String script = assetFile2Str(view.getContext(), path);
        view.loadUrl("javascript:" + script);
    }
	
	public static String assetFile2Str(Context c, String urlStr){
		InputStream in = null;
		try{
			in = c.getAssets().open(urlStr);
			
			int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
 
            // byte buffer into a string
            String text = new String(buffer);
            return text;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

}
