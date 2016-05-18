package sei.tk.service.labour;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import sei.tk.service.dao.model.TkMkpaper;
import sei.tk.service.dao.model.TkTestpaper;
import sei.tk.service.dao.model.TkTestsubject;

import java.util.List;
import java.util.Map;

/**
 * Created by liuyanhao on 2016/3/12 0012.
 * 制卷接口（保留）
 */
public interface LabourMkpaper {
//    //显示所有试卷信息（出卷人，出卷试卷，考试科目）
//    public List<Map<String,Object>> seekTestpaperId();
//    //根据testpaperId查找显示试卷信息、题目信息
//    public Map<String,Object> seekTestpaper(Long testpaperId);
//    //根据testpaperId查找mkpaper
//    public TkMkpaper seekMkpaper(Long testpaperId);
    //根据testpaperId查找mkpaperId
    public Long seekMkpaperId(Long testpaperId);
//    //根据testpaperId插入题目
//    public boolean insertTestSubject(JSONArray jsonArray,Long testpaperId);
//    //从json字符串中找出TkMkpaper的属性并存入数据库
//    public Long insertTkMkpaper(JSONObject jsonObject);
//    //从json字符串中找出TkTestpaper的属性并存入数据库
//    public Long insertTkTestpaper(JSONObject jsonObject,Long mkpaperId);
//    //找出tk_mkpaper中最近插入的mkpaperId
//    public Long getLateMkpaperId();
//    //找出tk_testpaper中最近插入的testpaperId
//    public Long getLateTestpaperId();
//    //根据mkpaperId删除mkpaper
//    public boolean deleteMkpaper(Long testpaperId);
//    //根据testpaperId删除testpaper
//    public boolean deleteTestpaper(Long testpaperId);
//    //根据testpaperId删除testsubject
//    public boolean deleteTestsubject(Long testpaperId);
    //根据mkpaperId修改mkpaper
    public boolean updateMkpaper(JSONObject jsonObject,Long mkpaperId);
    //根据testpaperId修改testpaper，返回mkpaperId
    public Long updateTestpaper(Long testpaperId,JSONObject jsonObject);
    //根据testpaperId修改testsubject
    public boolean updateTestsubject(Long testpaperId,JSONArray jsonArray);
}
