package hpscore.domain;

public class BadRequestException extends Exception {
    public BadRequestException(){

    }
    public BadRequestException(int errCode,String message){
        super(message);
    }
    public BadRequestException(int errCode,String message,Throwable cause){
        super(message,cause);
    }
}
