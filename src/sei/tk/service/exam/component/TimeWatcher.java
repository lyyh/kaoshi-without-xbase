package sei.tk.service.exam.component;

/**
 * Created by liuruijie on 2016/4/7.
 * 考试时间监控者
 */
public interface TimeWatcher {
    long startTime();//返回考试开始时间
    long oddTime(long now,long last,long start);//返回考试剩余时间
}
