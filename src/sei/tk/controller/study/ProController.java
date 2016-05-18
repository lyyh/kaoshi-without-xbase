package sei.tk.controller.study;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.service.study.ProService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 学习进度Contoller
 * Created by liuyanhao on 2016/3/26 0026.
 */
@RequestMapping(value = "/pro")
@Controller
public class ProController {
    @Resource
    ProService proService;

    /**
     *查看某一科目总的学习进度
     * @param stuId
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/totalPro")
    @ResponseBody
    public String showTotalPro(Long stuId,Short courseId){
        String totalPro = proService.showTotalPro(stuId,courseId);
        return totalPro;
    }

    /**
     * 查看章节学习进度（完成的章节数）
     * @param stuId
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/chapPro")
    @ResponseBody
    public List<Integer> showChapPro(Long stuId,Short courseId){
        List<Integer> integers = proService.showChapPro(stuId,courseId);
        return integers;
    }

    /**
     * 保存学习进度
     * @param subNum
     * @param stuId
     * @param chapterId
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/savePro")
    @ResponseBody
    public String savePro(int subNum,Long stuId,Byte chapterId,Short courseId){
        String str = proService.saveChapSch(subNum,stuId,chapterId,courseId);
        return str;
    }
}
