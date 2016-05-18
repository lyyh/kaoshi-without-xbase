package sei.tk.service.passport.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkStudentMapper;
import sei.tk.service.dao.mapper.TkTeacherMapper;
import sei.tk.service.dao.model.TkStudent;
import sei.tk.service.dao.model.TkStudentExample;
import sei.tk.service.dao.model.TkTeacher;
import sei.tk.service.dao.model.TkTeacherExample;
import sei.tk.service.passport.PassportServie;
import sei.tk.util.TkConfig;
import sei.tk.util.exception.TKException;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuruijie on 2016/3/25.
 */
@Service
public class PassportImpl implements PassportServie{

    @Resource
    TkStudentMapper tkStudentMapper;
    @Resource
    TkTeacherMapper tkTeacherMapper;

    @Override
    public void addStudent(TkStudent tkStudent) {//添加学生
        if(tkStudent==null
                ||tkStudent.getStuPassword()==null||tkStudent.getStuPassword().equals("")
                ||tkStudent.getStuCode()==null||tkStudent.getStuCode().equals("")){
            throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
        }
        if(!isStudentCode(tkStudent.getStuCode())){
            throw new TKException(TkConfig.INVALID_ACTION,"学号不符合规则");
        }
        //不允许账号重复
        TkStudentExample tkStudentExample=new TkStudentExample();
        TkStudentExample.Criteria criteria = tkStudentExample.createCriteria();
        criteria.andStuCodeEqualTo(tkStudent.getStuCode());
        List<TkStudent> tkStudentList=tkStudentMapper.selectByExample(tkStudentExample);
        if(tkStudentList.size()>0){
           throw new TKException(TkConfig.INVALID_ACTION,"账号已存在");
        }
        if(tkStudentMapper.insertSelective(tkStudent)==0){
            throw new TKException(TkConfig.DATABASE_ERROR,"添加学生失败");
        }
    }

    @Override
    public void addTeacher(TkTeacher tkTeacher) {//添加教师
        if(tkTeacher==null
                ||tkTeacher.getTeaCode()==null||tkTeacher.getTeaCode().equals("")
                ||tkTeacher.getTeaPassword()==null||tkTeacher.getTeaPassword().equals("")){
            throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
        }
        if(!isStudentCode(tkTeacher.getTeaCode())){
            throw new TKException(TkConfig.INVALID_ACTION,"教师号不符合规则");
        }
        TkTeacherExample tkTeacherExample=new TkTeacherExample();
        TkTeacherExample.Criteria criteria = tkTeacherExample.createCriteria();
        criteria.andTeaCodeEqualTo(tkTeacher.getTeaCode());
        List<TkTeacher> tkTeacherList=tkTeacherMapper.selectByExample(tkTeacherExample);
        if(tkTeacherList.size()>0){
            throw new TKException(TkConfig.INVALID_ACTION,"账号已存在");
        }
        if(0==tkTeacherMapper.insertSelective(tkTeacher)){
            throw new TKException(TkConfig.DATABASE_ERROR,"添加教师失败");
        }
    }

    @Override
    public void modifyPasswordStudent(Long passportId, String oldpsw, String newpsw) {//学生修改密码
        if(passportId==null)throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
        TkStudent tkStudent=tkStudentMapper.selectByPrimaryKey(passportId);
        if(!tkStudent.getStuPassword().equals(oldpsw))throw new TKException(TkConfig.INVALID_ACTION,"旧密码不正确");
        tkStudent.setStuPassword(newpsw);
        if(0==tkStudentMapper.updateByPrimaryKeySelective(tkStudent)){
            throw new TKException(TkConfig.DATABASE_ERROR,"修改失败");
        }
    }

    @Override
    public void modifyPasswordTeacher(Long passportId, String oldpsw, String newpsw) {//老师修改密码
        if(passportId==null)throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
        TkTeacher tkTeacher=tkTeacherMapper.selectByPrimaryKey(passportId);
        if(!tkTeacher.getTeaPassword().equals(oldpsw))throw new TKException(TkConfig.INVALID_ACTION,"旧密码不正确");
        tkTeacher.setTeaPassword(newpsw);
        if(0==tkTeacherMapper.updateByPrimaryKeySelective(tkTeacher)){
            throw new TKException(TkConfig.DATABASE_ERROR,"修改失败");
        }
    }

    @Override
    public void deleteStudent(Long passportId) {//删除学生
        if(passportId==null)throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
        if(0==tkStudentMapper.deleteByPrimaryKey(passportId)){
            throw new TKException(TkConfig.DATABASE_ERROR,"删除学生失败");
        }
    }

    @Override
    public void deleteTeacher(Long passportId) {//删除教师
        if(passportId==null)throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
        if(0==tkTeacherMapper.deleteByPrimaryKey(passportId)){
            throw new TKException(TkConfig.DATABASE_ERROR,"删除教师失败");
        }
    }

    @Override
    public boolean isTeacherCode(String passportCode) {
        return passportCode.matches(TkConfig.teacherCodeRegexRule);
    }

    @Override
    public boolean isStudentCode(String passportCode) {
        return passportCode.matches(TkConfig.studentCodeRegexRule);
    }
}
