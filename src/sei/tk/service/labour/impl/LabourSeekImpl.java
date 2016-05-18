package sei.tk.service.labour.impl;
/**
 * 人工制卷查找接口实现
 */
import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkMkpaperMapper;
import sei.tk.service.dao.mapper.TkTestpaperMapper;
import sei.tk.service.dao.mapper.TkTestsubjectMapper;
import sei.tk.service.dao.model.TkMkpaper;
import sei.tk.service.dao.model.TkTestpaper;
import sei.tk.service.dao.model.TkTestsubject;
import sei.tk.service.labour.LabourMkpaper;
import sei.tk.service.labour.LabourSeek;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/15 0015.
 */
@Service
public class LabourSeekImpl implements LabourSeek {
    @Resource
    TkTestpaperMapper tkTestpaperMapper;

    @Resource
    TkTestsubjectMapper tkTestsubjectMapper;

    @Resource
    TkMkpaperMapper tkMkpaperMapper;

    /**
     * 显示所有试卷信息（制卷人，制卷试卷，制卷时间）
     * @return
     */
    @Override
    public List<Map<String,Object>> seekTestpaperId() {
        List<Map<String,Object>> mapList = new ArrayList<>();
        //查询所有试卷id
        List<TkTestpaper> list = tkTestpaperMapper.selectAllTestpaperId();
        for(int i = 0;i<list.size();i++){
            Map<String,Object> map = new HashMap<>();
            Long testpaperId = list.get(i).getTestpaperId();
            TkMkpaper tkMkpaper = seekMkpaper(testpaperId);
            map.put("ppassportId", tkMkpaper.getPpassportId());
            map.put("ppassportTime", tkMkpaper.getPpassportTime());
            map.put("testpaperId", testpaperId);
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 根据testpaperId查找试卷题目和试卷信息
     * @param testpaperId
     * @return
     */
    @Override
    public Map<String,Object> seekTestpaper(Long testpaperId) {
        Map<String,Object> map = new HashMap<>();
        List<Long> list = new ArrayList<>();
        //查询所有题目id
        List<TkTestsubject> tkTestsubjects = tkTestsubjectMapper.selectAllSubjectId(testpaperId);
        for(int i = 0;i<tkTestsubjects.size();i++){
            list.add(tkTestsubjects.get(i).getSubjectId());
        }
        TkMkpaper tkMkpaper = seekMkpaper(testpaperId);
        map.put("subjectIdList",list);
        map.put("tkMkpaper",tkMkpaper);
        return map;
    }

    /**
     * 根据testpaperId返回mkpaper对象
     * @param testpaperId
     * @return
     */
    @Override
    public TkMkpaper seekMkpaper(Long testpaperId) {
        TkTestpaper tkTestpaper = null;
        TkMkpaper tkMkpaper = new TkMkpaper();
        TkMkpaper tk = new TkMkpaper();
        if((tkTestpaper = tkTestpaperMapper.selectByPrimaryKey(testpaperId))!=null){
            if((tk = tkMkpaperMapper.selectByPrimaryKey(tkTestpaper.getMkpaperId()))!=null){
                return tk;
            }
            return null;
        }
        return null;
    }

          /**
          * 根据testpaperId查找mkpaperId
          * @param testpaperId
          * @return
          */
    @Override
    public Long seekMkpaperId(Long testpaperId) {
        return tkTestpaperMapper.selectByPrimaryKey(testpaperId).getMkpaperId();
    }
}
