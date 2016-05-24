package sei.tk.service.testSchedule.Impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.*;
import sei.tk.service.dao.model.*;
import sei.tk.service.dao.model.vo.testSchedule.Mkpaper;
import sei.tk.service.testSchedule.TestDisService;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by 风中男子 on 2016-03-26.
 * 按照试卷学期和科目分发试卷到相应选了课的学生
 */
@Service
public class TestDisServiceImpl implements TestDisService {
    @Resource
    private TkMkpaperMapper tkMkpaperMapper;
    @Resource
    private TkSelectcourseMapper tkSelectcourseMapper;
    @Resource
    private TkCourseMapper tkCourseMapper;
    @Resource
    private TkTeacherMapper tkTeacherMapper;
    @Resource
    private TkTestscheduleMapper tkTestscheduleMapper;
    @Resource
    private TkTestpaperMapper tkTestpaperMapper;

    @Override
    public List<Mkpaper> getMkpaperInfo(Long ppassportId) {   //根据当前老师id得到其所选课程的所有制卷信息
        List<Mkpaper> mkpaperList=new ArrayList<>();    //最终要返回的数据

        //得到当前老师的全部课程号
        TkSelectcourseExample tkSelectcourseExample = new TkSelectcourseExample();
        TkSelectcourseExample.Criteria criteria = tkSelectcourseExample.createCriteria();
        criteria.andPpassportIdEqualTo(ppassportId);
        List<TkSelectcourse> tkSelectcourseList = tkSelectcourseMapper.selectByExample(tkSelectcourseExample);

        //根据课程号去得到制卷信息（无论哪个学期的）（Mkpaper是修改过的一个类）
        for (TkSelectcourse tkSelectcourse:tkSelectcourseList) {
            TkMkpaperExample tkMkpaperExample = new TkMkpaperExample();
            TkMkpaperExample.Criteria criteria1 = tkMkpaperExample.createCriteria();
            criteria1.andCourseIdEqualTo(tkSelectcourse.getCourseId());

            //查询一个课程下的所有制卷信息
            List<TkMkpaper> tkMkpaperList=tkMkpaperMapper.selectByExample(tkMkpaperExample);
            for(TkMkpaper tkMkpaper:tkMkpaperList){
                Mkpaper mkpaper=new Mkpaper();
                mkpaper.setMkpaperId(tkMkpaper.getMkpaperId());
                mkpaper.setCourseId(tkMkpaper.getCourseId());
                mkpaper.setMkpaperTerm(tkMkpaper.getMkpaperTerm());
                mkpaper.setMkpaperExtime(tkMkpaper.getMkpaperExtime());
                mkpaper.setMkpaperScore(tkMkpaper.getMkpaperScore());
                mkpaper.setMkpaperType(tkMkpaper.getMkpaperType());
                mkpaper.setPpassportId(tkMkpaper.getPpassportId());
                mkpaper.setPpassportTime(tkMkpaper.getPpassportTime());

                mkpaper.setCourseName((tkCourseMapper.selectByPrimaryKey(tkMkpaper.getCourseId())).getCourseName());
//                mkpaper.setTeaName((tkTeacherMapper.selectByPrimaryKey(tkMkpaper.getPpassportId())).getTeaName());

                mkpaperList.add(mkpaper);
            }
        }
        return mkpaperList;
    }

    @Override
    public Integer disTestpaper(Long ppassportId,Long mkpaperId, Date testStarttime, Date testEndtime) {   //根据当前的老师id给学生分发试卷并设置相应信息，没有分数
        Integer success=0;

        //根据老师选中的制卷id得到所有学生id
        TkSelectcourseExample tkSelectcourseExample=new TkSelectcourseExample();
        TkSelectcourseExample.Criteria criteria=tkSelectcourseExample.createCriteria();
        criteria.andPpassportIdEqualTo((tkMkpaperMapper.selectByPrimaryKey(mkpaperId)).getPpassportId());
        List<TkSelectcourse> tkStudentList=tkSelectcourseMapper.selectByExample(tkSelectcourseExample);

        //根据老师选中的制卷id得到其下所有的试卷
        TkTestpaperExample tkTestpaperExample=new TkTestpaperExample();
        TkTestpaperExample.Criteria criteria1=tkTestpaperExample.createCriteria();
        criteria1.andMkpaperIdEqualTo(mkpaperId);
        List<TkTestpaper> tkTestpaperList= tkTestpaperMapper.selectByExample(tkTestpaperExample);

        //循环学生id，从试卷id集合中选卷子给他
        Integer testpaperSize=tkTestpaperList.size();   //试卷总数，用来随机给卷
        for(TkSelectcourse tkSelectcourse:tkStudentList){
            TkTestschedule tkTestschedule=new TkTestschedule(); //要存入的数据

            Random random=new Random(System.currentTimeMillis());

            tkTestschedule.setTestpaperId((tkTestpaperList.get(random.nextInt(testpaperSize))).getTestpaperId());
            tkTestschedule.setPpassportId(tkSelectcourse.getPpassportId()); //学生ID
            tkTestschedule.setTestStarttime(testStarttime);
            tkTestschedule.setTestEndtime(testEndtime);
            tkTestschedule.setCourseId(tkSelectcourse.getCourseId());
            tkTestschedule.setCreatePpassportId(ppassportId);   //老师ID
            tkTestschedule.setCreateTime(new Date());

            success+=tkTestscheduleMapper.insertSelective(tkTestschedule);
        }

        return success;   //返回成功得到试卷的人数
    }
}
