package sei.tk.service.study.imp;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkTestpaperMapper;
import sei.tk.service.dao.mapper.TkTestscheduleMapper;
import sei.tk.service.dao.model.TkTestpaper;
import sei.tk.service.dao.model.TkTestschedule;
import sei.tk.service.dao.model.TkTestscheduleExample;
import sei.tk.service.study.SimExamService;
import javax.annotation.Resource;
import java.util.List;

/**
 * 模拟考试实现类
 * Created by liuyanhao on 2016/3/25 0025.
 */
@Service
public class SimExamServiceImpl implements SimExamService{
    @Resource
    TkTestscheduleMapper tkTestscheduleMapper;
    @Resource
    TkTestpaperMapper tkTestpaperMapper;

    /**
     * 通过学生id得到testpaperId
     * @param ppassportId
     * @return
     */
    @Override
    public Long getTestpaperId(Long ppassportId) {
        TkTestscheduleExample tkTestscheduleExample = new TkTestscheduleExample();
        TkTestscheduleExample.Criteria criteria = tkTestscheduleExample.createCriteria();
        criteria.andPpassportIdEqualTo(ppassportId);
        //根据学生id找到考试计划
        List<TkTestschedule> list = tkTestscheduleMapper.selectByExample(tkTestscheduleExample);
        //根据考试计划找到考卷类型（模拟还是正式）
        for(TkTestschedule tkTestschedule:list){
            TkTestpaper tkTestpaper = tkTestpaperMapper.selectByPrimaryKey(tkTestschedule.getTestpaperId());
            if(tkTestpaper.getTestpaperType()){
                //返回模拟试卷
                return tkTestschedule.getTestpaperId();
            }
        }
        return null;
    }
}
