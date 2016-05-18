package sei.tk.service.passport.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkStudentMapper;
import sei.tk.service.dao.mapper.TkTeacherMapper;
import sei.tk.service.dao.model.TkStudent;
import sei.tk.service.dao.model.TkStudentExample;
import sei.tk.service.dao.model.TkTeacher;
import sei.tk.service.dao.model.TkTeacherExample;
import sei.tk.service.dao.model.vo.passport.Passport;
import sei.tk.service.dao.model.vo.passport.SessionPassport;
import sei.tk.service.passport.LoginService;
import sei.tk.util.TkConfig;
import sei.tk.util.exception.TKException;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuruijie on 2016/3/25.
 */
@Service
public class LoginImpl implements LoginService{

    @Resource
    TkStudentMapper tkStudentMapper;
    @Resource
    TkTeacherMapper tkTeacherMapper;

    @Override
    public SessionPassport doLoginStudent(Passport passport) {
        if(passport.getPassportCode()==null||passport.getPassword()==null)
            throw new TKException(TkConfig.INVALID_ACTION,"学号或密码不能为空");
        //查询要登陆的学生信息
        TkStudentExample tkStudentExample=new TkStudentExample();
        TkStudentExample.Criteria stuCriteria = tkStudentExample.createCriteria();
        stuCriteria.andStuCodeEqualTo(passport.getPassportCode());
        stuCriteria.andStuPasswordEqualTo(passport.getPassword());
        List<TkStudent> tkStudentList=tkStudentMapper.selectByExample(tkStudentExample);
        if(tkStudentList==null||tkStudentList.size()==0)
            throw new TKException(TkConfig.INVALID_ACTION,"学号或密码错误");
        TkStudent tkStudent=tkStudentList.get(0);
        //只保留最基本的信息
        SessionPassport sessionPassport=new SessionPassport();
        sessionPassport.setPpassportId(tkStudent.getPpassportId());
        sessionPassport.setPassportCode(tkStudent.getStuCode());
        sessionPassport.setPassportName(tkStudent.getStuName());

        return sessionPassport;
    }

    @Override
    public SessionPassport doLoginTeacher(Passport passport) {
        if(passport.getPassportCode()==null||passport.getPassword()==null)
            throw new TKException(TkConfig.INVALID_ACTION,"教师号或密码不能为空");
        //查询要登陆的教师信息
        TkTeacherExample tkTeacherExample=new TkTeacherExample();
        TkTeacherExample.Criteria criteria = tkTeacherExample.createCriteria();
        criteria.andTeaCodeEqualTo(passport.getPassportCode());
        criteria.andTeaPasswordEqualTo(passport.getPassword());
        List<TkTeacher> tkTeacherList=tkTeacherMapper.selectByExample(tkTeacherExample);
        if(tkTeacherList==null||tkTeacherList.size()==0)
            throw new TKException(TkConfig.INVALID_ACTION,"教师号或密码错误");
        TkTeacher tkTeacher=tkTeacherList.get(0);
        //只保留最基本的信息
        SessionPassport sessionPassport=new SessionPassport();
        sessionPassport.setPassportName(tkTeacher.getTeaName());
        sessionPassport.setPassportCode(tkTeacher.getTeaCode());
        sessionPassport.setPpassportId(tkTeacher.getPpassportId());

        return sessionPassport;
    }
}
