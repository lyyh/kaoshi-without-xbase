package sei.tk.service.labour;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import sei.tk.service.dao.model.TkMkpaper;

/**
 * 记录人工制卷内容
 * Created by Administrator on 2016/3/15 0015.
 */
public interface LabourInsert {
    //根据testpaperId插入题目
    public boolean insertTestSubject(JSONArray jsonArray,Long testpaperId);
    //从json字符串中找出TkMkpaper的属性并存入数据库
    public Long insertTkMkpaper(TkMkpaper tkMkpaper);
    //从json字符串中找出TkTestpaper的属性并存入数据库
    public Long insertTkTestpaper(TkMkpaper tkMkpaper,Long mkpaperId,boolean testType);
    //找出tk_mkpaper中最近插入的mkpaperId
    public Long getLateMkpaperId();
    //找出tk_testpaper中最近插入的testpaperId
    public Long getLateTestpaperId();
}
