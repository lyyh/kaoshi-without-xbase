package sei.tk.service.exam.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.ExamMapper;
import sei.tk.service.exam.AfterExamService;
import sei.tk.service.exam.vo.Analyse;
import sei.tk.service.exam.vo.GenExam;
import sei.tk.service.exam.vo.MyExam;
import sei.tk.util.Page;

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

    @Override
    public Page listAllGradsByPage(Integer currentPage, Integer rows, GenExam genExam) {
        if(currentPage==null||rows==null){
            genExam.setStart(null);
        }else{
            genExam.setStart((currentPage-1)*rows);
        }

        genExam.setRows(rows);

        return new Page(examMapper.selectGradeByPage(genExam),examMapper.countGradeByPage(genExam));
    }

    @Override
    public Analyse getAnalyse(Long passportId) {
        return null;
    }

}
