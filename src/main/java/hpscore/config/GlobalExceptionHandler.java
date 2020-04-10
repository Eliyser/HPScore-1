package hpscore.config;

import com.alibaba.fastjson.JSONObject;
import hpscore.domain.AuthenticationException;
import hpscore.domain.BadRequestException;
import hpscore.domain.ReturnCode;
import org.hibernate.TypeMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;

/**
 * @Author haien
 * @Description 全局异常，对多个异常进行处理
 * @Date 2018/9/12
 **/
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody //返回json格式的数据
    @ExceptionHandler(value=Exception.class) //处理Controller层抛出的Exception及其子类,出异常会自动找到词类中对应的方法执行
    public Object defaulterrorHandler(HttpServletRequest request,Exception e){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("message",e.getMessage()); //不用定义errMsg应该也能返回事先填入的信息

        //自定义的登录异常
        if(e instanceof AuthenticationException){
            jsonObject.put("result",((AuthenticationException) e).getErrCode()); //获取抛出异常时塞进去的状态码
        }
        //自定义的参数异常
        if(e instanceof BadRequestException){
            jsonObject.put("result",ReturnCode.INVALID_PARAM); //其实状态码在这里塞进去好一点吧
        }
        //错误的请求方式||输入的参数类型不正确||输入参数不全
        if(e instanceof HttpRequestMethodNotSupportedException || e instanceof TypeMismatchException
                || e instanceof MissingServletRequestParameterException){
            jsonObject.put("result",ReturnCode.INVALID_PARAM);
        }
        //输入参数不满足约束
        if(e instanceof ValidationException || e instanceof MethodArgumentNotValidException){
            jsonObject.put("result",ReturnCode.INVALID_PARAM);
        }else{
            //其他情况均以系统异常处理
            jsonObject.put("result",ReturnCode.SYSTEM_ERROR);
        }

        return jsonObject;
    }

}
