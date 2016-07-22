package cn.hdmoney.hdy.Entity;

/**
 * Created by Administrator on 2016/6/29 0029.
 */
public class CheckResponse {

    /**
     * resultCode : ”000000”
     * resultDesc : ”成功”
     * result : {"exist":"1"}
     */

    private String resultCode;
    private String resultDesc;
    /**
     * exist : 1
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
        private String exist;

        public String getExist() {
            return exist;
        }

        public void setExist(String exist) {
            this.exist = exist;
        }
    }
}
