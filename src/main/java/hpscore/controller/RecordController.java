package hpscore.controller;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/22
 * Time: 21:29
 */

import hpscore.domain.Score;
import hpscore.domain.User;
import hpscore.repository.UserRepository;
import hpscore.service.PingweiService;
import hpscore.service.ScoreService;
import hpscore.service.WorksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 *@ClassName: IndexController
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/22 21:29
 **/
@Controller
@RequestMapping
public class RecordController {

    private final static Logger logger = LoggerFactory.getLogger(RecordController.class);

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorksService worksService;
    @Autowired
    private PingweiService pingweiService;

    @RequestMapping(value = "/record1")
    public ModelAndView record1(@RequestParam("editor")String editor){

        User user = userRepository.findByName(editor);
        ModelAndView modelAndView = new ModelAndView("record1");
        if(user!=null){
            List<Score> scores = null;
            //管理员可以查看所有记录
            if(user.getRole()==0){
                scores = scoreService.selectAll();
                modelAndView.addObject("scoreList", scores);
            }
            //否则仅可以查看自己相关的记录
            else{
                scores = scoreService.selectByEditorAndModel(editor,"本科组");
                modelAndView.addObject("scoreList", scores);
            }
        }
        modelAndView.addObject("worksList", worksService.selectAllName());
        modelAndView.addObject("pingweiList", pingweiService.selectAllName());
        return modelAndView;
    }

    @RequestMapping("/record2")
    public ModelAndView record2(){
        //List<String> models =indexService.getModels();

        ModelAndView modelAndView = new ModelAndView("record2");
        modelAndView.addObject("model", "高职高专组");
        System.out.println("controller -- index --- index.html");
        return modelAndView;
    }

    @RequestMapping(value = "/record3")
    public ModelAndView record3(@RequestParam("editor")String editor,@RequestParam("model")String model){

        User user = userRepository.findByName(editor);
        ModelAndView modelAndView = new ModelAndView("record3");
        if(user!=null){
            List<Score> scores = null;
            //管理员可以查看所有记录
            if(user.getRole()==0){
                scores = scoreService.selectByModel(model);
                modelAndView.addObject("scoreList", scores);
            }
            //否则仅可以查看自己相关的记录
            else{
                scores = scoreService.selectByEditorAndModel(editor,model);
                modelAndView.addObject("scoreList", scores);
            }
        }
        modelAndView.addObject("worksList", worksService.selectAllName());
        modelAndView.addObject("pingweiList", pingweiService.selectAllName());
        return modelAndView;
    }
}
