package sei.tk.service.paper;

import sei.system.common.Page;

import sei.tk.service.dao.model.TkMkpaper;
import sei.tk.service.dao.model.TkTestpaper;
import sei.tk.service.dao.model.TkTestsubject;
import sei.tk.service.dao.model.vo.paper.PaperInfo;



/**
 * Created by ywl on 2016/4/10.
 */
public interface PaperService {

      Page selectAllpaper(PaperInfo paperInfo,Integer page, Integer rows); //得到试卷的列表
      PaperInfo selectpaperinfobyId(Long  testpaperId) ;// 得到试卷的具体信息
      int deletePaperBatch(Long[] testpaperIds);  //批量删除试卷
      boolean  updatepaper(TkMkpaper tkMkpaper,TkTestsubject tkTestsubject,TkTestpaper tkTestpaper); //修改试卷
      boolean deletePaper(long testpaperIds); //删除试卷
}


