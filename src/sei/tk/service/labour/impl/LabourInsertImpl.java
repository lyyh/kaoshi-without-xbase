package sei.tk.service.labour.impl;
/**
 * 人工制卷插入接口实现
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
import sei.tk.service.labour.LabourInsert;

import javax.annotation.Resource;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/3/15 0015.
 */
@Service
public class LabourInsertImpl implements LabourInsert{
    @Resource
    TkMkpaperMapper tkMkpaperMapper;

    @Resource
    TkTestpaperMapper tkTestpaperMapper;

    @Resource
    TkTestsubjectMapper tkTestsubjectMapper;

    /**
     * 根据testpaperId插入题目
     * @param array
     * @param lateTestpaperId
     * @return
     */
    @Override
    public boolean insertTestSubject(JSONArray array,Long lateTestpaperId) {
        if (lateTestpaperId != null && array != null) {
            Iterator<Object> iterator = array.iterator();
            while (iterator.hasNext()) {
                JSONObject ob = (JSONObject) iterator.next();
                TkTestsubject tkTestsubject = new TkTestsubject();
                tkTestsubject.setSubjectId(ob.getLong("subjectId"));
                tkTestsubject.setTestpaperScore(ob.getShort("scoreForEach"));
                tkTestsubject.setTestpaperId(lateTestpaperId);
                if(tkTestsubjectMapper.insert(tkTestsubject)==0){
                    return false;
                }
                tkTestsubjectMapper.insert(tkTestsubject);
            }
            return true;
        }
        return false;
            }

    /**
     * 从json对象中找出TkMkpaper的属性并存入数据库
     * @param tkMkpaper
     * @return
     */
    @Override
    public Long insertTkMkpaper(TkMkpaper tkMkpaper) {
//        if(jsonObject==null){
//            return null;
//        }
//        TkMkpaper tkMkpaper = new TkMkpaper();
//        tkMkpaper.setCourseId(jsonObject.getShort("courseId"));
//        tkMkpaper.setMkpaperTerm(jsonObject.getString("mkpaperTerm"));
//        tkMkpaper.setMkpaperExtime(jsonObject.getShort("mkpaperExtime"));
//        tkMkpaper.setMkpaperScore(jsonObject.getShort("mkpaperScore"));
//        tkMkpaper.setMkpaperType(jsonObject.getBoolean("mkpaperType"));
//        tkMkpaper.setPpassportId(jsonObject.getLong("ppassportId"));
        if(tkMkpaperMapper.insertSelective(tkMkpaper)!=0) {
            return getLateMkpaperId();
        }else {
            return null;
        }
    }

    /**
     * 从json字符串中找出TkTestpaper的属性并存入数据库
     * @param tkMkpaper
     * @param lateMkpaperId
     * @return
     */
    @Override
    public Long insertTkTestpaper(TkMkpaper tkMkpaper,Long lateMkpaperId,boolean testType) {
        if(lateMkpaperId!=null&&tkMkpaper!=null){
            TkTestpaper tkTestpaper = new TkTestpaper();
            tkTestpaper.setTestpaperType(testType);
            tkTestpaper.setMkpaperId(lateMkpaperId);
            tkTestpaper.setPpassportId(tkMkpaper.getPpassportId());
            if(tkTestpaperMapper.insertSelective(tkTestpaper)!=0){
                return getLateTestpaperId();
            }
            return null;
        }
        return null;
    }

    /**
     * 找出tk_mkpaper中最近插入的mkpaperId
     * @return
     */
    @Override
    public Long getLateMkpaperId() {
        Long mkpaperId = null;
        if((mkpaperId = tkMkpaperMapper.selectMaxMkpaperId())!=null){
            return mkpaperId;
        }
        return null;
    }

    /**
     * 找出tk_testpaper中最近插入的testpaperId
     * @return
     */
    @Override
    public Long getLateTestpaperId() {
        Long testpaperId = null;
        if((testpaperId = tkTestpaperMapper.selectMaxTestpaperId())!=null){
            return testpaperId;
        }
        return null;
    }
}
