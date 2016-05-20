package sei.tk.service.subject;

import sei.tk.util.Page;
import sei.tk.service.dao.model.TkSubject;
import sei.tk.service.dao.model.TkSubjectWithBLOBs;
import sei.tk.service.dao.model.vo.subject.SubjectInfo;

/**
 * Created by liuruijie on 2016/3/18.
 */
public interface SubjectServie {
    void addSubject(TkSubjectWithBLOBs tkSubject,String[] choseOptions,String[] blankAnswers);
    Page getSubjects(TkSubject tkSubject,Integer page,Integer rows);//分页多条件查询题目信息
    void updateSubject(TkSubjectWithBLOBs tkSubject);//修改题目信息
    TkSubjectWithBLOBs getSubjectById(Long subjectId);//获取某道题
    void delectSubject(Long subjectId);//删除某道题
    void addSubject(SubjectInfo subjectInfo);//添加一道题
    SubjectInfo getSubjectInfoById(Long subjectId);//获取某道题的具体信息
    void updateSubject(SubjectInfo subjectInfo);//修改某道题的具体信息
    Page getSubjects(SubjectInfo subjectInfo,Integer page,Integer rows);//分页多条件查询题目具体信息
    void deleteSubjectBatch(Long[] subjectIds);//批量删除
}
