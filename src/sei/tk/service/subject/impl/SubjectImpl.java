package sei.tk.service.subject.impl;

import org.springframework.stereotype.Service;
import sei.tk.util.Page;
import sei.tk.service.dao.mapper.*;
import sei.tk.service.dao.model.*;
import sei.tk.service.dao.model.vo.subject.SubjectInfo;
import sei.tk.service.subject.SubjectServie;
import sei.tk.util.DateFormat;
import sei.tk.util.LittleUtil;
import sei.tk.util.StudyUtil;
import sei.tk.util.TkConfig;
import sei.tk.util.exception.TKException;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liuruijie on 2016/3/18.
 */
@Service
public class SubjectImpl implements SubjectServie{
    @Resource
    TkSubjectMapper tkSubjectMapper;
    @Resource
    TkTeacherMapper tkTeacherMapper;
    @Resource
    SubjectInfoMapper subjectInfoMapper;
    @Resource
    TkKnopointMapper tkKnopointMapper;
    @Resource
    TkCourseMapper tkCourseMapper;

    @Override
    public void addSubject(TkSubjectWithBLOBs tkSubject,String[] choseOptions,String[] blankAnswers) {//提交所出的题目
        if(tkSubject==null||tkSubject.getSubjectName()==null||tkSubject.getSubjectName().equals(""))
            throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
        if(tkSubject.getQuetypeId()==1){//选择题要拼接选项字符串
            tkSubject.setSubjectOption(StudyUtil.linkBlank(choseOptions));
        }else if(tkSubject.getQuetypeId()==3){//填空题要拼接答案字符串
            tkSubject.setSubjectAnswer(StudyUtil.linkBlank(blankAnswers));
            tkSubject.setSubjectOption("");
        }else{
            tkSubject.setSubjectOption("");
        }
        if(tkSubject.getSubjectSolution()==null){tkSubject.setSubjectSolution(" ");}

        if(tkSubjectMapper.insertSelective(tkSubject)==0){
            throw new TKException(TkConfig.INVALID_ACTION,"未添加任何记录");
        }
    }

    @Override
    public Page getSubjects(TkSubject tkSubject,Integer page, Integer rows) {
        if(page==null||rows==null)throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
        TkSubjectExample tkSubjectExample=new TkSubjectExample();
        TkSubjectExample.Criteria criteria = tkSubjectExample.createCriteria();
        tkSubjectExample.setRow(rows);
        tkSubjectExample.setStart((page-1)*rows);
        //拼接条件
        if(tkSubject!=null) {
            if (tkSubject.getQuetypeId() != null) criteria.andQuetypeIdEqualTo(tkSubject.getQuetypeId());
            if (tkSubject.getCourseId() != null) criteria.andCourseIdEqualTo(tkSubject.getCourseId());
            if (tkSubject.getChapterId() != null) criteria.andChapterIdEqualTo(tkSubject.getChapterId());
            if (tkSubject.getKnopointId() != null) criteria.andKnopointIdEqualTo(tkSubject.getKnopointId());
            if (tkSubject.getLevelId() != null) criteria.andLevelIdEqualTo(tkSubject.getLevelId());
            if (tkSubject.getPpassportId() != null) criteria.andPpassportIdEqualTo(tkSubject.getPpassportId());
        }
        return new Page(tkSubjectMapper.selectByExample(tkSubjectExample),tkSubjectMapper.countByExample(tkSubjectExample));
    }

    @Override
    public void updateSubject(TkSubjectWithBLOBs tkSubject) {
        if(tkSubject==null||tkSubject.getSubjectId()==null)throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
        if(tkSubjectMapper.updateByPrimaryKeySelective(tkSubject)==0){
            throw new TKException(TkConfig.INVALID_ACTION,"未产生任何更新");
        }
    }

    @Override
    public TkSubjectWithBLOBs getSubjectById(Long subjectId) {
        if(subjectId==null)throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
        //查询题目
        TkSubjectWithBLOBs tkSubjectWithBLOBs=tkSubjectMapper.selectByPrimaryKey(subjectId);
        //格式化时间，方便输出
        tkSubjectWithBLOBs.setPpassportTimeString(DateFormat.DateToString(tkSubjectWithBLOBs.getPpassportTime(),"yyyy-MM-dd hh-mm-ss"));
        //查询录题人的姓名
        TkTeacher tkTeacher=tkTeacherMapper.selectByPrimaryKey(tkSubjectWithBLOBs.getPpassportId());
        if(tkTeacher==null||tkTeacher.getTeaName()==null)throw new TKException(TkConfig.INVALID_ACTION,"查询异常");
        if(tkTeacher!=null)tkSubjectWithBLOBs.setPpassportName(tkTeacher.getTeaName());

        return tkSubjectWithBLOBs;
    }

    @Override
    public void delectSubject(Long subjectId) {
        if(subjectId==null)throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
        if(tkSubjectMapper.deleteByPrimaryKey(subjectId)==0){
            throw new TKException(TkConfig.INVALID_ACTION,"删除无效");
        }
    }

    @Override
    public void addSubject(SubjectInfo subjectInfo) {
        if(subjectInfo==null)throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
        //要修改的学科数据库有没有，没有就新增
        if(subjectInfo.getCourse()!=null){
            TkCourseExample tkCourseExample=new TkCourseExample();
            TkCourseExample.Criteria criteria = tkCourseExample.createCriteria();
            criteria.andCourseNameEqualTo(subjectInfo.getCourse());
            List<TkCourse> courseList=tkCourseMapper.selectByExample(tkCourseExample);
            if(courseList.size()==0){
                TkCourse tkCourse=new TkCourse();
                tkCourse.setCourseName(subjectInfo.getCourse());
                tkCourseMapper.insertSelective(tkCourse);
                subjectInfo.setCourseId(tkCourseMapper.selectByExample(tkCourseExample).get(0).getCourseId());
            }else{
                subjectInfo.setCourseId(courseList.get(0).getCourseId());
            }
        }

        //要修改的知识点数据库有没有，没有则新增
        if(subjectInfo.getKnopoint()!=null){
            TkKnopointExample tkKnopointExample=new TkKnopointExample();
            TkKnopointExample.Criteria criteria1 = tkKnopointExample.createCriteria();
            criteria1.andKnopointNameEqualTo(subjectInfo.getKnopoint());
            criteria1.andChapterIdEqualTo(subjectInfo.getChapterId());
            criteria1.andCourseIdEqualTo(subjectInfo.getCourseId());
            List<TkKnopoint> knopointList=tkKnopointMapper.selectByExample(tkKnopointExample);
            if(knopointList.size()==0){
                TkKnopoint tkKnopoint=new TkKnopoint();
                tkKnopoint.setCourseId(subjectInfo.getCourseId());
                tkKnopoint.setChapterId(subjectInfo.getChapterId());
                tkKnopoint.setKnopointName(subjectInfo.getKnopoint());
                tkKnopointMapper.insertSelective(tkKnopoint);
                subjectInfo.setKnopointId(tkKnopointMapper.selectByExample(tkKnopointExample).get(0).getKnopointId());
            }else{
                subjectInfo.setKnopointId(knopointList.get(0).getKnopointId());
            }
        }
        //拼接选项和填空题答案字符串
        if(subjectInfo.getType()==1||subjectInfo.getType()==5){
            subjectInfo.setOptions(StudyUtil.linkBlank(subjectInfo.getSubjectOptions()));
        } else if(subjectInfo.getType()==3){
            subjectInfo.setOptions("");
            subjectInfo.setAnswer(StudyUtil.linkBlank(subjectInfo.getBlankAnswer()));
        }else{
            subjectInfo.setOptions("");
        }

        if(subjectInfo.getSubjectSolution()==null){
            subjectInfo.setSubjectSolution("");
        }

        //将vo转化为与数据库对应的po并更新信息
        TkSubjectWithBLOBs tkSubject=new TkSubjectWithBLOBs();

        tkSubject.setKnopointId(subjectInfo.getKnopointId());
        tkSubject.setChapterId(subjectInfo.getChapterId());
        tkSubject.setPpassportName(subjectInfo.getPassportName());
        tkSubject.setPpassportId(subjectInfo.getPassportId());
        tkSubject.setCourseId(subjectInfo.getCourseId());
        tkSubject.setLevelId(subjectInfo.getLevelId());
        tkSubject.setQuetypeId(subjectInfo.getType().shortValue());
        tkSubject.setSubjectAnswer(subjectInfo.getAnswer());
        tkSubject.setSubjectOption(subjectInfo.getOptions());
        tkSubject.setSubjectSolution(subjectInfo.getSubjectSolution());
        tkSubject.setSubjectName(subjectInfo.getSubjectName());

        if(tkSubjectMapper.insertSelective(tkSubject)==0){
            throw new TKException(TkConfig.INVALID_ACTION,"新增无效");
        }
    }

    @Override
    public SubjectInfo getSubjectInfoById(Long subjectId) {
        if(subjectId==null)throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
        SubjectInfo subjectInfo = subjectInfoMapper.selectSubjectInfoById(subjectId);
        if(subjectInfo==null)throw new TKException(TkConfig.INVALID_ACTION,"未获取到任何题目信息");
        subjectInfo.setCreateTimeString(DateFormat.DateToString(subjectInfo.getCreateTime(),"yyyy-MM-dd"));

        if(subjectInfo.getType()==1||subjectInfo.getType()==5){
            subjectInfo.setSubjectOptions(subjectInfo.getOptions().split("@#%"));
        }else if(subjectInfo.getType()==3){
            subjectInfo.setBlankAnswer(subjectInfo.getAnswer().split("@#%"));
        }

        return subjectInfo;
    }

    @Override
    public void updateSubject(SubjectInfo subjectInfo) {
        if(subjectInfo==null)throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
        //要修改的学科数据库有没有，没有就新增
        if(subjectInfo.getCourse()!=null){
            TkCourseExample tkCourseExample=new TkCourseExample();
            TkCourseExample.Criteria criteria = tkCourseExample.createCriteria();
            criteria.andCourseNameEqualTo(subjectInfo.getCourse());
            List<TkCourse> courseList=tkCourseMapper.selectByExample(tkCourseExample);
            if(courseList.size()==0){
                TkCourse tkCourse=new TkCourse();
                tkCourse.setCourseName(subjectInfo.getCourse());
                tkCourseMapper.insertSelective(tkCourse);
                subjectInfo.setCourseId(tkCourseMapper.selectByExample(tkCourseExample).get(0).getCourseId());
            }else{
                subjectInfo.setCourseId(courseList.get(0).getCourseId());
            }
        }

        //要修改的知识点数据库有没有，没有则新增
        if(subjectInfo.getKnopoint()!=null){
            TkKnopointExample tkKnopointExample=new TkKnopointExample();
            TkKnopointExample.Criteria criteria1 = tkKnopointExample.createCriteria();
            criteria1.andKnopointNameEqualTo(subjectInfo.getKnopoint());
            criteria1.andChapterIdEqualTo(subjectInfo.getChapterId());
            criteria1.andCourseIdEqualTo(subjectInfo.getCourseId());
            List<TkKnopoint> knopointList=tkKnopointMapper.selectByExample(tkKnopointExample);
            if(knopointList.size()==0){
                TkKnopoint tkKnopoint=new TkKnopoint();
                tkKnopoint.setCourseId(subjectInfo.getCourseId());
                tkKnopoint.setChapterId(subjectInfo.getChapterId());
                tkKnopoint.setKnopointName(subjectInfo.getKnopoint());
                tkKnopointMapper.insertSelective(tkKnopoint);
                subjectInfo.setKnopointId(tkKnopointMapper.selectByExample(tkKnopointExample).get(0).getKnopointId());
            }else{
                subjectInfo.setKnopointId(knopointList.get(0).getKnopointId());
            }
        }
        //拼接选项和填空题答案字符串
        if(subjectInfo.getType()==1){
            subjectInfo.setOptions(StudyUtil.linkBlank(subjectInfo.getSubjectOptions()));
        } else if(subjectInfo.getType()==3){
            subjectInfo.setOptions("");
            subjectInfo.setAnswer(StudyUtil.linkBlank(subjectInfo.getBlankAnswer()));
        }else{
            subjectInfo.setOptions("");
        }

        if(subjectInfo.getSubjectSolution()==null){
            subjectInfo.setSubjectSolution("");
        }

        //将vo转化为与数据库对应的po并更新信息
        TkSubjectWithBLOBs tkSubject=new TkSubjectWithBLOBs();

        tkSubject.setSubjectId(subjectInfo.getSubjectId());
        tkSubject.setKnopointId(subjectInfo.getKnopointId());
        tkSubject.setChapterId(subjectInfo.getChapterId());
        tkSubject.setPpassportName(subjectInfo.getPassportName());
        tkSubject.setPpassportId(subjectInfo.getPassportId());
        tkSubject.setCourseId(subjectInfo.getCourseId());
        tkSubject.setLevelId(subjectInfo.getLevelId());
        tkSubject.setQuetypeId(subjectInfo.getType().shortValue());
        tkSubject.setSubjectAnswer(subjectInfo.getAnswer());
        tkSubject.setSubjectOption(subjectInfo.getOptions());
        tkSubject.setSubjectSolution(subjectInfo.getSubjectSolution());
        tkSubject.setSubjectName(subjectInfo.getSubjectName());

        if(tkSubjectMapper.updateByPrimaryKeySelective(tkSubject)==0){
            throw new TKException(TkConfig.INVALID_ACTION,"未产生任何更新");
        }
    }

    @Override
    public Page getSubjects(SubjectInfo subjectInfo, Integer page, Integer rows) {
        if(page==null||rows==null)throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
        subjectInfo.setStart((page-1)*rows);//查询起始条数
        subjectInfo.setRow(rows);//查多少条
        List<SubjectInfo> subjectInfoList=subjectInfoMapper.selectSubjectInfosByPage(subjectInfo);
        //格式化时间,过滤<img>标签
        for(int i=0;i<subjectInfoList.size();i++){
            SubjectInfo s=subjectInfoList.get(i);
            s.setCreateTimeString(DateFormat.DateToString(s.getCreateTime(),"yyyy-MM-dd"));
            s.setSubjectName(LittleUtil.filterImg(s.getSubjectName()));
        }
        return new Page(subjectInfoList,subjectInfoMapper.countSubjectInfo(subjectInfo));
    }

    @Override
    public void deleteSubjectBatch(Long[] subjectIds) {
        if(subjectIds==null||subjectIds.length==0)throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");

        TkSubjectExample tkSubjectExample=new TkSubjectExample();
        TkSubjectExample.Criteria criteria = tkSubjectExample.createCriteria();
        criteria.andSubjectIdIn(Arrays.asList(subjectIds));
        if(tkSubjectMapper.deleteByExample(tkSubjectExample)==0){
            throw new TKException(TkConfig.INVALID_ACTION,"删除无效");
        }
    }
}
