package sei.tk.service.study.imp;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkSubjectMapper;
import sei.tk.service.dao.model.TkSubject;
import sei.tk.service.dao.model.TkSubjectExample;
import sei.tk.service.dao.model.vo.study.StudyAnswerVo;
import sei.tk.service.study.StudyAnswerService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 检查答案是否正确
 * Created by Administrator on 2016/3/18 0018.
 */
@Service
public class StudyAnswerServiceImpl implements StudyAnswerService {
    @Resource
    TkSubjectMapper tkSubjectMapper;

    /**
     * 比较单个题目的答案，错误则返回带有答案和“题目分析”的对象
     * @param answer
     * @param subjectId
     * @return 返回一个TkSubject，获取其中的正确答案和“题目分析”
     */
    @Override
    public TkSubject compareAnswer(String answer,Long subjectId) {
        TkSubjectExample tkSubjectExample = new TkSubjectExample();
        TkSubjectExample.Criteria criteria = tkSubjectExample.createCriteria();
        criteria.andSubjectIdEqualTo(subjectId);
        TkSubject tkSubject = tkSubjectMapper.selectByPrimaryKey(subjectId);
        //对选择题、判断题、解答题进行对错分析
        if(tkSubject.getQuetypeId()==1||tkSubject.getQuetypeId()==2||tkSubject.getQuetypeId()==3){
            if(answer.trim().equals(tkSubject.getSubjectAnswer())){
                return null;
            }
            return tkSubject;
        }
        return null;
    }


    /**
     * 批量比较题目的答案（待完善）
     * @param map
     * @return
     */
    @Override
    public List<Map<String, String>> batchCompareAnswer(Map<Long, StudyAnswerVo> map) {
        return null;
    }
}
