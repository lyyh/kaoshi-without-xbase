package sei.tk.util;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkQuecollMapper;
import sei.tk.service.dao.mapper.TkSubjectMapper;
import sei.tk.service.dao.model.TkQuecoll;
import sei.tk.service.dao.model.TkQuecollExample;
import sei.tk.service.dao.model.TkSubject;
import sei.tk.service.dao.model.TkSubjectWithBLOBs;
import sei.tk.service.dao.model.vo.test.SubjectInfoVo;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liuyanhao on 2016/3/23 0023.
 */

public class StudyUtil {
//    @Resource
//    TkQuecollMapper tkQuecollMapper;
//    @Resource
//    TkSubjectMapper tkSubjectMapper;
    /**
     * 对填空题的答案进行拼接
     *
     * @param blankAnw
     * @return
     */
    public static String linkBlank(String blankAnw[]) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String anw : blankAnw) {
            stringBuffer.append(anw + "@#%");
        }
        return stringBuffer.toString();
    }

    /**
     * 将题目转化成可以显示的题目
     *
     * @return
     */
//    public SubjectInfoVo getSubInfoVo(TkSubject tkSubject) {
//        TkSubjectWithBLOBs tkSubjectWithBLOBs = null;
//        SubjectInfoVo subjectInfoVo = new SubjectInfoVo();
//            if (tkSubject.getQuetypeId() == 1) {
//                SubjectInfoVo sInfo = new SubjectInfoVo();
//                //得到题目编号
//                sInfo.setSubjectId(tkSubject.getSubjectId());
//                sInfo.setType(1);
//                System.out.println(tkSubjectMapper.selectByPrimaryKey(Long.valueOf(2)));
//                tkSubjectWithBLOBs = tkSubjectMapper.selectByPrimaryKey(tkSubject.getSubjectId());
//                //拆分选择题
//                String str[] = tkSubjectWithBLOBs.getSubjectOption().split("@#%");
//                //将String数组转为list中
//                List<String> strList = Arrays.asList(str);
//                sInfo.setSubjectOptions(strList);
//                //得到题干
//                sInfo.setSubjectName(tkSubjectWithBLOBs.getSubjectName());
//                //得到题目解析
//                sInfo.setSubjectSolution(tkSubjectWithBLOBs.getSubjectSolution());
//                return sInfo;
//            } else if (tkSubject.getQuetypeId() == 2) {
//                SubjectInfoVo sInfo = new SubjectInfoVo();
//                sInfo.setSubjectId(tkSubject.getSubjectId());
////                 sInfo.setNumber(list2.size()+1);
//                sInfo.setType(2);
//                tkSubjectWithBLOBs = tkSubjectMapper.selectByPrimaryKey(tkSubject.getSubjectId());
//                //得到题干
//                sInfo.setSubjectName(tkSubjectWithBLOBs.getSubjectName());
//                //得到题目解析
//                sInfo.setSubjectSolution(tkSubjectWithBLOBs.getSubjectSolution());
//                return sInfo;
//            } else if (tkSubject.getQuetypeId() == 3) {
//                SubjectInfoVo sInfo = new SubjectInfoVo();
//                sInfo.setSubjectId(tkSubject.getSubjectId());
////                 sInfo.setNumber(list3.size()+1);
//                sInfo.setType(3);
//                tkSubjectWithBLOBs = tkSubjectMapper.selectByPrimaryKey(tkSubject.getSubjectId());
//                //得到题干
//                sInfo.setSubjectName(tkSubjectWithBLOBs.getSubjectName());
//                //分割填空题
//                String strings[] = tkSubjectWithBLOBs.getSubjectAnswer().split("@#%");
//                sInfo.setBlankamount(strings.length);
//                //得到题目解析
//                sInfo.setSubjectSolution(tkSubjectWithBLOBs.getSubjectSolution());
//                return sInfo;
//            } else if (tkSubject.getQuetypeId() == 4) {
//                SubjectInfoVo sInfo = new SubjectInfoVo();
//                sInfo.setSubjectId(tkSubject.getSubjectId());
////                 sInfo.setNumber(list4.size()+1);
//                sInfo.setType(4);
//                tkSubjectWithBLOBs = tkSubjectMapper.selectByPrimaryKey(tkSubject.getSubjectId());
//                //得到题干
//                sInfo.setSubjectName(tkSubjectWithBLOBs.getSubjectName());
//                //得到题目解析
//                sInfo.setSubjectSolution(tkSubjectWithBLOBs.getSubjectSolution());
//                return sInfo;
//            }
//        return null;
//    }

    /**
     * 通过题目id得到错误的答题答案
     * @param subId
     * @return
     */
//    public String getErrAnw(Long subId){
//        TkQuecollExample tkQuecollExample = new TkQuecollExample();
//        TkQuecollExample.Criteria criteria = tkQuecollExample.createCriteria();
//        criteria.andStuIdEqualTo(subId);
//        //根据题目id找到该题目的收藏
//        List<TkQuecoll> tkQuecolls = tkQuecollMapper.selectByExample(tkQuecollExample);
//        TkQuecoll tkQuecoll = tkQuecolls.get(0);
//        return tkQuecoll.getQuecollAnswer();
//    }
}
