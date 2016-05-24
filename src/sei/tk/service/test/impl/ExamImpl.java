package sei.tk.service.test.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.*;
import sei.tk.service.dao.model.*;
import sei.tk.service.dao.model.vo.test.StuAnswerVo;
import sei.tk.service.dao.model.vo.test.SubjectInfoVo;
import sei.tk.service.dao.model.vo.test.TestpaperInfVo;
import sei.tk.service.test.ExamService;
import sei.tk.util.TkClassifyUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/*
 * Created by liuruijie on 2016/3/12.
 * 处理考试信息获取相关业务
 */
@Service
public class ExamImpl implements ExamService {
    @Resource
    TkTestsubjectMapper tkTestsubjectMapper;
    @Resource
    TkSubjectMapper tkSubjectMapper;
    @Resource
    TkTestpaperMapper tkTestpaperMapper;
    @Resource
    TkMkpaperMapper tkMkpaperMapper;
    @Resource
    TkQuetypeMapper tkQuetypeMapper;
    @Resource
    TkCourseMapper tkCourseMapper;
    @Resource
    private TkTestscheduleMapper tkTestscheduleMapper;

    @Override
    public TestpaperInfVo initExam(HttpSession session, Long testpaperId) {//初始化考卷（试卷信息，题目编号列表）
        TestpaperInfVo testpaperInfVo = new TestpaperInfVo();
        testpaperId = 1L;//假装拿到了id为1的试卷

        //检验testpaperId是否有效，防止恶意在前端修改testpaperId
        TkTestscheduleExample tkTestscheduleExample = new TkTestscheduleExample();
        TkTestscheduleExample.Criteria criteria = tkTestscheduleExample.createCriteria();
        criteria.andPpassportIdEqualTo(((SessionPassport) session.getAttribute("sessionStudent")).getPassportId());
        criteria.andTestpaperIdEqualTo(testpaperId);
        List<TkTestschedule> tkTestscheduleList = tkTestscheduleMapper.selectByExample(tkTestscheduleExample); //查询考试安排表中的相关信息
        boolean flag=true;
        for(TkTestschedule tkTestschedule:tkTestscheduleList){
            if (!(tkTestscheduleList == null
                    ||tkTestschedule.getStuScore()!=-1
                    || tkTestschedule.getTestStarttime().getTime() >= System.currentTimeMillis()
                    ||tkTestschedule.getTestEndtime().getTime() <= System.currentTimeMillis())) {
                flag=false;//为空或不在考试时间范围内都属非法信息
            }
        }
        if(flag)return null;

        //拿到试卷相关信息
        TkTestpaper tkTestpaper = tkTestpaperMapper.selectByPrimaryKey(testpaperId);
        TkMkpaper tkMkpaper = tkMkpaperMapper.selectByPrimaryKey(tkTestpaper.getMkpaperId());

        //先查出试卷对应的题目（testsubject中间表）
        TkTestsubjectExample tkTestsubjectExample = new TkTestsubjectExample();
        TkTestsubjectExample.Criteria tsCriteria = tkTestsubjectExample.createCriteria();
        tsCriteria.andTestpaperIdEqualTo(testpaperId);
        List<TkTestsubject> tkTestsubjectList = tkTestsubjectMapper.selectByExample(tkTestsubjectExample);

        //获取当前考试试卷的所有题目的id
        List<Long> subIds = new ArrayList<>();
        for (TkTestsubject tkTestsubject : tkTestsubjectList) {
            subIds.add(tkTestsubject.getSubjectId());
        }

        //查询每个题型对应的名称和数量
        Map<String, Integer> typeAndAmount = new HashMap();
        List<TkSubject> tkSubjectList = new ArrayList<>();
        List<TkQuetype> tkQuetypeList = tkQuetypeMapper.selectByExample(new TkQuetypeExample());
        if (subIds != null && subIds.size() != 0)
            for (TkQuetype tkQuetype : tkQuetypeList) {//遍历所有类型的题目
                TkSubjectExample tkSubjectExample = new TkSubjectExample();
                TkSubjectExample.Criteria sbCriteria = tkSubjectExample.createCriteria();
                sbCriteria.andQuetypeIdEqualTo(tkQuetype.getQuetypeId());
                sbCriteria.andSubjectIdIn(subIds);//保证题目出自当前考试试卷
                List<TkSubject> tempList = tkSubjectMapper.selectByExample(tkSubjectExample);
                tkSubjectList.addAll(tempList);
                typeAndAmount.put("" + tkQuetype.getQuetypeId(), tempList.size());
            }
        testpaperInfVo.setTotalSubAmount(tkTestsubjectList.size());


        //查询考试科目
        TkCourse tkCourse = tkCourseMapper.selectByPrimaryKey(tkMkpaperMapper.selectByPrimaryKey(tkTestpaper.getMkpaperId()).getCourseId());

        //设置试卷的其他基本信息
        testpaperInfVo.setTestpaperId(testpaperId);
        testpaperInfVo.setCourse(tkCourse.getCourseName());
//        testpaperInfVo.setSubAmount(typeAndAmount);
        testpaperInfVo.setTerm(tkMkpaper.getMkpaperTerm());
        testpaperInfVo.setTotalScore(new Integer(tkMkpaper.getMkpaperScore()));
        testpaperInfVo.setTotalTime(new Integer(tkMkpaper.getMkpaperExtime()));

        //整理题目顺序
        TkClassifyUtil.subjectClassify(tkSubjectList, tkQuetypeList, tkTestsubjectList);

        //将试卷里的所有题目对象存起
        session.setAttribute("tkTestsubjectList", tkTestsubjectList);
        //存储试卷信息到session中，表示正在进行考试
        session.setAttribute("testpaperInfVo", testpaperInfVo);

        //初始化题目的次序（如第二题）为1，即最初显示第一题
        return testpaperInfVo;
    }

    @Override
    public SubjectInfoVo getSubjet(HttpSession session, int testNumber) {//获取题目信息（只一题）
        SubjectInfoVo subjectInfoVo = new SubjectInfoVo();

        //根据所需要的题目的次序（如第二题）得到题目对象
        List<TkTestsubject> tkTestsubjectList = (List<TkTestsubject>) session.getAttribute("tkTestsubjectList");
        if(tkTestsubjectList==null)return null;
        TkTestsubject tkTestsubject = tkTestsubjectList.get(testNumber);//(题目编号已排好序所以按顺序序号直接取题)

        //如果是需要得到已经做过的题的信息则从session中找
        StuAnswerVo stuAnswerVo = (StuAnswerVo) session.getAttribute("stuAnswerVo");
        if (stuAnswerVo != null) {
//            SubjectInfoVo stuSubject = stuAnswerVo.getAnswers().get(tkTestsubject.getSubjectId());
//            if (stuSubject != null) return stuSubject;
        }
        //再通过编号查询题目信息
        TkSubjectWithBLOBs tkSubjectWithBLOBs = tkSubjectMapper.selectByPrimaryKey(tkTestsubject.getSubjectId());

        //将查出的信息set到题目信息对象中
        subjectInfoVo.setScore(new Integer(tkTestsubject.getTestpaperScore()));
        subjectInfoVo.setSubjectId(tkSubjectWithBLOBs.getSubjectId());
        subjectInfoVo.setSubjectName(tkSubjectWithBLOBs.getSubjectName());
        subjectInfoVo.setType(new Integer(tkSubjectWithBLOBs.getQuetypeId()));

        //选择题（分隔符在选项中）
        if (subjectInfoVo.getType() == 1) {
            String[] options = tkSubjectWithBLOBs.getSubjectOption().split("@#%");
            List<String> optionList = Arrays.asList(options);
            subjectInfoVo.setSubjectOptions(optionList);
            //填空题（分隔符在答案中）
        } else if (subjectInfoVo.getType() == 3) {
            String[] blanks = tkSubjectWithBLOBs.getSubjectAnswer().split("@#%");
            subjectInfoVo.setBlankamount(blanks.length);
        }

        return subjectInfoVo;
    }

    @Override
    public void destroyExam(HttpSession session) {
        session.removeAttribute("tkTestsubjectList");
        session.removeAttribute("testpaperInfVo");
        session.removeAttribute("startTime");
        session.removeAttribute("stuAnswerVo");
    }

}
