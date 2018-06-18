package hpscore.service.impl;

import hpscore.service.ExcelService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/17
 * Time: 10:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelServiceImplTest {
    @Autowired
    private ExcelService excelService;

    private String model1="本科组";
    //private String model1="高专高职组";
    @Test
    public void reviewExcel() throws Exception {
        String result = excelService.reviewExcel(model1);
        Assert.assertThat(result,notNullValue());
    }

    @Test
    public void reviewTransferExcel() throws Exception {
        String result = excelService.reviewTransferExcel(model1);
        Assert.assertThat(result,notNullValue());
    }

    @Test
    public void relativeScoreExcel() throws Exception {
        String result = excelService.relativeScoreExcel(model1);
        Assert.assertThat(result,notNullValue());
    }

    @Test
    public void finalScoreExcel() throws Exception {

        String result = excelService.finalScoreExcel(model1);
        Assert.assertThat(result,notNullValue());
    }


    @Test
    public void scoringSumUpExcel() throws Exception {

        String result = excelService.scoringSumUpExcel(model1);
        Assert.assertThat(result,notNullValue());
    }
}