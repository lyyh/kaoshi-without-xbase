package sei.tk.service.study.imp;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkQuecollMapper;
import sei.tk.service.dao.mapper.TkSubjectMapper;
import sei.tk.service.dao.model.*;
import sei.tk.service.dao.model.vo.study.SubjectInfoVo;
import sei.tk.service.study.CollSubjectService;
import javax.annotation.Resource;
import java.util.*;

/**
 * 收藏题目的实现类
 * Created by liuyanhao on 2016/3/23 0023.
 */
@Service
public class CollSubjectServiceImpl implements CollSubjectService{
    @Resource
    TkQuecollMapper tkQuecollMapper;

    @Resource
    TkSubjectMapper tkSubjectMapper;

    /**
     * 收藏指定的题目
     * @param errorAnw 学生错误的答案
     * @param stuId 学生id
     * @param subId 题目id
     * @return
     */
    @Override
    public String collSubjects(String errorAnw, Long stuId, Long subId) {
        //整理收藏的信息
        TkQuecoll tkQuecoll = new TkQuecoll();
        tkQuecoll.setStuId(stuId);
        tkQuecoll.setSubjectId(subId);
        tkQuecoll.setQuecollAnswer(errorAnw);
        TkQuecollExample tkQuecollExample = new TkQuecollExample();
        TkQuecollExample.Criteria criteria = tkQuecollExample.createCriteria();
        criteria.andSubjectIdEqualTo(subId);
        //判断是否已经收藏
        if(tkQuecollMapper.selectByExample(tkQuecollExample)!=null){
            return "已经收藏该题！";
        }
        //进行收藏
        if(tkQuecollMapper.insertSelective(tkQuecoll)!=0){
            return "收藏成功！";
        }
        return "收藏失败！";
    }

    /**
     * 根据课程Id和学生收藏的所有题目找出课程对应的收藏
     * @param courseId
     * @param subIds
     * @return
     */
    @Override
    public List<SubjectInfoVo> initCollSubject(Short courseId,List<Long> subIds) {
        SubjectInfoVo subjectInfoVo = null;
        List<TkSubjectWithBLOBs> tkSubjectWithBLOBs = new ArrayList<>();
        for(Long subId:subIds) {
            TkSubjectExample tkSubjectExample = new TkSubjectExample();
            TkSubjectExample.Criteria criteria = tkSubjectExample.createCriteria();
            criteria.andSubjectIdEqualTo(subId);
            criteria.andCourseIdEqualTo(courseId);
            List<TkSubjectWithBLOBs> subjectWithBLOBs = tkSubjectMapper.selectSubOrderBy(tkSubjectExample);
            tkSubjectWithBLOBs.add(subjectWithBLOBs.get(0));
        }
        //用于存放要显示的题目
        List<SubjectInfoVo> subjectInfoVos = new ArrayList<>();
        //用于对题目进行题号排序
        Integer number = 0;
        //把数据库中的题目转化成要显示的题目
        for(TkSubjectWithBLOBs subBLOBs:tkSubjectWithBLOBs){
            //得到存在收藏里面的错误答案
            String errAnw = getErrAnw(subBLOBs.getSubjectId());
            //得到转化后的题目
            subjectInfoVo = getSubInfoVo(subBLOBs);
            //将题目再进行填充
            subjectInfoVo.setChapterId(subBLOBs.getChapterId());
            subjectInfoVo.setKnopointId(subBLOBs.getKnopointId());
            subjectInfoVo.setCourseId(subBLOBs.getCourseId());
            subjectInfoVo.setErrAnw(errAnw);
            subjectInfoVo.setNumber(++number);
            subjectInfoVos.add(subjectInfoVo);
        }
        return subjectInfoVos;
    }

    /**
     * 将题目转化成可以显示的题目
     * @param tkSubject
     * @return
     */
    public SubjectInfoVo getSubInfoVo(TkSubject tkSubject) {
        TkSubjectWithBLOBs tkSubjectWithBLOBs = null;
        if (tkSubject.getQuetypeId() == 1) {
            SubjectInfoVo sInfo = new SubjectInfoVo();
            //得到题目编号
            sInfo.setSubjectId(tkSubject.getSubjectId());
            sInfo.setType(1);
            tkSubjectWithBLOBs = tkSubjectMapper.selectByPrimaryKey(tkSubject.getSubjectId());
            //拆分选择题
            String str[] = tkSubjectWithBLOBs.getSubjectOption().split("@#%");
            //将String数组转为list中
            List<String> strList = Arrays.asList(str);
            sInfo.setSubjectOptions(strList);
            //得到题干
            sInfo.setSubjectName(tkSubjectWithBLOBs.getSubjectName());
            //得到题目解析
            sInfo.setSubjectSolution(tkSubjectWithBLOBs.getSubjectSolution());
            return sInfo;
        } else if (tkSubject.getQuetypeId() == 2) {
            SubjectInfoVo sInfo = new SubjectInfoVo();
            sInfo.setSubjectId(tkSubject.getSubjectId());
            sInfo.setType(2);
            tkSubjectWithBLOBs = tkSubjectMapper.selectByPrimaryKey(tkSubject.getSubjectId());
            //得到题干
            sInfo.setSubjectName(tkSubjectWithBLOBs.getSubjectName());
            //得到题目解析
            sInfo.setSubjectSolution(tkSubjectWithBLOBs.getSubjectSolution());
            return sInfo;
        } else if (tkSubject.getQuetypeId() == 3) {
            SubjectInfoVo sInfo = new SubjectInfoVo();
            sInfo.setSubjectId(tkSubject.getSubjectId());
            sInfo.setType(3);
            tkSubjectWithBLOBs = tkSubjectMapper.selectByPrimaryKey(tkSubject.getSubjectId());
            //得到题干
            sInfo.setSubjectName(tkSubjectWithBLOBs.getSubjectName());
            //分割填空题
            String strings[] = tkSubjectWithBLOBs.getSubjectAnswer().split("@#%");
            sInfo.setBlankamount(strings.length-1);
            //得到题目解析
            sInfo.setSubjectSolution(tkSubjectWithBLOBs.getSubjectSolution());
            return sInfo;
        } else if (tkSubject.getQuetypeId() == 4) {
            SubjectInfoVo sInfo = new SubjectInfoVo();
            sInfo.setSubjectId(tkSubject.getSubjectId());
            sInfo.setType(4);
            tkSubjectWithBLOBs = tkSubjectMapper.selectByPrimaryKey(tkSubject.getSubjectId());
            //得到题干
            sInfo.setSubjectName(tkSubjectWithBLOBs.getSubjectName());
            //得到题目解析
            sInfo.setSubjectSolution(tkSubjectWithBLOBs.getSubjectSolution());
            return sInfo;
        }
        return null;
    }
    /**
     * 通过题目id得到错误的答题答案
     * @param subId
     * @return
     */
    public String getErrAnw(Long subId){
        TkQuecollExample tkQuecollExample = new TkQuecollExample();
        TkQuecollExample.Criteria criteria = tkQuecollExample.createCriteria();
        criteria.andSubjectIdEqualTo(subId);
        //根据题目id找到该题目的收藏
        List<TkQuecoll> tkQuecolls = tkQuecollMapper.selectByExample(tkQuecollExample);
        TkQuecoll tkQuecoll = tkQuecolls.get(0);
        return tkQuecoll.getQuecollAnswer();
    }

    /**
     * 返回所有收藏的课程id
     * @param stuId
     * @return
     */
    @Override
    public Set<Short> getCollCourId(Long stuId,List<Long> subIds) {
        List<Long> subjectIds = subIds;
        //用HashSet集合存放所有courseId,避免重复
        Set<Short> courseIds = new HashSet<>();
        for(Long subjectId:subjectIds) {
            TkSubjectWithBLOBs  tkSubjectWithBLOBs = tkSubjectMapper.selectByPrimaryKey(subjectId);
            courseIds.add(tkSubjectWithBLOBs.getCourseId());
        }
        return courseIds;
    }

    /**
     * 单个删除收藏的题目
     * @param subjectId
     * @return
     */
    @Override
    public String delCollSubject(Long subjectId) {
        TkQuecollExample tkQuecollExample = new TkQuecollExample();
        TkQuecollExample.Criteria criteria = tkQuecollExample.createCriteria();
        criteria.andSubjectIdEqualTo(subjectId);
        if(tkQuecollMapper.deleteByExample(tkQuecollExample)!=0){
            return "删除成功！";
        }
        return "删除失败！";
    }

    @Override
    public String batchDelCollSubject(List<Long> subjectId) {
        for(Long subId:subjectId){
        TkQuecollExample tkQuecollExample = new TkQuecollExample();
        TkQuecollExample.Criteria criteria = tkQuecollExample.createCriteria();
        criteria.andSubjectIdEqualTo(subId);
            if(tkQuecollMapper.deleteByExample(tkQuecollExample)==0){
                return "批量删除失败！";
            }
        }
        return "批量删除成功！";
    }

    /**
     * 得到学生所收藏的所有题目Id
     * @param stuId
     * @return
     */
    @Override
    public List<Long> getCollSubId(Long stuId) {
        TkQuecollExample tkQuecollExample = new TkQuecollExample();
        TkQuecollExample.Criteria criteria = tkQuecollExample.createCriteria();
        criteria.andStuIdEqualTo(stuId);
        //得到学生的所有收藏
        List<TkQuecoll> tkQuecolls = tkQuecollMapper.selectByExample(tkQuecollExample);
        List<Long> subIds = new ArrayList<>();
        for(TkQuecoll tkQuecoll:tkQuecolls){
            subIds.add(tkQuecoll.getSubjectId());
        }
        return subIds;
    }
}
