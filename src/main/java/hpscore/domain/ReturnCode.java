package hpscore.domain;

public class ReturnCode {
    //失败
    public final static int CODE_FAIL = -1;
    //成功
    public final static int CODE_SUCCESS = 0;

    //自定义的登录异常
    public final static int UNANTHORIZED=-2;
    //自定义的参数异常
    public final static int INVALID_PARAM=-3;
    //系统异常
    public final static int SYSTEM_ERROR=-4;
}
