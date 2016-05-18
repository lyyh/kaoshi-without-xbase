package sei.tk.service.study;

import sei.tk.service.dao.model.TkCourse;
import sei.tk.service.dao.model.TkSubject;

import java.util.List;

/**
 * 查找题目的接口类
 * Created by Administrator on 2016/3/18 0018.
 */
public interface SeekStudyService {
    //查找所有课程编号及课程名称
    public List<TkCourse> seekCourseId();
    //根据课程编号查找所有知识点
    public List<Byte> seekKnopoint(Short courseId);
    //根据课程编号来查找所有章节
    public List<Byte> seekChapter(Short courseId);
    //根据章节来找题目
    public List<TkSubject> seekSubjectByCh(Byte chapterId);
    //根据题目id找到Tksubject
    public TkSubject seekSubjectBySu(Long subjectId);
}
