package sei.tk.service.testSchedule.Impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.*;
import sei.tk.service.dao.model.*;
import sei.tk.service.dao.model.vo.testSchedule.PageTestInfo;
import sei.tk.service.dao.model.vo.testSchedule.TeaGetAllSch;
import sei.tk.service.dao.model.vo.testSchedule.TestInfo;
import sei.tk.service.testSchedule.ExamInfoService;
import sei.tk.util.DateFormat;
import sei.tk.util.TkConfig;
import sei.tk.util.exception.TKException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 风中男子 on 2016-03-25.
 * 学生获取与当前用户相关的考试相关信息
 */
@Service
public class ExamInfoServiceImpl implements ExamInfoService {
    @Resource
    private TkTestscheduleMapper tkTestscheduleMapper;
    @Resource
    private TkTestpaperMapper tkTestpaperMapper;
    @Resource
    private TkMkpaperMapper tkMkpaperMapper;
    @Resource
    private TkCourseMapper tkCourseMapper;
    @Resource
    private TkTeacherMapper tkTeacherMapper;
    @Resource
    private TkSelectcourseMapper tkSelectcourseMapper;

    @Override
    public PageTestInfo getAllPaperInfo(Long ppassportId, Integer page, Integer rows) { //老师得到所有试卷（无论安排与否）
        if (page == null || rows == null) throw new TKException(TkConfig.INVALID_ACTION, "参数不合法");
        PageTestInfo pageTestInfo = new PageTestInfo();   //最终返回的数据
        List<TestInfo> testInfoList = new ArrayList<>();

        TkSelectcourseExample tkSelectcourseExample = new TkSelectcourseExample();    //根据老师查询课程号
        TkSelectcourseExample.Criteria criteria = tkSelectcourseExample.createCriteria();
        criteria.andPpassportIdEqualTo(ppassportId);
        Short courseId = (tkSelectcourseMapper.selectByExample(tkSelectcourseExample)).get(0).getCourseId();

        TkMkpaperExample tkMkpaperExample = new TkMkpaperExample();   //根据课程号查询所有制卷号(分页顺序根据制卷表)
        TkMkpaperExample.Criteria criteria1 = tkMkpaperExample.createCriteria();
        tkMkpaperExample.setStart((page - 1) * rows);
        tkMkpaperExample.setRow(rows);
        criteria1.andCourseIdEqualTo(courseId);
        List<TkMkpaper> tkMkpaperList = tkMkpaperMapper.selectByExample(tkMkpaperExample);
        pageTestInfo.setTotal(tkMkpaperList.size());

        for (TkMkpaper tkMkpaper : tkMkpaperList) { //根据制卷号得到所有试卷
            TkTestpaperExample tkTestpaperExample = new TkTestpaperExample();
            TkTestpaperExample.Criteria criteria2 = tkTestpaperExample.createCriteria();
            criteria2.andMkpaperIdEqualTo(tkMkpaper.getMkpaperId());

            TkTestpaper tkTestpaper = (tkTestpaperMapper.selectByExample(tkTestpaperExample)).get(0);

            TestInfo testInfo = new TestInfo();
            testInfo.setTestpaperId(tkTestpaper.getTestpaperId());
            testInfo.setTeaName((tkTeacherMapper.selectByPrimaryKey(tkMkpaper.getPpassportId())).getTeaName());
            testInfo.setMkpaperTerm(tkMkpaper.getMkpaperTerm());
            testInfo.setMkpaperScore(tkMkpaper.getMkpaperScore());
            testInfo.setMkpaperExtime(tkMkpaper.getMkpaperExtime());
            testInfo.setCourseId(tkMkpaper.getCourseId());
            testInfo.setCourseName((tkCourseMapper.selectByPrimaryKey(tkMkpaper.getCourseId())).getCourseName());
            if (tkTestpaper.getTestpaperType()) {   //试卷类型，默认0（考试试卷）1（模拟试卷）
                testInfo.setTestpaperType((short) 1);
            } else {
                testInfo.setTestpaperType((short) 0);
            }
            testInfoList.add(testInfo);
        }
        pageTestInfo.setRows(testInfoList);
        return pageTestInfo;
    }

    @Override
    public TestInfo getPaperInfoDetails(Long ppassportId, Long testscheduleId, Long testpaperId) { //得到试卷属性细节
        TestInfo testInfo = new TestInfo();   //最终返回的数据

        if (testscheduleId != null) { //说明需要返回考试开始时间和结束时间
            TkTestschedule tkTestschedule=tkTestscheduleMapper.selectByPrimaryKey(testscheduleId);
            testInfo.setTestStarttime(DateFormat.DateToString(tkTestschedule.getTestStarttime(),"yyyy-MM-dd HH:mm:ss"));
            testInfo.setTestEndtime(DateFormat.DateToString(tkTestschedule.getTestEndtime(),"yyyy-MM-dd HH:mm:ss"));
            testpaperId=tkTestschedule.getTestpaperId();
        }

        TkTestpaper tkTestpaper = tkTestpaperMapper.selectByPrimaryKey(testpaperId);
        TkMkpaper tkMkpaper = tkMkpaperMapper.selectByPrimaryKey(tkTestpaper.getMkpaperId());

        testInfo.setTestpaperId(tkTestpaper.getTestpaperId());
        testInfo.setTeaName((tkTeacherMapper.selectByPrimaryKey(tkMkpaper.getPpassportId())).getTeaName());
        testInfo.setMkpaperTerm(tkMkpaper.getMkpaperTerm());
        testInfo.setMkpaperScore(tkMkpaper.getMkpaperScore());
        testInfo.setMkpaperExtime(tkMkpaper.getMkpaperExtime());
        testInfo.setCourseId(tkMkpaper.getCourseId());
        testInfo.setCourseName((tkCourseMapper.selectByPrimaryKey(tkMkpaper.getCourseId())).getCourseName());
        testInfo.setCreatePpassportId(ppassportId); //考试安排创建人，即当前老师
        if (tkTestpaper.getTestpaperType()) {   //试卷类型，默认0（考试试卷）1（模拟试卷）
            testInfo.setTestpaperType((short) 1);
        } else {
            testInfo.setTestpaperType((short) 0);
        }
        return testInfo;
    }

    @Override
    public PageTestInfo getAllExamInfo(Long ppassportId, Integer page, Integer rows) {    //老师得到所有已安排试卷
        if (page == null || rows == null) throw new TKException(TkConfig.INVALID_ACTION, "参数不合法");
        TeaGetAllSch teaGetAllSch = new TeaGetAllSch();
        teaGetAllSch.setCreatePpassportId(ppassportId);
        Integer total = tkTestscheduleMapper.getAllExamInfo(teaGetAllSch).size();  //查询考试安排表中的安排数量
        teaGetAllSch.setStart((page - 1) * rows);
        teaGetAllSch.setRow(rows);

        List<TkTestschedule> tkTestscheduleList = tkTestscheduleMapper.getAllExamInfo(teaGetAllSch);  //查询考试安排表中的相关信息

        PageTestInfo pageTestInfo = new PageTestInfo();   //最终要返回的数据
        pageTestInfo.setTotal(total);
        List<TestInfo> testInfoList = new ArrayList<>();

        for (TkTestschedule testschedule : tkTestscheduleList) {
            TestInfo testInfo = new TestInfo();
            testInfo.setTestscheduleId(testschedule.getTestscheduleId());
            testInfo.setTestpaperId(testschedule.getTestpaperId());
            testInfo.setTestStarttime(DateFormat.DateToString(testschedule.getTestStarttime(), "yyyy-MM-dd HH:mm:ss"));
            testInfo.setTestEndtime(DateFormat.DateToString(testschedule.getTestEndtime(), "yyyy-MM-dd HH:mm:ss"));
            testInfo.setCourseId(testschedule.getCourseId());
            testInfo.setCourseName((tkCourseMapper.selectByPrimaryKey(testschedule.getCourseId())).getCourseName());
            if ((tkTestpaperMapper.selectByPrimaryKey(testschedule.getTestpaperId())).getTestpaperType()) {   //试卷类型，默认0（考试试卷）1（模拟试卷）
                testInfo.setTestpaperType((short) 1);
            } else {
                testInfo.setTestpaperType((short) 0);
            }
            TkMkpaper tkMkpaper = tkMkpaperMapper.selectByPrimaryKey((tkTestpaperMapper.selectByPrimaryKey(testschedule.getTestpaperId())).getMkpaperId());
            testInfo.setMkpaperTerm(tkMkpaper.getMkpaperTerm());
            testInfo.setMkpaperExtime(tkMkpaper.getMkpaperExtime());
            testInfo.setMkpaperScore(tkMkpaper.getMkpaperScore());
            testInfo.setCreatePpassportId(tkMkpaper.getPpassportId());
            testInfo.setTeaName((tkTeacherMapper.selectByPrimaryKey(tkMkpaper.getPpassportId())).getTeaName());
            testInfoList.add(testInfo);
        }
        pageTestInfo.setRows(testInfoList);
        return pageTestInfo;
    }

    @Override
    public List<TestInfo> getFutureExamInfo(Long ppassportId) { //得到未考试卷，没有分数
        TkTestscheduleExample tkTestscheduleExample = new TkTestscheduleExample();
        TkTestscheduleExample.Criteria criteria = tkTestscheduleExample.createCriteria();
        criteria.andPpassportIdEqualTo(ppassportId);
        List<TkTestschedule> tkTestscheduleList = tkTestscheduleMapper.selectByExample(tkTestscheduleExample); //查询考试安排表中的相关信息

        List<TestInfo> testInfoList = new ArrayList<>();  //最终要返回的数据

        for (TkTestschedule testschedule : tkTestscheduleList) {
            if (testschedule.getTestStarttime().getTime() > System.currentTimeMillis()) {
                TestInfo testInfo = new TestInfo();
                testInfo.setTestscheduleId(testschedule.getTestscheduleId());
                testInfo.setTestpaperId(testschedule.getTestpaperId());
                testInfo.setTestStarttime(DateFormat.DateToString(testschedule.getTestStarttime(), "yyyy-MM-dd HH:mm:ss"));
                testInfo.setTestEndtime(DateFormat.DateToString(testschedule.getTestEndtime(), "yyyy-MM-dd HH:mm:ss"));
                testInfo.setCourseId(testschedule.getCourseId());
                testInfo.setCourseName((tkCourseMapper.selectByPrimaryKey(testschedule.getCourseId())).getCourseName());
                if ((tkTestpaperMapper.selectByPrimaryKey(testschedule.getTestpaperId())).getTestpaperType()) {   //试卷类型，默认0（考试试卷）1（模拟试卷）
                    testInfo.setTestpaperType((short) 1);
                } else {
                    testInfo.setTestpaperType((short) 0);
                }
                TkMkpaper tkMkpaper = tkMkpaperMapper.selectByPrimaryKey((tkTestpaperMapper.selectByPrimaryKey(testschedule.getTestpaperId())).getMkpaperId());
                testInfo.setMkpaperTerm(tkMkpaper.getMkpaperTerm());
                testInfo.setMkpaperExtime(tkMkpaper.getMkpaperExtime());
                testInfo.setMkpaperScore(tkMkpaper.getMkpaperScore());
                testInfo.setCreatePpassportId(tkMkpaper.getPpassportId());
                testInfo.setTeaName((tkTeacherMapper.selectByPrimaryKey(tkMkpaper.getPpassportId())).getTeaName());
                testInfoList.add(testInfo);
            }
        }
        return testInfoList;
    }

    @Override
    public List<TestInfo> getValidExamInfo(Long ppassportId) {  //得到进行中的考卷，没有分数
        TkTestscheduleExample tkTestscheduleExample = new TkTestscheduleExample();
        TkTestscheduleExample.Criteria criteria = tkTestscheduleExample.createCriteria();
        criteria.andPpassportIdEqualTo(ppassportId);
        List<TkTestschedule> tkTestscheduleList = tkTestscheduleMapper.selectByExample(tkTestscheduleExample); //查询考试安排表中的相关信息

        List<TestInfo> testInfoList = new ArrayList<>();  //最终要返回的数据

        for (TkTestschedule testschedule : tkTestscheduleList) {
            if (testschedule.getTestEndtime().getTime() > System.currentTimeMillis() && testschedule.getTestStarttime().getTime() <= System.currentTimeMillis()) {
                TestInfo testInfo = new TestInfo();
                testInfo.setTestscheduleId(testschedule.getTestscheduleId());
                testInfo.setTestpaperId(testschedule.getTestpaperId());
                testInfo.setTestStarttime(DateFormat.DateToString(testschedule.getTestStarttime(), "yyyy-MM-dd HH:mm:ss"));
                testInfo.setTestEndtime(DateFormat.DateToString(testschedule.getTestEndtime(), "yyyy-MM-dd HH:mm:ss"));
                testInfo.setCourseId(testschedule.getCourseId());
                testInfo.setCourseName((tkCourseMapper.selectByPrimaryKey(testschedule.getCourseId())).getCourseName());
                if ((tkTestpaperMapper.selectByPrimaryKey(testschedule.getTestpaperId())).getTestpaperType()) {   //试卷类型，默认0（考试试卷）1（模拟试卷）
                    testInfo.setTestpaperType((short) 1);
                } else {
                    testInfo.setTestpaperType((short) 0);
                }
                TkMkpaper tkMkpaper = tkMkpaperMapper.selectByPrimaryKey((tkTestpaperMapper.selectByPrimaryKey(testschedule.getTestpaperId())).getMkpaperId());
                testInfo.setMkpaperTerm(tkMkpaper.getMkpaperTerm());
                testInfo.setMkpaperExtime(tkMkpaper.getMkpaperExtime());
                testInfo.setMkpaperScore(tkMkpaper.getMkpaperScore());
                testInfo.setCreatePpassportId(tkMkpaper.getPpassportId());
                testInfo.setTeaName((tkTeacherMapper.selectByPrimaryKey(tkMkpaper.getPpassportId())).getTeaName());
                testInfoList.add(testInfo);
            }
        }
        return testInfoList;
    }

    @Override
    public List<TestInfo> getOverdueExamInfo(Long ppassportId) {    //得到过期考卷
        TkTestscheduleExample tkTestscheduleExample = new TkTestscheduleExample();
        TkTestscheduleExample.Criteria criteria = tkTestscheduleExample.createCriteria();
        criteria.andPpassportIdEqualTo(ppassportId);
        List<TkTestschedule> tkTestscheduleList = tkTestscheduleMapper.selectByExample(tkTestscheduleExample); //查询考试安排表中的相关信息

        List<TestInfo> testInfoList = new ArrayList<>();  //最终要返回的数据

        for (TkTestschedule testschedule : tkTestscheduleList) {
            if (testschedule.getTestEndtime().getTime() <= System.currentTimeMillis()) {
                TestInfo testInfo = new TestInfo();
                testInfo.setTestscheduleId(testschedule.getTestscheduleId());
                testInfo.setTestpaperId(testschedule.getTestpaperId());
                testInfo.setTestStarttime(DateFormat.DateToString(testschedule.getTestStarttime(), "yyyy-MM-dd HH:mm:ss"));
                testInfo.setTestEndtime(DateFormat.DateToString(testschedule.getTestEndtime(), "yyyy-MM-dd HH:mm:ss"));
                testInfo.setCourseId(testschedule.getCourseId());
                testInfo.setStuBasescore(testschedule.getStuBasescore());
                testInfo.setStuScore(testschedule.getStuScore());

                testInfo.setCourseName((tkCourseMapper.selectByPrimaryKey(testschedule.getCourseId())).getCourseName());
                if ((tkTestpaperMapper.selectByPrimaryKey(testschedule.getTestpaperId())).getTestpaperType()) {   //试卷类型，默认0（考试试卷）1（模拟试卷）
                    testInfo.setTestpaperType((short) 1);
                } else {
                    testInfo.setTestpaperType((short) 0);
                }
                TkMkpaper tkMkpaper = tkMkpaperMapper.selectByPrimaryKey((tkTestpaperMapper.selectByPrimaryKey(testschedule.getTestpaperId())).getMkpaperId());
                testInfo.setMkpaperTerm(tkMkpaper.getMkpaperTerm());
                testInfo.setMkpaperExtime(tkMkpaper.getMkpaperExtime());
                testInfo.setMkpaperScore(tkMkpaper.getMkpaperScore());
                testInfo.setCreatePpassportId(tkMkpaper.getPpassportId());
                testInfo.setTeaName((tkTeacherMapper.selectByPrimaryKey(tkMkpaper.getPpassportId())).getTeaName());
                testInfoList.add(testInfo);
            }
        }
        return testInfoList;
    }

}
