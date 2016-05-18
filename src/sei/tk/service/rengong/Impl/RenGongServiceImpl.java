package sei.tk.service.rengong.Impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkCourseMapper;
import sei.tk.service.dao.mapper.TkKnopointMapper;
import sei.tk.service.dao.mapper.TkQuetypeMapper;
import sei.tk.service.dao.mapper.TkSubjectMapper;
import sei.tk.service.dao.model.*;
import sei.tk.service.rengong.RenGongService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/3/14.
 */
@Service
public class RenGongServiceImpl implements RenGongService {

    @Resource
    TkKnopointMapper tkKnopointMapper;

    @Resource
    TkSubjectMapper tkSubjectMapper;

    @Resource
    TkCourseMapper tkCourseMapper;

    @Resource
    TkQuetypeMapper tkQuetypeMapper;



    @Override
    public List<TkSubjectWithBLOBs> getByKpoint(Short courseId,Byte chapterId ,Long knopointId,Byte levelId,Short quetypeId) {

        List<TkSubjectWithBLOBs> list=tkSubjectMapper.selectByKnpoint(courseId,chapterId,knopointId,levelId,quetypeId);
		   for(TkSubjectWithBLOBs tkSubjectWithBLOBs:list){
                     //替换 成<br>
                  String temp=tkSubjectWithBLOBs.getSubjectOption();
                    temp=temp.replaceAll("@#%","<br>");
                       tkSubjectWithBLOBs.setSubjectOption(temp);
              }
          
		 
        return list;

    }


    @Override
    public List<TkCourse> getCourseList() {
        return tkCourseMapper.selectAllCourse();
    }

    @Override
    public List<TkKnopoint> getKnopointList(Short courseId) {
        return   tkKnopointMapper.selectBycID(courseId);
    }

    @Override
    public TkCourse getTkCourse(String courseName) {
          TkCourseExample tkCourseExample=new TkCourseExample();
          TkCourseExample.Criteria criteria= tkCourseExample.createCriteria();
          criteria.andCourseNameEqualTo(courseName.trim());
        List<TkCourse>  courses= tkCourseMapper.selectByExample(tkCourseExample);
        return courses.get(0);
    }


}
