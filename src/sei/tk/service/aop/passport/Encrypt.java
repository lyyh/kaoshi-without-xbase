package sei.tk.service.aop.passport;

import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkStudentMapper;
import sei.tk.service.dao.mapper.TkTeacherMapper;
import sei.tk.service.dao.model.TkStudent;
import sei.tk.service.dao.model.TkStudentExample;
import sei.tk.service.dao.model.TkTeacher;
import sei.tk.service.dao.model.TkTeacherExample;
import sei.tk.service.dao.model.vo.passport.Passport;
import sei.tk.util.exception.TKException;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by liuruijie on 2016/3/25.
 * 各种service之前的加密操作
 */
@Aspect
@Service
public class Encrypt {

    @Resource
    TkStudentMapper tkStudentMapper;
    @Resource
    TkTeacherMapper tkTeacherMapper;

    @Around("execution(* sei.tk.service.passport.LoginService.*(..))")
    public Object loginEncrypt(ProceedingJoinPoint joinPoint){//登陆加密校验
        Object[] args=joinPoint.getArgs();//获取参数
        Passport passport= (Passport) args[0];
        if(passport.getPassportCode()==null||passport.getPassword()==null)return null;
        //查询创建时间
//        TkStudentExample tkStudentExample=new TkStudentExample();
//        TkStudentExample.Criteria criteria = tkStudentExample.createCriteria();
//        criteria.andStuCodeEqualTo(passport.getPassportCode());
//        List<TkStudent> tkStudentList=tkStudentMapper.selectByExample(tkStudentExample);
//        if(tkStudentList==null||tkStudentList.size()==0)return null;
//        TkStudent tkStudent = tkStudentList.get(0);
//        //以时间作为盐进行md5加密
//        passport.setPassword(DigestUtils.md5Hex(passport.getPassword()+tkStudent.getStuCreatetime().getTime()));
//        passport.setPassword(DigestUtils.md5Hex(passport.getPassword()));
        try {
            return joinPoint.proceed();
        }catch (TKException e){
            throw e;
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Around("execution(* sei.tk.service.passport.impl.PassportImpl.addStudent(..))")
    public Object addEcryptStudent(ProceedingJoinPoint joinPoint){//添加学生时对密码加密
        Object[] args = joinPoint.getArgs();
        TkStudent tkStudent= (TkStudent) args[0];
        if(tkStudent==null
                ||tkStudent.getStuPassword()==null||tkStudent.getStuPassword().equals("")
                ||tkStudent.getStuCode()==null||tkStudent.getStuCode().equals(""))return null;
        //以时间作为盐
//        String salt=""+new Date().getTime();
//        salt=salt.substring(0,salt.length()-3);
//        salt=salt+"000";
//        tkStudent.setStuCreatetime(new Date(Long.parseLong(salt)));
//        tkStudent.setStuPassword(DigestUtils.md5Hex(tkStudent.getStuPassword()+salt));
//        tkStudent.setStuPassword(DigestUtils.md5Hex(tkStudent.getStuPassword()));
        try {
            return joinPoint.proceed();
        }catch (TKException e){
            throw e;
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Around("execution(* sei.tk.service.passport.impl.PassportImpl.addTeacher(..))")
    public Object addEcryptTeacher(ProceedingJoinPoint joinPoint){//添加教师时对密码进行加密
        Object[] args = joinPoint.getArgs();
        TkTeacher tkTeacher= (TkTeacher) args[0];
        if(tkTeacher==null
                ||tkTeacher.getTeaCode()==null||tkTeacher.getTeaCode().equals("")
                ||tkTeacher.getTeaPassword()==null||tkTeacher.getTeaPassword().equals(""))
            return null;
        //以时间作为盐
//        String salt=""+new Date().getTime();
//        salt=salt.substring(0,salt.length()-3);
//        salt=salt+"000";
//        tkTeacher.setTeaCreatetime(new Date(Long.parseLong(salt)));
//        tkTeacher.setTeaPassword(DigestUtils.md5Hex(tkTeacher.getTeaPassword()+salt));
//        tkTeacher.setTeaPassword(DigestUtils.md5Hex(tkTeacher.getTeaPassword()));
        try {
            return joinPoint.proceed();
        }catch (TKException e){
            throw e;
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Around("execution(* sei.tk.service.passport.impl.PassportImpl.modifyPasswordStudent(..))")
    public Object modifyPasswordEncryptStudent(ProceedingJoinPoint joinPoint){//学生修改密码前的加密
        Object[] args = joinPoint.getArgs();
        Long passportId= (Long) args[0];
        String oldpsw= (String) args[1];
        String newpsw= (String) args[2];
        if(oldpsw==null||newpsw==null)return null;
        //将密码加密后再进入service层
//        TkStudent tkStudent=tkStudentMapper.selectByPrimaryKey(passportId);
//        oldpsw=DigestUtils.md5Hex(oldpsw+tkStudent.getStuCreatetime().getTime());
//        newpsw=DigestUtils.md5Hex(newpsw+tkStudent.getStuCreatetime().getTime());
//        oldpsw=DigestUtils.md5Hex(oldpsw);
//        newpsw=DigestUtils.md5Hex(newpsw);
        args[1]=oldpsw;
        args[2]=newpsw;

        try {
            return joinPoint.proceed(args);
        }catch (TKException e){
            throw e;
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Around("execution(* sei.tk.service.passport.impl.PassportImpl.modifyPasswordTeacher(..))")
    public Object modifyPasswordTeacher(ProceedingJoinPoint joinPoint){//修改密码前的加密
        Object[] args = joinPoint.getArgs();
        Long passportId= (Long) args[0];
        String oldpsw= (String) args[1];
        String newpsw= (String) args[2];
        if(oldpsw==null||newpsw==null)return null;
        //先加密在进入service层
//        TkTeacher tkTeacher=tkTeacherMapper.selectByPrimaryKey(passportId);
//        oldpsw=DigestUtils.md5Hex(oldpsw+tkTeacher.getTeaCreatetime().getTime());
//        newpsw=DigestUtils.md5Hex(newpsw+tkTeacher.getTeaCreatetime().getTime());
//        oldpsw=DigestUtils.md5Hex(oldpsw);
//        newpsw=DigestUtils.md5Hex(newpsw);
        args[1]=oldpsw;
        args[2]=newpsw;
        try {
            return joinPoint.proceed(args);
        }catch (TKException e){
            throw e;
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
