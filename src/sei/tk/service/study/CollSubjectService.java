package sei.tk.service.study;

import sei.tk.service.dao.model.vo.study.SubjectInfoVo;
import java.util.List;
import java.util.Set;

/**
 * 收藏题目的接口类
 * Created by liuyanhao on 2016/3/23 0023.
 */
public interface CollSubjectService {
    //将错误答案，学生Id，题目Id存入数据库
    public String collSubjects(String errorAnw,Long stuId,Long subId);
    //根据科目显示学生所有收藏的题目（针对章节、知识点、类型）
    public List<SubjectInfoVo> initCollSubject(Short courseId,List<Long> subIds);
    //返回所有收藏的课程id
    public Set<Short> getCollCourId(Long stuId,List<Long> subIds);
    //单个删除收藏的题目
    public String delCollSubject(Long subId);
    //批量删除收藏的题目
    public String batchDelCollSubject(List<Long> subjectId);
    //得到学生收藏的所有题目
    public List<Long> getCollSubId(Long stuId);
}
