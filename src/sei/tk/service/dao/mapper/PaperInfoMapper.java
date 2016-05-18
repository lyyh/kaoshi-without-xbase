package sei.tk.service.dao.mapper;

import sei.tk.service.dao.model.TkSubject;
import sei.tk.service.dao.model.TkSubjectWithBLOBs;
import sei.tk.service.dao.model.vo.paper.PaperInfo;


import java.util.List;

/**
 * Created by ywl on 2016/4/15.
 * �Ծ����
 */
public interface PaperInfoMapper {

    List<PaperInfo> selectAllPaper(PaperInfo paperInfo);  //
    PaperInfo selectById(Long testpaperId);  //
    Integer countPaperInfo(PaperInfo paperInfo);
    List<TkSubjectWithBLOBs> selectSubjects(Long testpaperId); //这里做了更改

}
