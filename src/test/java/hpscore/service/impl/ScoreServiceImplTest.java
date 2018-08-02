package hpscore.service.impl;

import hpscore.domain.Score;
import hpscore.domain.Works;
import hpscore.repository.ScoreRepository;
import hpscore.repository.WorksRepository;
import hpscore.service.PingweiService;
import hpscore.service.ScoreService;
import hpscore.tools.ScoreUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/13
 * Time: 22:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScoreServiceImplTest {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private WorksRepository worksRepository;

    @Autowired
    private PingweiService pingweiService;
    private String model1="本科组";
    //private String model1="高职高专组";
    private int[] optionlist={10,15,20,20,20,15};
    //private int[] optionlist={10,15,10,25,25,15};
    private int year=2018;
    @Test
    public void checkIfAllTheSameTimes() throws Exception {

        List<String> pingweiList = pingweiService.selectAllCodeByModelAndYear(model1,year);
        int index = scoreService.checkIfAllTheSameTimes(model1,pingweiList,2018);
        System.out.println("index = "+index);
        Assert.assertThat(index,equalTo(pingweiList.size()));
    }

    //计算所有相对分
    @Test
    public void calculateRelativeScore() throws Exception {

        List<String> pingweiList = pingweiService.selectAllCodeByModelAndYear(model1,year);
        int index = scoreService.calculateRelativeScore(model1,pingweiList,2018);
        if(index!=pingweiList.size()){
           System.out.println("第"+pingweiList.get(index)+"位的委评相对分计算出错！");
        }
    }

    //计算某个评委相对分
    @Test
    public void calculateByCodeAndModel() throws Exception {

        int index = scoreService.calculateByCodeAndModel( "1", model1,2018);
        Assert.assertThat(index,is(1));
    }


    //生成测试分数
    @Test
    public void GenerateScoreData() throws Exception {

        scoreRepository.deleteByModelAndYear(model1,year);

        List<Works> worksList = worksRepository.findByModelAndYear(model1,year);
        System.out.println("works size="+worksList.size());
        List<String>pingweiList = pingweiService.selectAllCodeByModelAndYear(model1,year);
        System.out.println("pingwei size="+pingweiList.size());
        for(Works works:worksList){
            for (String pid: pingweiList){
                Score score = new Score(pid,works.getCode(),
                        ScoreUtil.GetRandomNumber(5,optionlist[0]),
                        ScoreUtil.GetRandomNumber(5,optionlist[1]),
                        ScoreUtil.GetRandomNumber(5,optionlist[2]),
                        ScoreUtil.GetRandomNumber(5,optionlist[3]),
                        ScoreUtil.GetRandomNumber(5,optionlist[4]),
                        ScoreUtil.GetRandomNumber(5,optionlist[5]),
                        2,
                        works.getModel(),
                        year);
                score.setEditor1("editor"+pingweiList.get(
                        ScoreUtil.GetRandomNumber(0,pingweiList.size())));
                scoreRepository.save(score);
            }
        }
        List<Score>scoreList = scoreRepository.findByModelAndYear(model1,year);
        Assert.assertThat(scoreList.size(),
                is(pingweiList.size()*worksList.size()));
    }

    @Test
    public void GeneratePingweiData() throws Exception {

    }
}