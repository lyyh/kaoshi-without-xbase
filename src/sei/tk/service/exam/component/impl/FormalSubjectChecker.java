package sei.tk.service.exam.component.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkSubjectMapper;
import sei.tk.service.dao.mapper.TkTestsubjectMapper;
import sei.tk.service.dao.model.TkSubject;
import sei.tk.service.dao.model.TkSubjectWithBLOBs;
import sei.tk.service.dao.model.TkTestsubject;
import sei.tk.service.dao.model.TkTestsubjectExample;
import sei.tk.service.dao.model.vo.test.StuAnswerVo;
import sei.tk.service.dao.model.vo.test.SubjectInfoVo;
import sei.tk.service.exam.component.SubjectChecker;
import sei.tk.util.TkConfig;
import sei.tk.util.exception.TKException;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liuruijie on 2016/4/7.
 * 期末考试题目检验者的实现
 */
@Service
public class FormalSubjectChecker implements SubjectChecker{
    @Resource
    TkTestsubjectMapper tkTestsubjectMapper;
    @Resource
    TkSubjectMapper tkSubjectMapper;

    /**
     * 检验题目
     * @param stuAnswerVo 学生答案信息
     * @return 选择判断题分数
     */
    @Override
    public int checkSubjects(StuAnswerVo stuAnswerVo) {
        List<SubjectInfoVo> answers = stuAnswerVo.getAnswers();

        int score=0;

        for(SubjectInfoVo subjectInfoVo:answers){
            if(subjectInfoVo.getType()==1||subjectInfoVo.getType()==2){
                if(tkSubjectMapper.selectByPrimaryKey(
                        subjectInfoVo.getSubjectId())
                        .getSubjectAnswer().equals(subjectInfoVo.getStuAnswer())){
                    score+=subjectInfoVo.getScore();
                }
            }
        }
        return score;
    }

    /**
     * 临时保存学生答案
     * @param stuAnswerVo 学生答案信息
     * @param number 题目在试卷中的编号
     * @param stuAnswer 学生答案
     * @param blankAnswers 学生填空题答案
     */
    @Override
    public void TempAnswer(StuAnswerVo stuAnswerVo,int number,String stuAnswer,String[] blankAnswers) {
        if(blankAnswers!=null)
            stuAnswerVo.getAnswers().get(number).setStuBlankAnswer(blankAnswers);
        else if(stuAnswer!=null){
            if(stuAnswerVo.getAnswers().get(number).getType()==1&&!stuAnswer.equals(""))
//                stuAnswerVo.getAnswers().get(number).setIntAnswer(
//                        new Integer(stuAnswer.charAt(0)));
            stuAnswerVo.getAnswers().get(number).setStuAnswer(stuAnswer);
        }
    }

    /**
     * 将po中的信息转到vo中
     * @param tkSubject 对用数据库tk_subject表的po
     * @param testpaperId 试卷编号
     * @return 题目信息
     */
    @Override
    public SubjectInfoVo subjectPoToVo(TkSubject tkSubject,long testpaperId) {
        TkSubjectWithBLOBs tkSubjectWithBLOBs= (TkSubjectWithBLOBs) tkSubject;

        SubjectInfoVo subjectInfoVo=new SubjectInfoVo();

        TkTestsubjectExample tkTestsubjectExample=new TkTestsubjectExample();
        TkTestsubjectExample.Criteria criteria = tkTestsubjectExample.createCriteria();
        criteria.andSubjectIdEqualTo(tkSubject.getSubjectId());
        criteria.andTestpaperIdEqualTo(testpaperId);

        List<TkTestsubject> tkTestsubjectList=tkTestsubjectMapper.selectByExample(tkTestsubjectExample);
        if (tkTestsubjectList.size()==0)return null;
        TkTestsubject tkTestsubject=tkTestsubjectList.get(0);

        subjectInfoVo.setScore(new Integer(tkTestsubject.getTestpaperScore()));
        subjectInfoVo.setSubjectId(tkSubject.getSubjectId());
        subjectInfoVo.setSubjectName(tkSubjectWithBLOBs.getSubjectName());
        subjectInfoVo.setType(new Integer(tkSubject.getQuetypeId()));
        subjectInfoVo.setChapterId(tkSubject.getChapterId());
        subjectInfoVo.setKnopointId(tkSubject.getKnopointId());
        if(subjectInfoVo.getType()==1||subjectInfoVo.getType()==5) {
            String[] options = tkSubjectWithBLOBs.getSubjectOption().split("@#%");
            subjectInfoVo.setSubjectOptions(Arrays.asList(options));
        }else if(subjectInfoVo.getType()==3){
            subjectInfoVo.setBlankamount(tkSubjectWithBLOBs.getSubjectAnswer().split("@#%").length);
        }

        return subjectInfoVo;
    }

}
