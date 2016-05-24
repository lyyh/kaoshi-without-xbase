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
    public static final String SUCCESS="1001";//系统错误
    public static final String NO_LOGIN="2001";//未登陆
    public static final String NO_PRIVILEGE="2002";//没有权限
    public static final String INVALID_ACTION="2003";//无效的操作
    public static final String SYSTEM_ERROR="0001";//系统错误
    //数据库返回码
    public static final String IP_NOT_ACCESS="3001";//IP不能通过
    public static final String MULTI_PLACE_LOGIN="3002";//账号多点登陆
    //权限代码
    public static final String ALL_DATA_PRIVILEGE="1";
    public static final String SELF_DATA_PRIVILEGE="2";
    public static final String OTHER_DATA_PRIVILEGE="3";
    public static final String CHILD_DATA_PRIVILEGE="4";
    public static final String ACCESS_PRIVILEGE="5";
    public static final String DISABLE_PRIVILEGE="6";
    //扩展用角色代码
    public static final int ROLE_TEMP1=10;

    //一些考试相关的缓存字段名
    public static final String TEST_PAPER_INFO="testpaperInfo";//当前考试的试卷信息
    public static final String TEST_STU_ANSWER="stuAnswer";//当前考试学生答案信息
    public static final String TEST_START_TIME="startTime";//考试开始时间
    public static final String TEST_SCORE="score";//选择判断分数
    public static final String TEST_SCHEDULE_ID="scheduleId";//考试安排编号
    public static final String TEST_IP="ip";//进行考试的ip地址

    //登陆相关角色
    public static final int ROLE_TEACHER=1;//老师
    public static final int ROLE_STUDENT=2;//学生
    public static final int ROLE_ADMIN=3;//管理员

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
