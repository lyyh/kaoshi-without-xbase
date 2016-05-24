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
 * Created by �������� on 2016-04-22.
 * ��ʦ���ӡ�ɾ�����޸İ��ſ���
 */
@Service
public class TeaSchServiceImpl implements TeaSchService {
    @Resource
    private TkTestscheduleMapper tkTestscheduleMapper;
    @Resource
    private TkStudentMapper tkStudentMapper;

    @Override
    public Integer addExamSch(Long ppassportId, TestInfo testInfo) { //��ʦ��ӿ��԰��ţ��ݸ�����ѧ�����Ͱ��ţ�
        TkStudentExample tkStudentExample = new TkStudentExample();
        TkStudentExample.Criteria criteria = tkStudentExample.createCriteria();
        criteria.andPassportIdIsNotNull();
        List<TkStudent> studentList = tkStudentMapper.selectByExample(tkStudentExample);

        TkTestschedule tkTestschedule=new TkTestschedule();
        tkTestschedule.setTestpaperId(testInfo.getTestpaperId());

        tkTestschedule.setTestStarttime(DateFormat.StringToDate(testInfo.getTestStarttime(),"yyyy-MM-dd HH:mm:ss"));
        tkTestschedule.setTestEndtime(DateFormat.StringToDate(testInfo.getTestEndtime(),"yyyy-MM-dd HH:mm:ss"));
        tkTestschedule.setCourseId(testInfo.getCourseId());
        tkTestschedule.setCreatePpassportId(testInfo.getCreatePpassportId());
        tkTestschedule.setCreateTime(new Date());

        for (TkStudent tkStudent : studentList) {
            tkTestschedule.setPpassportId(tkStudent.getPassportId());
            tkTestscheduleMapper.insertSelective(tkTestschedule);
        }
        return studentList.size();    //���ر���Ӱ��ŵ�ѧ������
    }

    @Override
    public Integer delExamSch(Long ppassportId, Long testscheduleId) {  //��ʦɾ�����԰��ţ����ݰ��ű�Ŷ�Ӧ�İ���ʱ�䣩
        TkTestscheduleExample tkTestscheduleExample=new TkTestscheduleExample();
        TkTestscheduleExample.Criteria criteria=tkTestscheduleExample.createCriteria();
        criteria.andTestscheduleIdEqualTo(testscheduleId);
        Date createTime= (tkTestscheduleMapper.selectByExample(tkTestscheduleExample)).get(0).getCreateTime();

        TkTestscheduleExample tkTestscheduleExample1=new TkTestscheduleExample();
        TkTestscheduleExample.Criteria criteria1=tkTestscheduleExample1.createCriteria();
        criteria1.andCreateTimeEqualTo(createTime);
        return (tkTestscheduleMapper.deleteByExample(tkTestscheduleExample1))-1;    //������Ӱ���ѧ����
    }

    @Override
    public Integer editExamSch(Long ppassportId, TestInfo testInfo) {//��ʦ�޸Ŀ��԰��ţ����ݰ��ű�Ŷ�Ӧ�İ���ʱ�䣩
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
        return schIds.size();    //���ر��޸İ��ŵ�ѧ������
    }


}
