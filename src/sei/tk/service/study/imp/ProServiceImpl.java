package sei.tk.service.study.imp;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkProgressMapper;
import sei.tk.service.dao.mapper.TkStudypageMapper;
import sei.tk.service.dao.mapper.TkSubjectMapper;
import sei.tk.service.dao.model.*;
import sei.tk.service.study.ProService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学习进度实现类
 * Created by liuyanhao on 2016/3/26 0026.
 */
@Service
public class ProServiceImpl implements ProService {
    @Resource
    TkProgressMapper tkProgressMapper;
    @Resource
    TkSubjectMapper tkSubjectMapper;
    @Resource
    TkStudypageMapper tkStudypageMapper;
    /**
     *通过章节id,课程id得到某一章节各个题型数量从而得到总数量
     * @param chapterId
     * @return
     */
    @Override
    public Map<String, Short> getSubTypeNum(Byte chapterId,Short courseId) {
        TkStudypageExample tkStudypageExample = new TkStudypageExample();
        TkStudypageExample.Criteria criteria = tkStudypageExample.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        criteria.andChapterIdEqualTo(chapterId);
        //根据课程id和章节id得到章节题目
        List<TkStudypage> tkStudypages = tkStudypageMapper.selectByExample(tkStudypageExample);
        TkStudypage tkStudypage = tkStudypages.get(0);
        //将章节题型数目存入map
        Map<String,Short> map = new HashMap<>();
        map.put("queChoice",tkStudypage.getQueChoice());
        map.put("queJudge",tkStudypage.getQueJduge());
        map.put("queSolution",tkStudypage.getQueSolution());
        map.put("queBlank",tkStudypage.getQueBlank());
        return map;
    }


    /**
     * 若本章做完，则将本章存入进度(学生id是否提前录入)
     * @param subNum
     * @param stuId
     * @param chapterId
     * @param courseId
     * @return
     */
    @Override
    public String saveChapSch(int subNum,Long stuId,Byte chapterId,Short courseId) {
        Map<String,Short> map = getSubTypeNum(chapterId,courseId);
        int chapterNum = 0;
        //只遍历map的value值得到章节规定的题目数
        for(Short num:map.values()){
            chapterNum+=num;
        }
        if(subNum==chapterNum){
            TkProgressExample tkProgressExample = new TkProgressExample();
            TkProgressExample.Criteria criteria = tkProgressExample.createCriteria();
            criteria.andStuIdEqualTo(stuId);
            criteria.andCourseIdEqualTo(courseId);
            //根据学生id和学生学习的科目找出他的学习进度
            List<TkProgress> tkProgresses =tkProgressMapper.selectByExample(tkProgressExample);
            TkProgress tkProgress = tkProgresses.get(0);
            //得到学生进度id和之前完成的章节
            Long proUuid = tkProgress.getProUuid();
            String proCha = tkProgress.getProChapter();
            if(proCha.indexOf(chapterId.toString())!=-1){
                return "学习进度保存成功";
            }
            //对章节数量进行追加
            StringBuffer stringBuffer = new StringBuffer(proCha);
            stringBuffer.append(chapterId+",");
            String str = stringBuffer.toString();
            //覆盖之前的章节进度
            TkProgress tkProgress1 = new TkProgress();
            tkProgress1.setProUuid(proUuid);
            tkProgress1.setCourseId(courseId);
            tkProgress1.setProChapter(str);
            //保存章节进度和总的做题进度
            if(tkProgressMapper.updateByPrimaryKeySelective(tkProgress1)!=0){
                String saveRes = saveTotalSch(str,proUuid,courseId);
                return saveRes;
            }
        }
        return "请完成该章所有科目！";
    }

    /**
     * 根据科目得到所有章节，再根据章节进度分析总进度
     * @param chapSch
     * @param proUuid
     * @param courseId
     * @return
     */
    @Override
    public String saveTotalSch(String chapSch,Long proUuid,Short courseId) {
        TkSubjectExample tkSubjectExample = new TkSubjectExample();
        TkSubjectExample.Criteria criteria = tkSubjectExample.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        //根据courseId得到章节数
        List<TkSubject> tkSubjects = tkSubjectMapper.selectByExample(tkSubjectExample);
        //得到该科目总的章节数
        double totalChapNum = tkSubjects.size();
        //得到记录的章节
        String chapStr[] = chapSch.split(",");
        double proChapNum = chapStr.length;
        //得到总的进度完成情况
        int douRes = (int)(proChapNum/totalChapNum*100);
        //int转byte
        Byte res = Byte.valueOf(Integer.toString(douRes));
        //添加修改条件
        TkProgress tkProgress = new TkProgress();
        tkProgress.setProUuid(proUuid);
        tkProgress.setProCourse(res);
        if(tkProgressMapper.updateByPrimaryKeySelective(tkProgress)!=0){
            return "学习进度保存成功！";
        }
        return "学习进度保存失败！";
    }

    /**
     * 查看总的学习进度
     * @param stuId
     * @param courseId
     * @return
     */
    @Override
    public List<Integer> showChapPro(Long stuId, Short courseId) {
        TkProgressExample tkProgressExample = new TkProgressExample();
        TkProgressExample.Criteria criteria = tkProgressExample.createCriteria();
        criteria.andStuIdEqualTo(stuId);
        criteria.andCourseIdEqualTo(courseId);
        //根据学生id和课程id找到学生的学习进度
        List<TkProgress> tkProgresses = tkProgressMapper.selectByExample(tkProgressExample);
        TkProgress tkProgress = tkProgresses.get(0);
        String chapPro = tkProgress.getProChapter();
        String[] str = chapPro.split(",");
        List<Integer> list = new ArrayList<>();
        //将学生做的章节提取出来
        for(String chapStr:str){
            Integer integer = Integer.valueOf(chapStr);
            list.add(integer);
        }
        return list;
    }

    /**
     * 返回总的学习进度
     * @param stuId
     * @param courseId
     * @return
     */
    @Override
    public String showTotalPro(Long stuId, Short courseId) {
        TkProgressExample tkProgressExample = new TkProgressExample();
        TkProgressExample.Criteria criteria = tkProgressExample.createCriteria();
        criteria.andStuIdEqualTo(stuId);
        criteria.andCourseIdEqualTo(courseId);
        //根据学生id和课程id找到学生的学习进度
        List<TkProgress> tkProgresses = tkProgressMapper.selectByExample(tkProgressExample);
        if(tkProgresses.size()==0){
            return "没有学习记录";
        }
        TkProgress tkProgress = tkProgresses.get(0);
        //将byte转int
        Byte proByte = tkProgress.getProCourse();
        String totalPro = proByte.toString();
        return totalPro;
    }
}
