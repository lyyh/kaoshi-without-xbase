package sei.tk.service.labour;

import sei.tk.service.dao.model.TkMkpaper;

import java.util.List;
import java.util.Map;

/**
 * 查找人工制卷信息
 * Created by Administrator on 2016/3/15 0015.
 */
public interface LabourSeek {
    //显示所有试卷信息（制卷人，制卷试卷，制卷时间）
    public List<Map<String,Object>> seekTestpaperId();
    //根据testpaperId查找显示试卷信息、题目信息
    public Map<String,Object> seekTestpaper(Long testpaperId);
    //根据testpaperId查找mkpaper
    public TkMkpaper seekMkpaper(Long testpaperId);
    //根据testpaperId查找mkpaperId
    public Long seekMkpaperId(Long testpaperId);
}
