package sei.tk.service.study;

import sei.tk.service.dao.model.TkSubject;
import sei.tk.service.dao.model.vo.study.StudyAnswerVo;

import java.util.List;
import java.util.Map;

/**
 * 检查答案是否正确
 * Created by Administrator on 2016/3/18 0018.
 */
public interface StudyAnswerService {
    //比较单个题目的答案
    public TkSubject compareAnswer(String answer, Long subjectId);
    //批量比较题目的答案
    public List<Map<String,String>> batchCompareAnswer(Map<Long, StudyAnswerVo> map);
}
