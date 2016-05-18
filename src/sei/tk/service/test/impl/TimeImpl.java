package sei.tk.service.test.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkMkpaperMapper;
import sei.tk.service.dao.mapper.TkTestanswerMapper;
import sei.tk.service.dao.mapper.TkTestpaperMapper;
import sei.tk.service.dao.model.*;
import sei.tk.service.dao.model.vo.test.TestpaperInfVo;
import sei.tk.service.test.TimeService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by liuruijie on 2016/3/12.
 * 控制考试时间相关业务
 */
@Service
public class TimeImpl implements TimeService {
    @Resource
    TkMkpaperMapper tkMkpaperMapper;
    @Resource
    TkTestpaperMapper tkTestpaperMapper;

    @Override
    public Long getOddTime(HttpSession session) {//获取剩余考试时间
        Long start = (Long) session.getAttribute("startTime");
        if(start==null)return null;
        TestpaperInfVo testpaperInfVo = (TestpaperInfVo) session.getAttribute("testpaperInfVo");
        if(testpaperInfVo==null)return null;
        //Long start = (Long) session.getAttribute("startTime");
        //查询考试所需时间
        int mkpaperExtime=testpaperInfVo.getTotalTime();
        //计算剩余时间
        Long lastTime =System.currentTimeMillis() - start;
        return (long)mkpaperExtime*1000*60 - lastTime;
    }

    @Override
    public void setStart(HttpSession session) {//记录考试开始时间
        session.setAttribute("startTime", System.currentTimeMillis());
    }
}
