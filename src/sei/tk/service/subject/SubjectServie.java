package sei.tk.service.subject;

import sei.tk.service.dao.model.TkSubjectWithBLOBs;
import sei.tk.service.dao.model.vo.subject.SubjectInfo;
import sei.tk.util.Page;

/**
 * Created by liuruijie on 2016/3/18.
 */
public interface SubjectServie {
    void addSubject(SubjectInfo subjectInfo);//添加一道题
    SubjectInfo getSubjectInfoById(Long subjectId);//获取某道题的具体信息
    void updateSubject(SubjectInfo subjectInfo);//修改某道题的具体信息
    Page getSubjects(SubjectInfo subjectInfo, Integer page, Integer rows);//分页多条件查询题目具体信息
    void deleteSubjectBatch(Long[] subjectIds);//批量删除
}
