package sei.tk.service.study;

import sei.tk.service.dao.model.TkStudypage;
import sei.tk.service.dao.model.TkSubject;
import sei.tk.service.dao.model.vo.study.SubjectInfoVo;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 学生章节练习接口类
 * Created by liuyanhao on 2016/3/21 0021.
 */
public interface ChapterStudyService {
    //根据章节id得到题型的数目
    public Map<String,Short> getSubTypeNum(Byte chapterId,Short courseId);
    //根据课程id，章节id，题型数量得到章节题目
    public List<TkSubject> initSubject(HttpSession session, Short courseId, Byte chapter, Integer selectNum, Integer blankNum,
                                       Integer checkNum,
                                       Integer explainNum);
    public List<TkSubject> initSubject(Short courseId,Byte chapter);
    //根据题目类型对题目进行排序
    public List<SubjectInfoVo> sortSubjet(List<TkSubject> list);
    //得到一道题
    public SubjectInfoVo getSubject(List<SubjectInfoVo> list, int subjectNum);
    //比对每一道题的答案(返回正确答案和解析）
    public Map<String,String> subjecRes(String stuAnswer,Long subId);
    //得到章节题目数量
    public int getSubNum(List<SubjectInfoVo> subjectInfoVos);
    //录入章节题型规范
    public boolean creStudyContent(TkStudypage tkStudypage);
    //删除章节题型规范
    public boolean delStudyContent(Long studypageId);
    //修改章节题型规范
    public boolean updStudyContent(TkStudypage tkStudypage);
    //显示章节题型规范
    public boolean sekStudyContent(Short courseId);
    //将题目转化成可以显示的题目
    public SubjectInfoVo getSubInfoVo(TkSubject tkSubject);
}
