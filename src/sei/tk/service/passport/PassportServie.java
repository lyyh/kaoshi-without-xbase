package sei.tk.service.passport;

import sei.tk.service.dao.model.TkStudent;
import sei.tk.service.dao.model.TkTeacher;

/**
 * Created by liuruijie on 2016/3/25.
 */
public interface PassportServie {
    void addStudent(TkStudent tkStudent);
    void addTeacher(TkTeacher tkTeacher);
    void modifyPasswordStudent(Long passportId,String oldpsw,String newpsw);
    void modifyPasswordTeacher(Long passportId,String oldpsw,String newpsw);
    void deleteStudent(Long passportId);
    void deleteTeacher(Long passportId);
    boolean isTeacherCode(String passportCode);
    boolean isStudentCode(String passportCode);
}
