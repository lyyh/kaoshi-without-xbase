package sei.tk.controller.study;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.service.dao.model.vo.study.SubjectInfoVo;
import sei.tk.service.study.CollSubjectService;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 收藏题目Controller
 * Created by liuyanhao on 2016/3/23 0023.
 */
@RequestMapping(value = "/coll")
@Controller
public class CollController {
    @Resource
    CollSubjectService collSubjectService;

    /**
     * 显示学生收藏的所有课程id
     * @param stuId
     * @return
     */
    @RequestMapping(value = "/showCour")
    @ResponseBody
    public Set<Short> showCour(Long stuId){
        List<Long> subIds = collSubjectService.getCollSubId(stuId);
        Set<Short> set = collSubjectService.getCollCourId(stuId,subIds);
        return set;
    }

    /**
     * 显示收藏的所有题目
     * @param stuId
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/showSub")
    @ResponseBody
    public List<SubjectInfoVo> showSub(Long stuId,Short courseId){
        //得到所有收藏的题目id
        List<Long> subIds = collSubjectService.getCollSubId(stuId);
        //得到所有题目
        List<SubjectInfoVo> list = collSubjectService.initCollSubject(courseId,subIds);
        return list;
    }

    /**
     * 收藏题目
     * @param errorAnw
     * @param stuId
     * @param subId
     * @return
     */
    @RequestMapping(value = "/collSub")
    @ResponseBody
    public String collSub(String errorAnw,Long stuId,Long subId){
        String str = collSubjectService.collSubjects(errorAnw,stuId,subId);
        return str;
    }

    /**
     * 删除收藏的题目
     * @param subId
     * @return
     */
    @RequestMapping(value = "/delColl")
    @ResponseBody
    public String delSub(Long subId){
        String str = collSubjectService.delCollSubject(subId);
        return str;
    }

    /**
     * 批量删除收藏的题目
     * @param subIds
     * @return
     */
    @RequestMapping(value = "/batDelColl")
    @ResponseBody
    public String batdelSub(List<Long> subIds){
        String str= collSubjectService.batchDelCollSubject(subIds);
        return str;
    }
}
