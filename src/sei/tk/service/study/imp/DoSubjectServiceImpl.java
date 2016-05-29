package sei.tk.service.study.imp;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkQuecollMapper;
import sei.tk.service.dao.mapper.TkSubjectMapper;
import sei.tk.service.dao.model.*;
import sei.tk.service.study.DoSubjectService;
import sei.tk.util.Page;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
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
        criteria.andQuetypeIdNotEqualTo((short) 4);
        tkSubjectExample.setRow(rows);
        tkSubjectExample.setStart((page-1)*rows);
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
    public  void collectSub(List<TkSubject> tkSubjects,HttpSession session){
        SessionPassport sessionPassport= (SessionPassport) session.getAttribute("sessionPassport");

        for(TkSubject tkSubject:tkSubjects){
            TkQuecoll tkQuecoll=new TkQuecoll();
            tkQuecoll.setStuId(sessionPassport.getPassportId());
            tkQuecoll.setSubjectId(tkSubject.getSubjectId());
            tkQuecoll.setQuecollAnswer(tkSubject.getSubjectAnswer());
            tkQuecollMapper.insertSelective(tkQuecoll);
        }

//        List<Long> subjectids=new ArrayList<Long>();
//        HashMap<Long,String> answer=new HashMap<Long,String>();
//        for(TkSubject tkSubject:tkSubjects){
//            subjectids.add(tkSubject.getSubjectId());
//            answer.put(tkSubject.getSubjectId(), tkSubject.getSubjectAnswer());
//        }
//        TkSubjectExample tkSubjectExample=new TkSubjectExample();
//        TkSubjectExample.Criteria criteria=tkSubjectExample.createCriteria();
//        criteria.andSubjectIdIn(subjectids);
//        List<TkSubject> tkSubjects1=tkSubjectMapper.selectByExample(tkSubjectExample);
//        for(int i=0;i<tkSubjects1.size();i++){
//            if(answer.get(tkSubjects1.get(i).getSubjectId())!=tkSubjects1.get(i).getSubjectAnswer()) {
//                TkQuecoll tkQuecoll = new TkQuecoll();
//                tkQuecoll.setStuId(sessionPassport.getPassportId());
//                tkQuecoll.setQuecollId(tkSubjects1.get(i).getSubjectId());
//                tkQuecoll.setQuecollAnswer(answer.get(tkSubjects1.get(i).getSubjectId()));
//                tkQuecollMapper.insertSelective(tkQuecoll);
//            }
//        }
    }
}
