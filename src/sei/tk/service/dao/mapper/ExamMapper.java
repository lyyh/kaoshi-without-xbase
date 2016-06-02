package sei.tk.service.dao.mapper;

import sei.tk.service.exam.vo.MyExam;

import java.util.List;

/**
 * Created by liuruijie on 2016/6/2.
 */
public interface ExamMapper {
    List<MyExam> getMyExam(Long passportId);
}
