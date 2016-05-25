package sei.tk.service.robot;

import sei.tk.service.dao.model.TkMkpaper;
import sei.tk.service.dao.model.TkMkpaperrule;
import sei.tk.service.dao.model.TkSubject;
import sei.tk.service.dao.model.TkTestsubject;
import sei.tk.service.dao.model.vo.robot.Question;
import sei.tk.service.dao.model.vo.robot.SubNum;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ywl on 2016/3/15.
 */
public interface RobotService {

        int insertTkMkpaperrule(TkMkpaperrule tkMkpaperrule);   // 添加制卷规则
        List<SubNum> getSubNum(TkSubject tkSubject);  //通过课程id,题型id，知识点id 得到该题型该知识点每种难度的题目数
        TkMkpaperrule[] getTkMkpaperrules(long mkpaperId);    // 查询制卷规则
        int getcourseId(TkMkpaperrule tkMkpaperrule);  //根据制卷id得到课程id
        LinkedList<Question> getmakePaper(TkMkpaperrule[] tkMkpaperrules); //根据制卷规则 得到题目的一些条件 用于找出题目
        List<TkSubject> robotmakepaper(LinkedList<Question> questions);   //机器制卷  得到所需要的所有
        /**
         * 制卷
         */
        Long insertTkMkpaerAndgetmkpareId(TkMkpaper tkMkpaper);   //通过前台传来的tkmkpaper生成一个新的tkMkpaper 并得到这个id
        long insertTkTestpaperAndgettestpaperId(long mkpareId,Long ppassportId );   //通过制卷id新增一张Testpaper表并得到他的testpaper1d  type为考试类型
        void insertTkTestsubject(TkTestsubject tkTestsubjects); //把题目加进testsubjec

        /**
         *
         * 目前不用的
         * **/
        TkMkpaperrule getOneTkMkpaperrule(TkMkpaperrule tkMkpaperrule);  //得到一个制卷规则
        int updateTkMkpaperrule(TkMkpaperrule tkMkpaperrule); // 修改一个制卷规则
        List<TkMkpaper> getTkMkmaper();                 //查找制卷表

}
