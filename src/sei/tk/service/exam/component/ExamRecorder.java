package sei.tk.service.exam.component;

import sei.tk.service.exam.ExamMapForRedis;

/**
 * Created by liuruijie on 2016/4/8.
 * 考试信息记录者
 */
public interface ExamRecorder {
    void recordScore(long stuId,ExamMapForRedis examMapForRedis);//记录分数
    void recordTest(long stuId,ExamMapForRedis examMapForRedis);//记录考试
    void recordAnswer(long stuId,ExamMapForRedis examMapForRedis);//记录学生答案
}
