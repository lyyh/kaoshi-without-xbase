package sei.tk.controller.testSchedule;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sei.tk.controller.common.TkBaseController;
import sei.tk.service.dao.model.SessionPassport;
import sei.tk.service.dao.model.vo.testSchedule.TestInfo;
import sei.tk.service.testSchedule.TeaSchService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
/**
 * Created by �������� on 2016-04-22.
 * ��ʦ���ӡ�ɾ�����޸İ��ſ���
 */
@Controller
@RequestMapping("/TeaSch")
public class TeaSchController extends TkBaseController {
    @Resource
    private TeaSchService teaSchService;

    @ResponseBody
    @RequestMapping("/addExamSch")
    public Integer addExamSch(HttpSession session, @RequestBody TestInfo testInfo) { //��ʦ��ӿ��԰��ţ��ݸ�����ѧ�����Ͱ��ţ�
        Long sessionPassport = null;
        sessionPassport = ((SessionPassport) session.getAttribute("sessionPassport")).getPassportId();
        return teaSchService.addExamSch(sessionPassport,testInfo);
    }

    @ResponseBody
    @RequestMapping("/delExamSch")
    public Integer delExamSch(HttpSession session, Long testscheduleId) {  //��ʦɾ�����԰��ţ����ݰ��ű�Ŷ�Ӧ�İ���ʱ�䣩
        Long sessionPassport = null;
        sessionPassport = ((SessionPassport) session.getAttribute("sessionPassport")).getPassportId();
        return teaSchService.delExamSch(sessionPassport,testscheduleId);
    }

    @ResponseBody
    @RequestMapping("/editExamSch")
    public Integer editExamSch(HttpSession session,@RequestBody  TestInfo testInfo) {  //��ʦ�޸Ŀ��԰��ţ����ݰ��ű�Ŷ�Ӧ�İ���ʱ�䣩
        Long sessionPassport = null;
        sessionPassport = ((SessionPassport) session.getAttribute("sessionPassport")).getPassportId();
        return teaSchService.editExamSch(sessionPassport, testInfo);
    }
}
