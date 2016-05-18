package sei.tk.service.study.imp;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkCourseMapper;
import sei.tk.service.dao.mapper.TkKnopointMapper;
import sei.tk.service.dao.mapper.TkSubjectMapper;
import sei.tk.service.dao.model.*;
import sei.tk.service.study.SeekStudyService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *查找题目的实现类
 * Created by Administrator on 2016/3/19 0019.
 */
@Service
public class SeekStudyServiceImpl implements SeekStudyService {
    @Resource
    TkCourseMapper tkCourseMapper;
    @Resource
    TkKnopointMapper tkKnopointMapper;
    @Resource
    TkSubjectMapper tkSubjectMapper;
    /**
     * 得到所有课程
     * @return
     */
    @Override
    public List<TkCourse> seekCourseId() {
        return tkCourseMapper.selectAllCourse();
    }


    /**
     * 根据章节来查找知识点
     * @param courseId
     * @return
     */
    @Override
    public List<Byte> seekKnopoint(Short courseId) {
        return null;
    }

    /**
     * 根据题目Id来找到Tksubject
     * @param subjectId
     * @return
     */
    @Override
    public TkSubject seekSubjectBySu(Long subjectId) {
        return tkSubjectMapper.selectByPrimaryKey(subjectId);
    }

    /**
     * 根据课程编号得到章节id
     * @param courseId
     * @return
     */
    @Override
    public List<Byte> seekChapter(Short courseId) {
        TkSubjectExample tkSubjectExample = new TkSubjectExample();
        TkSubjectExample.Criteria criteria = tkSubjectExample.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        //根据courseId找出所有符合条件的对象
        List<TkSubject> tkSubjects = tkSubjectMapper.selectByExample(tkSubjectExample);
        List<Byte> list = new ArrayList<>();
        //将tkKonpoints中的chapterId放入新的list中
        for(TkSubject tkSubject:tkSubjects){
            list.add(tkSubject.getChapterId());
        }
        return list;
    }

    /**
     * 根据章节Id得到所有题目
     * @param chapterId
     * @return
     */
    @Override
    public List<TkSubject> seekSubjectByCh(Byte chapterId) {
        TkSubjectExample tkSubjectExample = new TkSubjectExample();
        TkSubjectExample.Criteria criteria = tkSubjectExample.createCriteria();
        criteria.andChapterIdEqualTo(chapterId);
        List<TkSubject> list = tkSubjectMapper.selectByExample(tkSubjectExample);
        for(TkSubject tkSubject:list){
            tkSubject.setSubjectAnswer(null);
        }
        return list;
    }
}
