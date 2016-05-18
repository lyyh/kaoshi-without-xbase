package sei.tk.service.testSchedule.Impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkStudentMapper;
import sei.tk.service.dao.mapper.TkTestscheduleMapper;
import sei.tk.service.dao.model.*;
import sei.tk.service.dao.model.vo.testSchedule.TeaGetSchId;
import sei.tk.service.dao.model.vo.testSchedule.TestInfo;
import sei.tk.service.testSchedule.TeaSchService;
import sei.tk.util.DateFormat;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 风中男子 on 2016-04-22.
 * 老师增加、删除、修改安排考试
 */
@Service
public class TeaSchServiceImpl implements TeaSchService {
    @Resource
    private TkTestscheduleMapper tkTestscheduleMapper;
    @Resource
    private TkStudentMapper tkStudentMapper;

    @Override
    public Integer addExamSch(Long ppassportId, TestInfo testInfo) { //老师添加考试安排（暂给所有学生发送安排）
        TkStudentExample tkStudentExample = new TkStudentExample();
        TkStudentExample.Criteria criteria = tkStudentExample.createCriteria();
        criteria.andPpassportIdIsNotNull();
        List<TkStudent> studentList = tkStudentMapper.selectByExample(tkStudentExample);

        TkTestschedule tkTestschedule=new TkTestschedule();
        tkTestschedule.setTestpaperId(testInfo.getTestpaperId());

        tkTestschedule.setTestStarttime(DateFormat.StringToDate(testInfo.getTestStarttime(),"yyyy-MM-dd HH:mm:ss"));
        tkTestschedule.setTestEndtime(DateFormat.StringToDate(testInfo.getTestEndtime(),"yyyy-MM-dd HH:mm:ss"));
        tkTestschedule.setCourseId(testInfo.getCourseId());
        tkTestschedule.setCreatePpassportId(testInfo.getCreatePpassportId());
        tkTestschedule.setCreateTime(new Date());

        for (TkStudent tkStudent : studentList) {
            tkTestschedule.setPpassportId(tkStudent.getPpassportId());
            tkTestscheduleMapper.insertSelective(tkTestschedule);
        }
        return studentList.size();    //返回被添加安排的学生人数
    }

    @Override
    public Integer delExamSch(Long ppassportId, Long testscheduleId) {  //老师删除考试安排（根据安排编号对应的安排时间）
        TkTestscheduleExample tkTestscheduleExample=new TkTestscheduleExample();
        TkTestscheduleExample.Criteria criteria=tkTestscheduleExample.createCriteria();
        criteria.andTestscheduleIdEqualTo(testscheduleId);
        Date createTime= (tkTestscheduleMapper.selectByExample(tkTestscheduleExample)).get(0).getCreateTime();

        TkTestscheduleExample tkTestscheduleExample1=new TkTestscheduleExample();
        TkTestscheduleExample.Criteria criteria1=tkTestscheduleExample1.createCriteria();
        criteria1.andCreateTimeEqualTo(createTime);
        return (tkTestscheduleMapper.deleteByExample(tkTestscheduleExample1))-1;    //返回受影响的学生数
    }

    @Override
    public Integer editExamSch(Long ppassportId, TestInfo testInfo) {//老师修改考试安排（根据安排编号对应的安排时间）
        TeaGetSchId teaGetSchId=new TeaGetSchId();
        teaGetSchId.setTestpaperId(testInfo.getTestpaperId());
        teaGetSchId.setTestStarttime(DateFormat.StringToDate(testInfo.getTestStarttime(),"yyyy-MM-dd HH:mm:ss"));
        teaGetSchId.setTestEndtime(DateFormat.StringToDate(testInfo.getTestEndtime(),"yyyy-MM-dd HH:mm:ss"));
        List<TkTestschedule> schIds=tkTestscheduleMapper.getAllSchId(teaGetSchId);

        TkTestschedule tkTestschedule=new TkTestschedule();
        tkTestschedule.setTestStarttime(DateFormat.StringToDate(testInfo.getNewStarttime(), "yyyy-MM-dd HH:mm:ss"));
        tkTestschedule.setTestEndtime(DateFormat.StringToDate(testInfo.getNewEndtime(), "yyyy-MM-dd HH:mm:ss"));

        for (TkTestschedule schId : schIds) {
            TkTestscheduleExample tkTestscheduleExample=new TkTestscheduleExample();
            TkTestscheduleExample.Criteria criteria2=tkTestscheduleExample.createCriteria();
            criteria2.andTestscheduleIdEqualTo(schId.getTestscheduleId());
            tkTestscheduleMapper.updateByExampleSelective(tkTestschedule, tkTestscheduleExample);
        }
        return schIds.size();    //返回被修改安排的学生人数
    }


}
