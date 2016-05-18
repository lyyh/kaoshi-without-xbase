package sei.tk.controller.robot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.service.dao.model.*;
import sei.tk.service.dao.model.vo.robot.Robotmk;
import sei.tk.service.robot.RobotService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ywl on 2016/3/17.
 */

@Controller
@RequestMapping("/robot")
public class RobotMkController {
    @Resource
    RobotService robotService;

/**
    *  考试机器制卷
    *
    *
    **/
   @RequestMapping("/robotMkpaper.do")
   @ResponseBody
   public boolean robotMkpaper(@RequestBody Robotmk robotmk){
       TkMkpaper tkMkpaper =robotmk.getTkMkpaper();
       TkMkpaperrule [] Mkpaperrules=robotmk.getTkMkpaperrules();
       List<TkMkpaperrule> tkMkpaperrules=new ArrayList<TkMkpaperrule>();
       for(TkMkpaperrule tkMkpaperrule: Mkpaperrules){
           if(tkMkpaperrule.getMkpaperruleAmount()!=0)
               tkMkpaperrules.add(tkMkpaperrule);
       }
       long  mkpaperId= robotService.insertTkMkpaerAndgetmkpareId(tkMkpaper);
       for(TkMkpaperrule tkMkpaperrule: tkMkpaperrules){
           tkMkpaperrule.setMkpaperId(mkpaperId);
           robotService.insertTkMkpaperrule(tkMkpaperrule);
       }
       if(robotService.getTkMkpaperrules(mkpaperId)==null)
           return false;
       else if(robotService.getmakePaper(robotService.getTkMkpaperrules(mkpaperId))==null)
           return false;
       else
       {
           List<TkSubject> tkSubjects = robotService.robotmakepaper(robotService.getmakePaper(robotService.getTkMkpaperrules(mkpaperId)));
           Map<Short,Short> maps =new HashMap<Short,Short>();
           for(TkMkpaperrule tkMkpaperrule:tkMkpaperrules){
               maps.put(tkMkpaperrule.getQuetypeId(),tkMkpaperrule.getMkpaperruleScore());
           }
           long testpaper1d=robotService.insertTkTestpaperAndgettestpaperId(mkpaperId);  //生成试卷
           List<TkTestsubject> tkTestsubjects=new ArrayList<TkTestsubject>();
           for(TkSubject tkSubject:tkSubjects){      //设置试卷信息
               TkTestsubject tkTestsubject=new TkTestsubject();
               tkTestsubject.setTestpaperId(testpaper1d);
               tkTestsubject.setSubjectId(tkSubject.getSubjectId());
               tkTestsubject.setTestpaperScore(maps.get(tkSubject.getQuetypeId()));
               tkTestsubjects.add(tkTestsubject);
           }
           for(TkTestsubject tkTestsubject: tkTestsubjects) {
               robotService.insertTkTestsubject(tkTestsubject);
           }

           return  true;
       }

   }

/*    @RequestMapping("/robotMkmaper")
    @ResponseBody
    public boolean robotMkmaper(long  mkpaperId,List<TkMkpaperrule> tkMkpaperrules,int chapter,boolean testtype){
       if(robotService.getmakePaper(robotService.getTkMkpaperrule(mkpaperId),chapter)==null)
           return false;
       else
       {
         List<TkSubject> tkSubjects = robotService.robotmakepaper(robotService.getmakePaper(robotService.getTkMkpaperrule(mkpaperId),chapter));
           Map<Short,Short> maps =new HashMap<Short,Short>();
           for(TkMkpaperrule tkMkpaperrule:tkMkpaperrules){
              maps.put(tkMkpaperrule.getQuetypeId(),tkMkpaperrule.getMkpaperruleScore());
           }
        long testpaper1d=robotService.insertTkTestpaperAndgettestpaperId(mkpaperId,testtype);
         List<TkTestsubject> tkTestsubjects=new ArrayList<TkTestsubject>();
           for(TkSubject tkSubject:tkSubjects){
               TkTestsubject tkTestsubject=new TkTestsubject();
               tkTestsubject.setTestpaperId(testpaper1d);
               tkTestsubject.setSubjectId(tkSubject.getSubjectId());
               tkTestsubject.setTestpaperScore(maps.get(tkSubject.getQuetypeId()));
               tkTestsubjects.add(tkTestsubject);
           }
            for(TkTestsubject tkTestsubject: tkTestsubjects) {
                robotService.insertTkTestsubject(tkTestsubject);
            }

           return  true;
       }

    }*/
/*
    */
/**
 *  查找制卷信息
 *
 * **//*

    @RequestMapping("/getTkMkpaper")
    @ResponseBody
    public List<TkMkpaper> getTkMkpaper(){
        List<TkMkpaper> mkpapers=robotService.getTkMkmaper();
        return  mkpapers;
    }
    */
/**
 *  根据制卷id得到相应的制卷规则
 *
 * **//*

    @RequestMapping("/getTkMkpaperrule")
    @ResponseBody
    public List<TkMkpaperrule> getTkMkpaperrule(long  mkpaperId){
        List<TkMkpaperrule> tkMkpaperrules=robotService.getTkMkpaperrules(mkpaperId);
        return  tkMkpaperrules;
    }
    */
/**
 *  得到一个制卷规则的具体信息
 *
 * **//*

    @RequestMapping("/getoneTkMkmaperrule")
    @ResponseBody
    public TkMkpaperrule getoneTkMkmaperrule(TkMkpaperrule tkMkpaperrule){
        return  robotService.getOneTkMkpaperrule(tkMkpaperrule);
    }

    */
/**
 * 增加制卷规则
 *
 * **//*

    @RequestMapping("/insertTkMkpaperrule")
    @ResponseBody
    public boolean insertTkMkpaperrule(TkMkpaperrule tkMkpaperrule){
        int i = robotService.insertTkMkpaperrule(tkMkpaperrule);
        if (i<=0)
            return  false;
        else
            return  true;
    }
    */
/**
 * 修改制卷规则
 *r
 * **//*

    @RequestMapping("/updateTkMkpaperrule")
    @ResponseBody
    public boolean updateTkMkpaperrule(TkMkpaperrule tkMkpaperrule){
        int i = robotService.updateTkMkpaperrule(tkMkpaperrule);
        if (i<=0)
            return  false;
        else
            return  true;
    }
*/



}
