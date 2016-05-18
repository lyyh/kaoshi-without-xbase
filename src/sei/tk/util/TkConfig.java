package sei.tk.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by liuruijie on 2016/4/26.
 */
@Component
public class TkConfig {
    //错误码
    public static final Integer SUCCESS=1;//成功的操作
    public static final Integer SYSTEM_ERROR = -2;//系统错误
    public static final Integer INVALID_ACTION = 0;//无效的操作
    public static final Integer NO_LOGIN = -1;//未登录
    public static final Integer DATABASE_ERROR = -5;//数据库错误
    public static final Integer NO_PRIVILEGE = -6;//没有权限

    //一些考试相关的缓存字段名
    public static final String TEST_PAPER_INFO="testpaperInfo";//当前考试的试卷信息
    public static final String TEST_STU_ANSWER="stuAnswer";//当前考试学生答案信息
    public static final String TEST_START_TIME="startTime";//考试开始时间
    public static final String TEST_SCORE="score";//选择判断分数
    public static final String TEST_SCHEDULE_ID="scheduleId";//考试安排编号
    public static final String TEST_IP="ip";//进行考试的ip地址

    //登陆相关角色
    public static final String ROLE_TEACHER="teacher";//老师
    public static final String ROLE_STUDENT="student";//学生
    public static final String ROLE_ADMIN="admin";//管理员

    //账号规则
    public static String teacherCodeRegexRule;//教师工号规则
    public static String studentCodeRegexRule;//学生学号规则

    Properties properties=new Properties();//加载配置文件
    public TkConfig(){
        init();
    }

    private void init(){
        InputStream in=this.getClass().getResourceAsStream("tkConfig.properties");
        try {
            properties.load(in);
            configVar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configVar(){
        teacherCodeRegexRule=properties.getProperty("teacher.code.regexRule");
        studentCodeRegexRule=properties.getProperty("student.code.regexRule");
    }
}
