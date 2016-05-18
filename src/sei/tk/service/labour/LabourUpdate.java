package sei.tk.service.labour;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 修改人工制卷
 * Created by Administrator on 2016/3/15 0015.
 */
public interface LabourUpdate {
    //根据mkpaperId修改mkpaper
    public boolean updateMkpaper(JSONObject jsonObject,Long mkpaperId);
    //根据testpaperId修改testpaper，返回mkpaperId
    public Long updateTestpaper(Long testpaperId,JSONObject jsonObject);
    //根据testpaperId修改testsubject
    public boolean updateTestsubject(Long testpaperId,JSONArray jsonArray);
}
