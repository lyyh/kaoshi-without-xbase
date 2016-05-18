package sei.tk.service.labour.impl;
/**
 * 人工制卷更新接口实现
 */
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkMkpaperMapper;
import sei.tk.service.dao.mapper.TkTestpaperMapper;
import sei.tk.service.dao.mapper.TkTestsubjectMapper;
import sei.tk.service.dao.model.TkMkpaper;
import sei.tk.service.dao.model.TkTestpaper;
import sei.tk.service.dao.model.TkTestsubject;
import sei.tk.service.dao.model.TkTestsubjectExample;
import sei.tk.service.labour.LabourUpdate;

import javax.annotation.Resource;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/3/15 0015.
 */
@Service
public class LabourUpdateImpl implements LabourUpdate {
    @Resource
    TkMkpaperMapper tkMkpaperMapper;

    @Resource
    TkTestpaperMapper tkTestpaperMapper;

    @Resource
    TkTestsubjectMapper tkTestsubjectMapper;
    /**
     * 根据mkpaperId修改mkpaper
     * @param jsonObject
     * @param mkpaperId
     * @return
     */
    @Override
    public boolean updateMkpaper(JSONObject jsonObject,Long mkpaperId) {
        TkMkpaper tkMkpaper = new TkMkpaper();
        tkMkpaper.setMkpaperId(mkpaperId);
        tkMkpaper.setCourseId(jsonObject.getShort("courseId"));
        tkMkpaper.setMkpaperTerm(jsonObject.getString("mkpaperTerm"));
        tkMkpaper.setMkpaperExtime(jsonObject.getShort("mkpaperExtime"));
        tkMkpaper.setMkpaperScore(jsonObject.getShort("mkpaperScore"));
        tkMkpaper.setMkpaperType(jsonObject.getBoolean("mkpaperType"));
        tkMkpaper.setPpassportId(jsonObject.getLong("ppassportId"));
        if((tkMkpaperMapper.updateByPrimaryKey(tkMkpaper))!=0){
            return true;
        }
        return false;
    }

    /**
     * 根据testpaperId修改testpaper
     * @param testpaperId
     * @param jsonObject
     * @return 返回mkpaperId
     */
    @Override
    public Long updateTestpaper(Long testpaperId,JSONObject jsonObject) {
        if(jsonObject==null){
            return null;
        }
        TkTestpaper tkTestpaper = null;
        if((tkTestpaper = tkTestpaperMapper.selectByPrimaryKey(testpaperId))!=null){
            TkTestpaper tkTestpaper1 = new TkTestpaper();
            tkTestpaper1.setPpassportId(jsonObject.getLong("ppassportId"));
            tkTestpaper1.setTestpaperId(testpaperId);
            if(tkTestpaperMapper.updateByPrimaryKeySelective(tkTestpaper1)!=0){
                return tkTestpaper.getMkpaperId();
            }
        }
        return null;
    }

    /**
     * 根据testpaperId修改testsubject
     * @param testpaperId
     * @param array
     * @return
     */
    @Override
    public boolean updateTestsubject(Long testpaperId,JSONArray array) {
        if(array==null){
            return false;
        }
        TkTestsubjectExample tkTestsubjectExample = new TkTestsubjectExample();
        TkTestsubjectExample.Criteria criteria = tkTestsubjectExample.createCriteria();
        criteria.andTestpaperIdEqualTo(testpaperId);
        if(tkTestsubjectMapper.deleteByExample(tkTestsubjectExample)==0){
            return false;
        }
        Iterator<Object> iterator = array.iterator();
        while (iterator.hasNext()) {
            JSONObject ob = (JSONObject) iterator.next();
            TkTestsubject tkTestsubject = new TkTestsubject();
            tkTestsubject.setSubjectId(ob.getLong("questionId"));
            tkTestsubject.setTestpaperScore(ob.getShort("score"));
            tkTestsubject.setTestpaperId(testpaperId);
            if(tkTestsubjectMapper.insert(tkTestsubject)==0){
                return false;
            }
        }
        return true;
    }
}
