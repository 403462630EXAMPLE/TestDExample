//package com.baidao.chart;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//import com.baidao.data.FinanceCalendar;
//import com.baidao.data.TopMessage;
//import com.baidao.data.e.Server;
//import com.baidao.data.e.TopMessageType;
//import com.baidao.tools.UserHelper;
//import com.baidao.tools.Util;
//import com.baidao.tracker.LogData;
//import com.baidao.tracker.Tracker;
//import com.google.gson.Gson;
//
///**
// * Created by hexi on 14/12/23.
// */
//public class ChartListenerImpl implements ChartFragment.ChartListener{
//    private static final String TAG = "ChartListenerImple";
//
//    private static final String  ABOUT_TJ_URL = "http://www.5800.com/templets/ad/taijixian/";
//    private static final String  ABOUT_QKX_URL = "http://www.5800.com/appweb/qk/aboutqk.html";
//    private static final String ABOUT_YKX_URL = "http://www.5800.com/appweb/yik/yikintro.html";
//    private static final String ABOUT_QKT_URL = "http://win.5800.com/wap/act/20150917qkt/index.html";
//    private static final String ABOUT_BYJZ_URL = "http://www.5800.com/appweb/20151113/index.html";
//
//    private QuoteDetailFragment quoteDetailFragment;
//
//    public ChartListenerImpl(QuoteDetailFragment quoteDetailFragment) {
//        this.quoteDetailFragment = quoteDetailFragment;
//    }
//
//    private void connectCsr() {
//        if (!isQuoteDetailFragmentAttached()) {
//            return;
//        }
//        Context context = quoteDetailFragment.getActivity();
//        if (UserHelper.getInstance(context).isLogin()) {
//            context.startActivity(new Intent(context, ChatActivity.class));
//        } else {
//            quoteDetailFragment.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    PreLoginWindow window = new PreLoginWindow(context, R.drawable.marketing_advisor);
//                    window.setTrack("qkx","");
//                    window.showAtLocation(quoteDetailFragment.getView().getRootView());
//                }
//            });
//        }
//    }
//
//    private void openAccount() {
//        Intent intent = new Intent(quoteDetailFragment.getActivity(), WebViewActivity.class);
//        intent.putExtra(WebViewActivity.INTENT_DATA_TYPE, WebViewActivity.DataType.TOP_MESSAGE);
//        TopMessage data = new TopMessage();
//        data.setType(TopMessageType.OPEN_ACCOUNT);
//        intent.putExtra(WebViewActivity.INTENT_DATA, data);
//        quoteDetailFragment.startActivity(intent);
//    }
//
//    private void startWebViewActivity(String url, String title) {
//        Intent intent = new Intent(quoteDetailFragment.getActivity(), WebViewActivity.class);
//        intent.putExtra(WebViewActivity.INTENT_DATA_TYPE, WebViewActivity.DataType.LOAD_FROM_URL);
//        intent.putExtra(WebViewActivity.KEY_URL, url);
//        intent.putExtra(WebViewActivity.KEY_TITLE, title);
//        intent.putExtra(WebViewActivity.KEY_CAN_SHARE, false);
//        quoteDetailFragment.startActivity(intent);
//    }
//
//    private void startRegisterActivity() {
//        Intent intent = new Intent(quoteDetailFragment.getActivity(), LoginActivity.class);
//        intent.putExtra(LoginActivity.FUNCTION_TYPE, LoginActivity.REGISTER);
//        intent.putExtra(LoginActivity.SOURCE_TYPE, "kline.taiji");
//        quoteDetailFragment.startActivity(intent);
//    }
//
//    private boolean isQuoteDetailFragmentAttached() {
//        return quoteDetailFragment != null && quoteDetailFragment.getActivity() != null;
//    }
//
//    @Override
//    public void onBindPhone() {
//        Intent intent = new Intent(quoteDetailFragment.getActivity(), BindPhoneActivity.class);
//        quoteDetailFragment.startActivity(intent);
//    }
//
//    @Override
//    public void onHowUseForQK() {
//        boolean isFuckYKX = Util.getServer(quoteDetailFragment.getActivity()) == Server.TT;
//        if (isFuckYKX) {
//            startWebViewActivity(ABOUT_YKX_URL, quoteDetailFragment.getString(R.string.about_yk_title));
//        } else {
//            startWebViewActivity(ABOUT_QKX_URL, quoteDetailFragment.getString(R.string.about_qk_title));
//        }
//    }
//
//    @Override
//    public void onHowUseForTJ() {
//        startWebViewActivity(ABOUT_TJ_URL, quoteDetailFragment.getString(R.string.about_tj_title));
//    }
//
//    @Override
//    public void onOpenAccount() {
//        openAccount();
//    }
//
//    @Override
//    public void onApplyPermission() {
//        Log.d(TAG, "===connect csr===");
//        Server currentServer = Util.getServer(quoteDetailFragment.getActivity());
//        if (currentServer == Server.SSY || currentServer == Server.BSY) {
//            Util.makeCompanyCall(quoteDetailFragment.getActivity());
//        } else {
//            connectCsr();
//        }
//    }
//
//    @Override
//    public void onChangeLineType(String lineType) {
//        Log.d(TAG, "changeLineType: " + lineType);
//        if (quoteDetailFragment == null) {
//            return;
//        }
//        quoteDetailFragment.onChangeLineType(lineType);
//    }
//
//    @Override
//    public void onKLineEnterIndexSetting(String indexType) {
//        Log.d(TAG, "===kLine_enterIndexSetting indexType: " + indexType);
//        if (isQuoteDetailFragmentAttached()) {
//            Context context = quoteDetailFragment.getActivity();
//            Tracker.getInstance(context)
//                    .addLog(new LogData.Builder(context).pv(EventIDS.QUOTE_INDICATOR_CONFIG_PV).append("indicator", indexType));
//        }
//    }
//
//    @Override
//    public void onKLineChangeIndexType(String indexType) {
//        Log.d(TAG, "===Kline_changeIndexType indexType: " + indexType);
//        if (isQuoteDetailFragmentAttached()) {
//
//            Context context = quoteDetailFragment.getActivity();
//            Tracker.getInstance(context)
//                    .addLog(new LogData.Builder(context).event(EventIDS.QUOTE_INDICATOR_CLICK)
//                            .append("indicator", indexType));
//        }
//    }
//
//    @Override
//    public void onFinanceCalendarClick(String financeCalendar) {
//        Log.d(TAG, "economic calendar clicked: " + financeCalendar);
//        if (isQuoteDetailFragmentAttached()) {
//            FinanceCalendar calendar = new Gson().fromJson(financeCalendar, FinanceCalendar.class);
//            quoteDetailFragment.onEconomicCalendarClick(calendar);
//        }
//    }
//
//    @Override
//    public void onRegister() {
//        Log.d(TAG, "===onRegister===");
//        startRegisterActivity();
//    }
//
//    @Override
//    public void onHowUseForQKT() {
//        startWebViewActivity(ABOUT_QKT_URL, quoteDetailFragment.getString(R.string.about_qkt_title));
//    }
//
//    @Override
//    public void onHowUseForBY() {
//        startWebViewActivity(ABOUT_BYJZ_URL, quoteDetailFragment.getString(R.string.about_by_title));
//    }
//
//    @Override
//    public void onLogin(String lineType, String indexName) {
//        startLoginActivity(lineType, indexName);
//    }
//
//    private static String loginSourceType = "%s.%s";
//    private void startLoginActivity(String lineType, String indexName) {
//        Intent intent = new Intent(quoteDetailFragment.getActivity(), LoginActivity.class);
//        intent.putExtra(LoginActivity.FUNCTION_TYPE, LoginActivity.LOGIN);
//        intent.putExtra(LoginActivity.SOURCE_TYPE, String.format(loginSourceType, lineType, indexName));
//        quoteDetailFragment.startActivity(intent);
//    }
//}
