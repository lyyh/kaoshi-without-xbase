package sei.tk.service.labour.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkMkpaperMapper;
import sei.tk.service.dao.mapper.TkTestpaperMapper;
import sei.tk.service.dao.mapper.TkTestsubjectMapper;
import sei.tk.service.dao.model.*;
import sei.tk.service.labour.LabourMkpaper;
import javax.annotation.Resource;
import java.util.*;

/**
 * Created by liuyanhao on 2016/3/12 0012.
 */
//@Service
//public class LabourMkpaperImpl implements LabourMkpaper {
//
//    @Resource
//    TkMkpaperMapper tkMkpaperMapper;
//
//    @Resource
//    TkTestpaperMapper tkTestpaperMapper;
//
//    @Resource
//    TkTestsubjectMapper tkTestsubjectMapper;
//

//    /**
//     * 显示所有试卷信息（出卷人，出卷试卷，考试科目）
//     * @return
//     */
//    @Override
//    public List<Map<String,Object>> seekTestpaperId() {
//        List<Map<String,Object>> mapList = new ArrayList<>();
//        List<TkTestpaper> list = tkTestpaperMapper.selectAllTestpaperId();
//        for(int i = 0;i<list.size();i++){
//            Map<String,Object> map = new HashMap<>();
//            Long testpaperId = list.get(i).getTestpaperId();
//            TkMkpaper tkMkpaper = seekMkpaper(testpaperId);
//                map.put("ppassportId", tkMkpaper.getPpassportId());
//                map.put("ppassportTime", tkMkpaper.getPpassportTime());
//                map.put("testpaperId", testpaperId);
//            mapList.add(map);
//        }
//        return mapList;
//    }
//
//    /**
//     * 根据testpaperId查找试卷题目和试卷信息
//     * @param testpaperId
//     * @return
//     */
//    @Override
//    public Map<String,Object> seekTestpaper(Long testpaperId) {
//        Map<String,Object> map = new HashMap<>();
//        List<Long> list = new ArrayList<>();
//        List<TkTestsubject> tkTestsubjects = tkTestsubjectMapper.selectAllSubjectId(testpaperId);
//        for(int i = 0;i<tkTestsubjects.size();i++){
//            list.add(tkTestsubjects.get(i).getSubjectId());
//        }
//        TkMkpaper tkMkpaper = seekMkpaper(testpaperId);
//        map.put("subjectIdList",list);
//        map.put("tkMkpaper",tkMkpaper);
//        return map;
//    }
//
//    /**
//     * 根据testpaperId返回mkpaper对象
//     * @param testpaperId
//     * @return
//     */
//    @Override
//    public TkMkpaper seekMkpaper(Long testpaperId) {
//        TkTestpaper tkTestpaper = null;
//        TkMkpaper tkMkpaper = new TkMkpaper();
//        TkMkpaper tk = new TkMkpaper();
//        if((tkTestpaper = tkTestpaperMapper.selectByPrimaryKey(testpaperId))!=null){
//            if((tk = tkMkpaperMapper.selectByPrimaryKey(tkTestpaper.getMkpaperId()))!=null){
//                return tk;
//            }
//            return null;
//        }
//        return null;
//    }

//    /**
//     * 根据testpaperId查找mkpaperId
//     * @param testpaperId
//     * @return
//     */
//    @Override
//    public Long seekMkpaperId(Long testpaperId) {
//        return tkTestpaperMapper.selectByPrimaryKey(testpaperId).getMkpaperId();
//    }
//
//    /**
//     * 根据testpaperId插入题目
//     * @param array
//     * @param lateTestpaperId
//     * @return
//     */
//    @Override
//    public boolean insertTestSubject(JSONArray array,Long lateTestpaperId) {
//        if (lateTestpaperId != null&&array!=null) {
//            Iterator<Object> iterator = array.iterator();
//            while (iterator.hasNext()) {
//                JSONObject ob = (JSONObject) iterator.next();
//                TkTestsubject tkTestsubject = new TkTestsubject();
//                tkTestsubject.setSubjectId(ob.getLong("questionId"));
//                tkTestsubject.setTestpaperScore(ob.getShort("score"));
//                tkTestsubject.setTestpaperId(lateTestpaperId);
//                if(tkTestsubjectMapper.insert(tkTestsubject)==0){
//                    return false;
//                }
//            }
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 从json对象中找出TkMkpaper的属性并存入数据库
//     * @param jsonObject
//     * @return
//     */
//    @Override
//    public Long insertTkMkpaper(JSONObject jsonObject) {
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
//        if(tkMkpaperMapper.insertSelective(tkMkpaper)!=0) {
//            return getLateMkpaperId();
//        }else {
//            return null;
//        }
//    }
//
//    /**
//     * 从json字符串中找出TkTestpaper的属性并存入数据库
//     * @param jsonObject
//     * @param lateMkpaperId
//     * @return
//     */
//    @Override
//    public Long insertTkTestpaper(JSONObject jsonObject,Long lateMkpaperId) {
//        if(lateMkpaperId!=null&&jsonObject!=null){
//            TkTestpaper tkTestpaper = new TkTestpaper();
//            tkTestpaper.setMkpaperId(lateMkpaperId);
//            tkTestpaper.setPpassportId(jsonObject.getLong("ppassportId"));
//            if(tkTestpaperMapper.insertSelective(tkTestpaper)!=0){
//                return getLateTestpaperId();
//            }
//            return null;
//        }
//        return null;
//    }

//    /**
//     * 找出tk_mkpaper中最近插入的mkpaperId
//     * @return
//     */
//    @Override
//    public Long getLateMkpaperId() {
//        Long mkpaperId = null;
//        if((mkpaperId = tkMkpaperMapper.selectMaxMkpaperId())!=null){
//            return mkpaperId;
//        }
//        return null;
//    }

//    /**
//     * 找出tk_testpaper中最近插入的testpaperId
//     * @return
//     */
//    @Override
//    public Long getLateTestpaperId() {
//        Long testpaperId = null;
//        if((testpaperId = tkTestpaperMapper.selectMaxTestpaperId())!=null){
//            return testpaperId;
//        }
//        return null;
//    }
//
//    /**
//     * 根据mkpaperId删除mkpaper
//     * @param mkpaperId
//     * @return
//     */
//    @Override
//    public boolean deleteMkpaper(Long mkpaperId) {
//            if(tkMkpaperMapper.deleteByPrimaryKey(mkpaperId)!=0){
//                return true;
//            }
//            return false;
//    }
//
//    /**
//     * 根据testpaperId删除testpaper
//     * @param testpaperId
//     * @return
//     */
//    @Override
//    public boolean deleteTestpaper(Long testpaperId) {
//        if(tkTestpaperMapper.deleteByPrimaryKey(testpaperId)!=0){
//            return true;
//        }
//        return false;
//    }

//    /**
//     * 根据testpaperId删除testsubject
//     * @param testpaperId
//     * @return
//     */
//    @Override
//    public boolean deleteTestsubject(Long testpaperId) {
//        TkTestsubjectExample tkTestsubjectExample = new TkTestsubjectExample();
//        TkTestsubjectExample.Criteria criteria= tkTestsubjectExample.createCriteria();
//        criteria.andTestpaperIdEqualTo(testpaperId);
//        if(tkTestsubjectMapper.deleteByExample(tkTestsubjectExample)!=0){
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 根据mkpaperId修改mkpaper
//     * @param jsonObject
//     * @param mkpaperId
//     * @return
//     */
//    @Override
//    public boolean updateMkpaper(JSONObject jsonObject,Long mkpaperId) {
//        TkMkpaper tkMkpaper = new TkMkpaper();
//        tkMkpaper.setMkpaperId(mkpaperId);
//        tkMkpaper.setCourseId(jsonObject.getShort("courseId"));
//        tkMkpaper.setMkpaperTerm(jsonObject.getString("mkpaperTerm"));
//        tkMkpaper.setMkpaperExtime(jsonObject.getShort("mkpaperExtime"));
//        tkMkpaper.setMkpaperScore(jsonObject.getShort("mkpaperScore"));
//        tkMkpaper.setMkpaperType(jsonObject.getBoolean("mkpaperType"));
//        tkMkpaper.setPpassportId(jsonObject.getLong("ppassportId"));
//        if((tkMkpaperMapper.updateByPrimaryKey(tkMkpaper))!=0){
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 根据testpaperId修改testpaper
//     * @param testpaperId
//     * @param jsonObject
//     * @return 返回mkpaperId
//     */
//    @Override
//    public Long updateTestpaper(Long testpaperId,JSONObject jsonObject) {
//        if(jsonObject==null){
//            return null;
//        }
//        TkTestpaper tkTestpaper = null;
//       if((tkTestpaper = tkTestpaperMapper.selectByPrimaryKey(testpaperId))!=null){
//           TkTestpaper tkTestpaper1 = new TkTestpaper();
//           tkTestpaper1.setPpassportId(jsonObject.getLong("ppassportId"));
//           tkTestpaper1.setTestpaperId(testpaperId);
//           if(tkTestpaperMapper.updateByPrimaryKeySelective(tkTestpaper1)!=0){
//               return tkTestpaper.getMkpaperId();
//           }
//       }
//        return null;
//    }
//
//    /**
//     * 根据testpaperId修改testsubject
//     * @param testpaperId
//     * @param array
//     * @return
//     */
//    @Override
//    public boolean updateTestsubject(Long testpaperId,JSONArray array) {
//        if(array==null){
//            return false;
//        }
//        TkTestsubjectExample tkTestsubjectExample = new TkTestsubjectExample();
//        TkTestsubjectExample.Criteria criteria = tkTestsubjectExample.createCriteria();
//        criteria.andTestpaperIdEqualTo(testpaperId);
//        if(tkTestsubjectMapper.deleteByExample(tkTestsubjectExample)==0){
//            return false;
//        }
//            Iterator<Object> iterator = array.iterator();
//            while (iterator.hasNext()) {
//                JSONObject ob = (JSONObject) iterator.next();
//                TkTestsubject tkTestsubject = new TkTestsubject();
//                tkTestsubject.setSubjectId(ob.getLong("questionId"));
//                tkTestsubject.setTestpaperScore(ob.getShort("score"));
//                tkTestsubject.setTestpaperId(testpaperId);
//                if(tkTestsubjectMapper.insert(tkTestsubject)==0){
//                    return false;
//                }
//        }
//        return true;
//    }
//}
