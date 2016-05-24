package sei.tk.service.passportO;

import sei.tk.service.dao.model.vo.passport.StudentInfoVo;
import sei.tk.service.dao.model.vo.passport.TeacherInfoVo;

/**
 * Created by liuruijie on 2016/3/25.
 */
public interface UserInfoService {
    TeacherInfoVo getInfoTeacher(Long passportId);
    StudentInfoVo getInfoStudent(Long passportId);
    void updateTeacher(TeacherInfoVo teacherInfoVo);
    void updateStudent(StudentInfoVo studentInfoVo);
}
