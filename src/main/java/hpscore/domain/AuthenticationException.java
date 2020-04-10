package hpscore.domain;

public class AuthenticationException extends Exception{
    //状态码
    private int errCode;

    public AuthenticationException(){

    }
    public AuthenticationException(int errCode,String message){
        super(message);
        this.errCode=errCode;
    }
    public AuthenticationException(int errCode,String message,Throwable cause){
        super(message,cause);
        this.errCode=errCode;
    }

    public int getErrCode() {
        return errCode;
    }
}
