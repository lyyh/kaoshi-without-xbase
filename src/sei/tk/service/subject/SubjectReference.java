package sei.tk.service.subject;

import sei.tk.service.dao.model.TkCourse;
import sei.tk.service.dao.model.TkKnopoint;
import sei.tk.service.dao.model.TkQuetype;

import java.util.List;

/**
 * Created by liuruijie on 2016/3/18.
 */
public interface SubjectReference {
    List<TkCourse> getAllCourse();  //得到所有课程
    List<TkKnopoint> getKnopoint(Short courseId, Byte chapterId);   //根据课程号得到所有知识点
    List<TkQuetype> getAllType();   //得到所有题型
    List<Integer> getChapterId(Short courseId);   //得到所有章节

    void addCourse(TkCourse tkCourse);   //添加课程
    void addKnopoint(TkKnopoint tkKnopoint); //添加知识点
}
