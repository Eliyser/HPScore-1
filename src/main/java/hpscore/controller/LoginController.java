package hpscore.controller;

import hpscore.domain.User;
import hpscore.repository.UserRepository;
import hpscore.service.LogInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tengj on 2017/4/10.
 */
/**
 *@Author: Ricardo
 *@Description: 登录控制器
 *@Date: 20:08 2018/7/13
 *@param:
 **/
@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogInfoService logInfoService;

    /**
     *@Author: Ricardo
     *@Description: 登录请求
     *@Date: 20:08 2018/7/13
     *@param: 
     **/
    /*
    @RequestMapping(value = "/userlogin")
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request,
                                    HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();
        String userName=request.getParameter("userName");
        String password=request.getParameter("passWord");
        String model=request.getParameter("model");

        //表单验证
        if(StringUtil.isNull(userName)||StringUtil.isNull(password)){
            map.put("result",0);
            map.put("message","用户名或密码为空");
            return map;
        }
        if(!StringUtil.isUserName(userName)){
            map.put("result",0);
            map.put("message","用户名请输入中文、字母、数字或下划线");
            return map;
        }
        if(!StringUtil.isPassword(password)){
            map.put("result",0);
            map.put("message","密码请输入字母、数字或下划线");
            return map;
        }

        User user = userRepository.findByNameAndPassword(userName,password);
        String ip = request.getRemoteAddr();
        long startTime = System.currentTimeMillis();
        String action = this.getClass().getName()+".login,";
        if(user!=null){

            request.getSession().setAttribute("user",user);
            request.getSession().setAttribute("ip",ip);
            request.getSession().setAttribute("model",model);

            User user1 = (User)request.getSession().getAttribute("user");
            System.out.println("login -- userlogin:user1.name="+user1.getName());

            action+="登录成功！";
            map.put("result",1);
            map.put("role",user.getRole());
        }else{
            action+="登录失败！";
            map.put("result",0);
        }
        logInfoService.addLoginInfo(userName,ip,startTime, action, model);
        return map;
    }
    */

    /**
     * @Author haien
     * @Description 替代上面方法，用@Valid来验证，测试全局异常
     * @Date 2018/9/12
     * @Param
     * @return
     **/
    @RequestMapping(value = "/userlogin")
    @ResponseBody
    public Map<String,Object> login(@Valid User user){ //自动封装
        Map<String,Object> map =new HashMap<String,Object>();
        User user1=userRepository.findByNameAndPassword(user.getName(),user.getPassword());
        if(user!=null){
            map.put("result",1);
            map.put("role",user.getRole());
        }else{
            map.put("result",0);
        }
        return map;
    }

    /**
     *@Author: Ricardo
     *@Description: 退出请求
     *@Date: 20:09 2018/7/13
     *@param:
     **/
    @RequestMapping(value = "/logout")
    @ResponseBody
    public Map<String,Object> logout(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();
        User user1 = (User)request.getSession().getAttribute("user");
        String userName= user1.getName();
        String ip = (String)request.getSession().getAttribute("ip");
        String model = (String)request.getSession().getAttribute("model");
        long startTime = System.currentTimeMillis();

        request.getSession().removeAttribute("user");
        request.getSession().invalidate();

        user1 = (User)request.getSession().getAttribute("user");
        if(user1 == null){
            System.out.println("logout 成功！ -- logout:user==null ");
            logInfoService.addLoginInfo(userName,ip,startTime,
                    this.getClass().getName()+".logout 成功！",model);
            try {
                response.sendRedirect("toLogin");
            } catch (IOException e) {
                e.printStackTrace();
            }
            map.put("result",1);
        }else{
            map.put("result",0);
        }
        return map;
    }
}
