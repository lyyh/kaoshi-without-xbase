package sei.tk.service.exam.component.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.exam.component.TimeWatcher;

/**
 * Created by liuruijie on 2016/4/7.
 */
@Service
public class FormalTimeWatcher implements TimeWatcher {
    @Override
    public long startTime() {
        return System.currentTimeMillis();
    }

    @Override
    public long oddTime(long now, long last, long start) {
        return last-(now-start);
    }
}
