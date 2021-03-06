package hpscore.domain;

public class BusinessException extends Exception {
    private int errCode;
    private String errMsg;

    public BusinessException() {
    }

    public BusinessException(int errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
    public BusinessException(int errCode,String errMsg,Throwable cause){
        super(errMsg,cause);
        this.errCode=errCode;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
