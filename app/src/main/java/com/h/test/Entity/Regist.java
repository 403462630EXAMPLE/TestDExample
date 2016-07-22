package cn.hdmoney.hdy.Entity;

/**
 * Created by Administrator on 2016/6/22.
 */
public class Regist {


    /**
     * resultCode : ”000000”
     * resultDesc : ”成功”
     * result : {"uid":"100000"}
     */

    private String resultCode;
    private String resultDesc;
    /**
     * uid : 100000
     */

    private ResultBean result;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private String uid;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
