package sei.tk.service.subject.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkCourseMapper;
import sei.tk.service.dao.mapper.TkKnopointMapper;
import sei.tk.service.dao.mapper.TkQuetypeMapper;
import sei.tk.service.dao.mapper.TkSubjectMapper;
import sei.tk.service.dao.model.*;
import sei.tk.service.subject.SubjectReference;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuruijie on 2016/3/18.
 */
@Service
public class SubjectReferenceImpl implements SubjectReference {
    @Resource
    private TkCourseMapper tkCourseMapper;
    @Resource
    private TkKnopointMapper tkKnopointMapper;
    @Resource
    private TkQuetypeMapper tkQuetypeMapper;
    @Resource
    private TkSubjectMapper tkSubjectMapper;

    @Override
    public List<TkCourse> getAllCourse() {  //得到所有课程
        List<TkCourse> tkCourseList = tkCourseMapper.selectByExample(new TkCourseExample());
        return tkCourseList;
    }

    @Override
    public List<TkKnopoint> getKnopoint(Short courseId,Byte chapterId) {   //根据课程号得到所有知识点
        if(courseId==null||chapterId==null)
            throw new RuntimeException();
        TkKnopointExample tkKnopointExample = new TkKnopointExample();
        TkKnopointExample.Criteria criteria = tkKnopointExample.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        criteria.andChapterIdEqualTo(chapterId);
        List<TkKnopoint> tkKnopointList = tkKnopointMapper.selectByExample(tkKnopointExample);
        return tkKnopointList;
    }

    @Override
    public List<TkQuetype> getAllType() {   //得到所有题型
        List<TkQuetype> tkQuetypeList = tkQuetypeMapper.selectByExample(new TkQuetypeExample());
        return tkQuetypeList;
    }

    @Override
    public List<Integer> getChapterId(Short courseId) {   //得到所有章节
        if(courseId==null)throw new RuntimeException();
        return tkSubjectMapper.selectChapterGroup(courseId);
    }

    @Override
    public void addCourse(TkCourse tkCourse) {   //添加课程
        if(tkCourse==null||tkCourse.getCourseName()==null||tkCourse.getCourseName().matches("\\s+"))
            throw new RuntimeException();
        tkCourseMapper.insertSelective(tkCourse);
    }

    @Override
    public void addKnopoint(TkKnopoint tkKnopoint) { //添加知识点
        if(tkKnopoint==null||tkKnopoint.getKnopointId()==null||tkKnopoint.getKnopointName()==null||tkKnopoint.getKnopointName().matches("\\s+"))
            throw new RuntimeException();
        tkKnopointMapper.insertSelective(tkKnopoint);
    }
}
