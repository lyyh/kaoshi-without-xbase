package sei.tk.service.study;

import sei.tk.service.dao.model.TkQuecoll;
import sei.tk.service.dao.model.TkSubject;
import sei.tk.util.Page;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by ywl on 2016/5/24.
 */
public interface DoSubjectService {
    //得到题目数量
     int getSubjectnums(Short courseId,Long knopointId,Short quetypeId);
    //得到题目列表
     Page getSubjects(Short courseId,Long knopointId,Short quetypeId,Integer page, Integer rows);
    //得到答案和解析
     Page getSubjectsAndAnswer(Short courseId,Long knopointId,Short quetypeId,Integer page, Integer rows);
    //收集错题
    void collectSub(List<TkSubject> tkSubjects,HttpSession session );


}
