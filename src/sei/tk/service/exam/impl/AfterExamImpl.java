package sei.tk.service.exam.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.ExamMapper;
import sei.tk.service.exam.AfterExamService;
import sei.tk.service.exam.vo.MyExam;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuruijie on 2016/6/2.
 */
@Service
public class AfterExamImpl implements AfterExamService{

    @Resource
    ExamMapper examMapper;

    @Override
    public List<MyExam> listMyExam(Long passportId) {
        return examMapper.getMyExam(passportId);
    }
}
