package sei.tk.service.exam.component.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TestpaperInfoMapper;
import sei.tk.service.dao.mapper.TkSubjectMapper;
import sei.tk.service.dao.mapper.TkTestscheduleMapper;
import sei.tk.service.dao.model.*;
import sei.tk.service.dao.model.vo.test.TypeAmount;
import sei.tk.service.exam.ExamMapForRedis;
import sei.tk.service.dao.model.vo.test.SubjectInfoVo;
import sei.tk.service.dao.model.vo.test.TestpaperInfVo;
import sei.tk.service.exam.component.PaperProvider;
import sei.tk.util.TkConfig;
import sei.tk.util.exception.TKException;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuruijie on 2016/4/7.
 * 期末考试试卷提供者的实现
 */
@Service
public class FormalPaperProvider implements PaperProvider {
    @Resource
    TestpaperInfoMapper testpaperInfoMapper;
    @Resource
    TkTestscheduleMapper tkTestscheduleMapper;
    @Resource
    TkSubjectMapper tkSubjectMapper;

    /**
     * 获取试卷
     * @param testpaperId 试卷编号
     * @return 试卷信息
     */
    @Override
    public TestpaperInfVo obtainTestPaper(long testpaperId) {
        TestpaperInfVo testpaperInfVo=testpaperInfoMapper.selectTestpaperInfoById(testpaperId);
        if(testpaperInfVo==null) {
            throw new TKException(TkConfig.INVALID_ACTION, "未找到相应试卷");
        }
        testpaperInfVo.setSubAmount(testpaperInfoMapper.selectTestpaperSubAmountById(testpaperId));

        return testpaperInfVo;
    }

    /**
     * 从试卷中拿题
     * @param stuId 学生学号
     * @param number 题目在试卷中的编号
     * @param examMapForRedis 用于访问缓存
     * @return 题目信息
     */
    @Override
    public SubjectInfoVo pickSubject(long stuId,int number,ExamMapForRedis examMapForRedis) {
        JSONArray subjectInfoList = JSON.parseArray(examMapForRedis.get(stuId,"SubjectInfoList"));
        SubjectInfoVo subjectInfoVo = subjectInfoList.getObject(number, SubjectInfoVo.class);
        if(subjectInfoVo==null){
            throw new TKException(TkConfig.INVALID_ACTION,"未找到相应题目");
        }
        return subjectInfoVo;
    }

    /**
     * 判断试卷是否可用
     * @param stuId 学生学号
     * @param testpaperId 试卷编号
     * @return 是否可用
     */
    @Override
    public boolean isPaperAvailable(long stuId, long testpaperId) {
        TkTestscheduleExample tkTestscheduleExample=new TkTestscheduleExample();
        TkTestscheduleExample.Criteria criteria = tkTestscheduleExample.createCriteria();
        criteria.andPpassportIdEqualTo(stuId);
        criteria.andTestpaperIdEqualTo(testpaperId);
        List<TkTestschedule> tkTestscheduleList=tkTestscheduleMapper.selectByExample(tkTestscheduleExample);

        if(tkTestscheduleList.size()<=0)return false;
        for(TkTestschedule ts:tkTestscheduleList){
            if(ts.getStuScore()<0)return true;
        }
        return false;
    }

    /**
     * 获取试卷中所有题目
     * @param testpaperId 试卷编号
     * @return 题目列表
     */
    @Override
    public List<TkSubject> paperSubjects(long testpaperId) {
        return testpaperInfoMapper.selectSubjectsFromTestpaper(testpaperId);
    }
}
