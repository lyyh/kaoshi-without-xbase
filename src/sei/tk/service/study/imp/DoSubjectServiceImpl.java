package sei.tk.service.study.imp;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkQuecollMapper;
import sei.tk.service.dao.mapper.TkSubjectMapper;
import sei.tk.service.dao.model.TkQuecoll;
import sei.tk.service.dao.model.TkSubjectExample;
import sei.tk.service.dao.model.TkSubjectWithBLOBs;
import sei.tk.service.study.DoSubjectService;
import sei.tk.util.Page;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ywl on 2016/5/24.
 */
@Service
public class DoSubjectServiceImpl implements DoSubjectService {
    @Resource
    TkSubjectMapper tkSubjectMapper;
    @Resource
    TkQuecollMapper tkQuecollMapper;

    @Override
    public int getSubjectnums(Short courseId, Long knopointId, Short quetypeId) {
        TkSubjectExample tkSubjectExample=new TkSubjectExample();
        TkSubjectExample.Criteria criteria=tkSubjectExample.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        if(knopointId!=null)
        criteria.andKnopointIdEqualTo(knopointId);
        if(quetypeId!=null)
        criteria.andQuetypeIdEqualTo(quetypeId);
        return tkSubjectMapper.countByExample(tkSubjectExample);
    }

    @Override
    public Page getSubjects(Short courseId, Long knopointId, Short quetypeId, Integer page, Integer rows) {
        TkSubjectExample tkSubjectExample=new TkSubjectExample();
        TkSubjectExample.Criteria criteria=tkSubjectExample.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        if(knopointId!=null)
        criteria.andKnopointIdEqualTo(knopointId);
        if(quetypeId!=null)
        criteria.andQuetypeIdEqualTo(quetypeId);
        tkSubjectExample.setRow(rows);
        tkSubjectExample.setStart(page);
        List<TkSubjectWithBLOBs> tkSubjectWithBLOBses=tkSubjectMapper.selectSubs(tkSubjectExample);
        return new Page(tkSubjectWithBLOBses,tkSubjectMapper.countByExample(tkSubjectExample));
    }
    @Override
    public Page getSubjectsAndAnswer(Short courseId,Long knopointId,Short quetypeId,Integer page, Integer rows){
        TkSubjectExample tkSubjectExample=new TkSubjectExample();
        TkSubjectExample.Criteria criteria=tkSubjectExample.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        if(knopointId!=null)
            criteria.andKnopointIdEqualTo(knopointId);
        if(quetypeId!=null)
            criteria.andQuetypeIdEqualTo(quetypeId);
        tkSubjectExample.setRow(rows);
        tkSubjectExample.setStart(page);
        List<TkSubjectWithBLOBs> tkSubjectWithBLOBses=tkSubjectMapper.selectByExampleWithBLOBs(tkSubjectExample);
        return new Page(tkSubjectWithBLOBses,tkSubjectMapper.countByExample(tkSubjectExample));
    }
    @Override
    public  void collectSub(List<TkQuecoll> tkQuecolls){
        for(TkQuecoll tkQuecoll: tkQuecolls){
            tkQuecollMapper.insertSelective(tkQuecoll);
        }
    }
}
