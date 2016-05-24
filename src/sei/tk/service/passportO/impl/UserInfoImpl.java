package sei.tk.service.passportO.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkStudentMapper;
import sei.tk.service.dao.mapper.TkTeacherMapper;
import sei.tk.service.dao.model.TkStudent;
import sei.tk.service.dao.model.TkStudentExample;
import sei.tk.service.dao.model.TkTeacher;
import sei.tk.service.dao.model.TkTeacherExample;
import sei.tk.service.dao.model.vo.passport.StudentInfoVo;
import sei.tk.service.dao.model.vo.passport.TeacherInfoVo;
import sei.tk.service.passportO.UserInfoService;
import sei.tk.util.TkConfig;
import sei.tk.util.exception.TKException;

import javax.annotation.Resource;
import java.util.List;

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

        TkTeacherExample tkTeacherExample=new TkTeacherExample();
        TkTeacherExample.Criteria criteria = tkTeacherExample.createCriteria();
        criteria.andPassportIdEqualTo(passportId);
        List<TkTeacher> tkTeacherList=tkTeacherMapper.selectByExample(tkTeacherExample);
        if(tkTeacherList.size()==0){
            return null;
        }
        TkTeacher tkTeacher=tkTeacherList.get(0);
        //查出来的教师信息保留基本信息
        TeacherInfoVo teacherInfoVo=new TeacherInfoVo();
        teacherInfoVo.setUserId(tkTeacher.getTeacherId());
        teacherInfoVo.setUserName(tkTeacher.getTeaName());
        teacherInfoVo.setPassportId(tkTeacher.getPassportId());
        teacherInfoVo.setTeaEmail(tkTeacher.getTeaEmail());
        teacherInfoVo.setTeaGender(tkTeacher.getTeaGender());
        teacherInfoVo.setTeaInstitute(tkTeacher.getTeaInstitute());
        teacherInfoVo.setTeaMajor(tkTeacher.getTeaMajor());
        teacherInfoVo.setTeaTel(tkTeacher.getTeaTel());

        return teacherInfoVo;
    }

    @Override
    public StudentInfoVo getInfoStudent(Long passportId) {

        TkStudentExample tkStudentExample=new TkStudentExample();
        TkStudentExample.Criteria criteria = tkStudentExample.createCriteria();
        criteria.andPassportIdEqualTo(passportId);
        List<TkStudent> tkStudentList=tkStudentMapper.selectByExample(tkStudentExample);
        if(tkStudentList.size()==0)return null;

        TkStudent tkStudent=tkStudentList.get(0);
        //仅保留基本信息
        StudentInfoVo studentInfoVo=new StudentInfoVo();
        studentInfoVo.setUserName(tkStudent.getStuName());
        studentInfoVo.setPassportId(tkStudent.getPassportId());
        studentInfoVo.setUserId(tkStudent.getStudentId());
        studentInfoVo.setStuClassId(tkStudent.getStuClassId());
        studentInfoVo.setStuEmail(tkStudent.getStuEmail());
        studentInfoVo.setStuGender(tkStudent.getStuGender());
        studentInfoVo.setStuInstitute(tkStudent.getStuInstitute());
        studentInfoVo.setStuMajor(tkStudent.getStuMajor());

        return studentInfoVo;
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