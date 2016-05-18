package sei.tk.service.study;

import java.util.List;
import java.util.Map;

/**
 * 学习进度接口类
 * Created by liuyanhao on 2016/3/26 0026.
 */
public interface ProService {
    //通过章节id,课程id得到某一章节各个题型数量从而得到总数量
    public Map<String, Short> getSubTypeNum(Byte chapterId,Short courseId);
    //若本章做完，则将本章存入进度
    public String saveChapSch(int subNum,Long stuId,Byte chapterId,Short courseId);
    //根据章节进度得到总进度
    public String saveTotalSch(String chapSch,Long proUuid,Short courseId);
    //查看完成了多少章节
    public List<Integer> showChapPro(Long stuId,Short courseId);
    //查看总的学习进度
    public String showTotalPro(Long stuId,Short courseId);
}
