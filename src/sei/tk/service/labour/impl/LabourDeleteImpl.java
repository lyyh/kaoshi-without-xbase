package sei.tk.service.labour.impl;
/**
 * 人工制卷删除接口实现
 */
import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkMkpaperMapper;
import sei.tk.service.dao.mapper.TkTestpaperMapper;
import sei.tk.service.dao.mapper.TkTestsubjectMapper;
import sei.tk.service.dao.model.TkTestsubjectExample;
import sei.tk.service.labour.LabourDelete;

import javax.annotation.Resource;

/**
 *
 * Created by Administrator on 2016/3/15 0015.
 */
@Service
public class LabourDeleteImpl implements LabourDelete{
    @Resource
    TkTestpaperMapper tkTestpaperMapper;

    @Resource
    TkTestsubjectMapper tkTestsubjectMapper;

    @Resource
    TkMkpaperMapper tkMkpaperMapper;
    /**
     * 根据mkpaperId删除mkpaper
     * @param mkpaperId
     * @return
     */
    @Override
    public boolean deleteMkpaper(Long mkpaperId) {

        if(tkMkpaperMapper.deleteByPrimaryKey(mkpaperId)!=0){
            return true;
        }
        return false;
    }

    /**
     * 根据testpaperId删除testpaper
     * @param testpaperId
     * @return
     */
    @Override
    public boolean deleteTestpaper(Long testpaperId) {
        if(tkTestpaperMapper.deleteByPrimaryKey(testpaperId)!=0){
            return true;
        }
        return false;
    }

    /**
     * 根据testpaperId删除testsubject
     * @param testpaperId
     * @return
     */
    @Override
    public boolean deleteTestsubject(Long testpaperId) {
        TkTestsubjectExample tkTestsubjectExample = new TkTestsubjectExample();
        TkTestsubjectExample.Criteria criteria= tkTestsubjectExample.createCriteria();
        criteria.andTestpaperIdEqualTo(testpaperId);
        if(tkTestsubjectMapper.deleteByExample(tkTestsubjectExample)!=0){
            return true;
        }
        return false;
    }
}
