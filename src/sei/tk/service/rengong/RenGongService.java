package sei.tk.service.rengong;

import sei.tk.service.dao.model.TkCourse;
import sei.tk.service.dao.model.TkKnopoint;
import sei.tk.service.dao.model.TkSubject;
import sei.tk.service.dao.model.TkSubjectWithBLOBs;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/3/12.
 */
public interface RenGongService {

    public List<TkSubjectWithBLOBs> getByKpoint(Short courseId, Byte chapterId, Long knopointId, Byte levelId, Short quetypeId);

  public  List<TkCourse>  getCourseList();

    public  List<TkKnopoint> getKnopointList(Short courseId);
    public TkCourse getTkCourse(String courseName);

}
