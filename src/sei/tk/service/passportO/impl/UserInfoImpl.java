package sei.tk.service.passportO.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkStudentMapper;
import sei.tk.service.dao.mapper.TkTeacherMapper;
import sei.tk.service.dao.model.TkStudent;
import sei.tk.service.dao.model.TkTeacher;
import sei.tk.service.dao.model.vo.passport.StudentInfoVo;
import sei.tk.service.dao.model.vo.passport.TeacherInfoVo;
import sei.tk.service.passportO.UserInfoService;
import sei.tk.util.TkConfig;
import sei.tk.util.exception.TKException;

import javax.annotation.Resource;
/**
 * Created by liuruijie on 2016/3/25.
 */
@Service
public class UserInfoImpl implements UserInfoService{
    @Resource
    TkStudentMapper tkStudentMapper;
    @Resource
    TkTeacherMapper tkTeacherMapper;

    @Override
    public TeacherInfoVo getInfoTeacher(Long passportId) {//获取教师信息
        if(passportId==null)throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");

//        TkTeacher tkTeacher=tkTeacherMapper.selectByPrimaryKey(passportId);
//        //查出来的教师信息保留基本信息
//        TeacherInfoVo teacherInfoVo=new TeacherInfoVo();
//        teacherInfoVo.setUserId(tkTeacher.getTeaCode());
//        teacherInfoVo.setUserName(tkTeacher.getTeaName());
//        teacherInfoVo.setPassportId(tkTeacher.getPpassportId());
//        teacherInfoVo.setTeaEmail(tkTeacher.getTeaEmail());
//        teacherInfoVo.setTeaGender(tkTeacher.getTeaGender());
//        teacherInfoVo.setTeaInstitute(tkTeacher.getTeaInstitute());
//        teacherInfoVo.setTeaMajor(tkTeacher.getTeaMajor());
//        teacherInfoVo.setTeaTel(tkTeacher.getTeaTel());

//        return teacherInfoVo;
        return null;
    }

    @Override
    public StudentInfoVo getInfoStudent(Long passportId) {
//        if(passportId==null)throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
//
//        TkStudent tkStudent=tkStudentMapper.selectByPrimaryKey(passportId);
//        //仅保留基本信息
//        StudentInfoVo studentInfoVo=new StudentInfoVo();
//        studentInfoVo.setUserName(tkStudent.getStuName());
//        studentInfoVo.setPassportId(tkStudent.getPpassportId());
//        studentInfoVo.setUserId(tkStudent.getStuCode());
//        studentInfoVo.setStuClassId(tkStudent.getStuClassId());
//        studentInfoVo.setStuEmail(tkStudent.getStuEmail());
//        studentInfoVo.setStuGender(tkStudent.getStuGender());
//        studentInfoVo.setStuInstitute(tkStudent.getStuInstitute());
//        studentInfoVo.setStuMajor(tkStudent.getStuMajor());
//        studentInfoVo.setStuTel(tkStudent.getStuTel());
//
//        return studentInfoVo;
        return null;
    }

    @Override
    public void updateTeacher(TeacherInfoVo teacherInfoVo) {//更新教师信息
        if(teacherInfoVo==null||teacherInfoVo.getTeaTel()==null||teacherInfoVo.getTeaEmail()==null)throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
        TkTeacher tkTeacher=new TkTeacher();
        //设置修改信息
        tkTeacher.setPassportId(teacherInfoVo.getPassportId());
        tkTeacher.setTeaEmail(teacherInfoVo.getTeaEmail());
        tkTeacher.setTeaTel(teacherInfoVo.getTeaTel());

        if(0==tkTeacherMapper.updateByPrimaryKeySelective(tkTeacher)){
            throw new TKException(TkConfig.INVALID_ACTION,"修改无效");
        }
    }

    @Override
    public void updateStudent(StudentInfoVo studentInfoVo) {//更新学生信息
        if(studentInfoVo==null||studentInfoVo.getStuEmail()==null||studentInfoVo.getStuTel()==null)
            throw new TKException(TkConfig.INVALID_ACTION,"参数不合法");
        TkStudent tkStudent=new TkStudent();
        //设置修改信息
//        tkStudent.setPpassportId(studentInfoVo.getPassportId());
//        tkStudent.setStuEmail(studentInfoVo.getStuEmail());
//        tkStudent.setStuTel(studentInfoVo.getStuTel());

        if(0==tkStudentMapper.updateByPrimaryKeySelective(tkStudent)){
            throw new TKException(TkConfig.INVALID_ACTION,"修改无效");
        }
    }
}