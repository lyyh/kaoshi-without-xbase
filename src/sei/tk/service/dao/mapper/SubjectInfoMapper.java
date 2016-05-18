package sei.tk.service.dao.mapper;

import sei.tk.service.dao.model.vo.subject.SubjectInfo;

import java.util.List;

/**
 * Created by liuruijie on 2016/4/10.
 */
public interface SubjectInfoMapper {
    SubjectInfo selectSubjectInfoById(Long subjectId);
    List<SubjectInfo> selectSubjectInfosByPage(SubjectInfo subjectInfo);
    Integer countSubjectInfo(SubjectInfo subjectInfo);
}
