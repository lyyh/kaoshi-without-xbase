package sei.tk.service.study.imp;
import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkProgressMapper;
import sei.tk.service.dao.mapper.TkQuecollMapper;
import sei.tk.service.dao.mapper.TkStudypageMapper;
import sei.tk.service.dao.mapper.TkSubjectMapper;
import sei.tk.service.dao.model.*;
import sei.tk.service.dao.model.vo.study.SubjectInfoVo;
import sei.tk.service.study.ChapterStudyService;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 学生章节练习实现类
 * Created by liuyanhao on 2016/3/21 0021.
 */
@Service
public class ChapterStudyServiceImpl implements ChapterStudyService {
    @Resource
    TkSubjectMapper tkSubjectMapper;
    @Resource
    TkQuecollMapper tkQuecollMapper;
    @Resource
    TkProgressMapper tkProgressMapper;
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
     * 根据课程id，章节id，题型数量得到章节题目（用于一次只显示一道题）
     * @param courseId
     * @param selectNum 选择题数量
     * @param blankNum 填空题数量
     * @param checkNum 判断题数量
     * @param explainNum 解答题数量
     * @return
     */
    @Override
    public List<TkSubject> initSubject(HttpSession session,Short courseId,Byte chapterId,Integer selectNum, Integer
            blankNum, Integer checkNum, Integer explainNum) {
        TkSubjectExample tkSubjectExample = new TkSubjectExample();
        TkSubjectExample.Criteria criteria = tkSubjectExample.createCriteria();
        criteria.andChapterIdEqualTo(chapterId);
        criteria.andCourseIdEqualTo(courseId);
        //找出课程和章节符合的前100道题
        List<TkSubject> list = tkSubjectMapper.selectByExample(tkSubjectExample);
        //打乱list集合的顺序
        Collections.shuffle(list);
        List<TkSubject> subjectList = new ArrayList<>();
        int sNum = 0;
        int bNum = 0;
        int cNum = 0;
        int eNum = 0;
        //将符合条件的题目放在subjectList中
        for (TkSubject tkSubject : list) {
            //1选择题，2判断题，3填空题，4解答题
            if (tkSubject.getQuetypeId()==1 && sNum < selectNum) {
                subjectList.add(tkSubject);
                ++sNum;
            } else if (tkSubject.getQuetypeId()==2 && cNum < checkNum) {
                subjectList.add(tkSubject);
                ++cNum;
            } else if (tkSubject.getQuetypeId()==3 && bNum < blankNum) {
                subjectList.add(tkSubject);
                ++bNum;
            } else if (tkSubject.getQuetypeId()==4 && eNum < explainNum) {
                subjectList.add(tkSubject);
                ++eNum;
            }
        }
        return subjectList;
    }

    /**
     * 通过课程id和章节id一次初始化所有题目
     * @param courseId
     * @param chapterId
     * @return
     */
    @Override
    public List<TkSubject> initSubject(Short courseId, Byte chapterId) {
        Map<String,Short> map = getSubTypeNum(chapterId,courseId);
        TkSubjectExample tkSubjectExample = new TkSubjectExample();
        TkSubjectExample.Criteria criteria = tkSubjectExample.createCriteria();
        criteria.andChapterIdEqualTo(chapterId);
        criteria.andCourseIdEqualTo(courseId);
        //找出课程和章节符合的前100道题
        List<TkSubject> list = tkSubjectMapper.selectByExample(tkSubjectExample);
        //打乱list集合的顺序
        Collections.shuffle(list);
        List<TkSubject> subjectList = new ArrayList<>();
        int sNum = 0;
        int bNum = 0;
        int cNum = 0;
        int eNum = 0;
        //将符合条件的题目放在subjectList中
        for (TkSubject tkSubject : list) {
            //1选择题，2判断题，3填空题，4解答题
            if (tkSubject.getQuetypeId()==1 && sNum < map.get("queChoice")) {
                subjectList.add(tkSubject);
                ++sNum;
            } else if (tkSubject.getQuetypeId()==2 && cNum < map.get("queJudge")) {
                subjectList.add(tkSubject);
                ++cNum;
            } else if (tkSubject.getQuetypeId()==3 && bNum < map.get("queBlank")) {
                subjectList.add(tkSubject);
                ++bNum;
            } else if (tkSubject.getQuetypeId()==4 && eNum < map.get("queSolution")) {
                subjectList.add(tkSubject);
                ++eNum;
            }
        }
        return subjectList;
    }

    /**
     * 根据题目类型对题目进行排序
     * @param tkSubjects
     * @return
     */
    @Override
    public List<SubjectInfoVo> sortSubjet(List<TkSubject> tkSubjects) {
        SubjectInfoVo sInfo = null;
        //选择题
        List<SubjectInfoVo> list1 = new ArrayList<>();
        //判断题
        List<SubjectInfoVo> list2 = new ArrayList<>();
        //填空题
        List<SubjectInfoVo> list3 = new ArrayList<>();
        //解答题
        List<SubjectInfoVo> list4 = new ArrayList<>();
        //对每道题目进行加工
         for(TkSubject tkSubject:tkSubjects){
             if (tkSubject.getQuetypeId()==1) {
                 sInfo = getSubInfoVo(tkSubject);
                 list1.add(sInfo);
             } else if (tkSubject.getQuetypeId()==2) {
                 sInfo = getSubInfoVo(tkSubject);
                 list2.add(sInfo);
             } else if (tkSubject.getQuetypeId()==3) {
                 sInfo = getSubInfoVo(tkSubject);
                 list3.add(sInfo);
             } else if (tkSubject.getQuetypeId()==4) {
                 sInfo = getSubInfoVo(tkSubject);
                 list4.add(sInfo);
             }
         }
        //按顺序拼接题目
        list1.addAll(list2);
        list1.addAll(list3);
        list1.addAll(list4);
        int n=1;
        //设置题目编号
        for(SubjectInfoVo subjectInfoVo:list1){
            subjectInfoVo.setNumber(n++);
        }
        return list1;
    }

    /**
     * 根据题目编号显示题目（用于一次只显示一道题）
     * @param subjectInfoVos
     * @param subjectNum
     * @return
     */
    @Override
    public SubjectInfoVo getSubject(List<SubjectInfoVo> subjectInfoVos,int subjectNum) {
        try {
            return subjectInfoVos.get(subjectNum - 1);
        }catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    /**
     * 返回每一题的结果(返回正确答案和解析）
     * @param stuAnswer
     * @param subId
     * @return
     */
    @Override
    public Map<String, String> subjecRes(String stuAnswer, Long subId) {
        //根据题目id找到题目内容，从中提取题目答案和解析
        TkSubjectWithBLOBs tkSubjectWithBLOBs = tkSubjectMapper.selectByPrimaryKey(subId);
        String subAnw = tkSubjectWithBLOBs.getSubjectAnswer();
        String subSolution = tkSubjectWithBLOBs.getSubjectSolution();
        Map<String, String> map = new HashMap<>();
            if (stuAnswer.trim().equals(subAnw)) {
                map.put("res", "答案正确！");
                map.put("rightAnw", tkSubjectWithBLOBs.getSubjectAnswer());
                map.put("solution",subSolution);
                return map;
            } else {
                map.put("res", "答案错误！");
                map.put("rightAnw", tkSubjectWithBLOBs.getSubjectAnswer());
                map.put("solution",subSolution);
                return map;
            }
    }

    /**
     * 得到章节题目数量
     * @param subjectInfoVos
     * @return
     */
    @Override
    public int getSubNum(List<SubjectInfoVo> subjectInfoVos) {
        return subjectInfoVos.size();
    }

    /**
     * 将题目转化成可以显示的题目
     *
     * @return
     */
    public SubjectInfoVo getSubInfoVo(TkSubject tkSubject) {
        TkSubjectWithBLOBs tkSubjectWithBLOBs = null;
        if (tkSubject.getQuetypeId() == 1) {
            SubjectInfoVo sInfo = new SubjectInfoVo();
            //得到题目编号
            sInfo.setSubjectId(tkSubject.getSubjectId());
            sInfo.setType(1);
            tkSubjectWithBLOBs = tkSubjectMapper.selectByPrimaryKey(tkSubject.getSubjectId());
            //拆分选择题
            String str[] = tkSubjectWithBLOBs.getSubjectOption().split("@#%");
            //将String数组转为list中
            List<String> strList = Arrays.asList(str);
            sInfo.setSubjectOptions(strList);
            //得到题干
            sInfo.setSubjectName(tkSubjectWithBLOBs.getSubjectName());
            //得到题目解析
            sInfo.setSubjectSolution(tkSubjectWithBLOBs.getSubjectSolution());
            return sInfo;
        } else if (tkSubject.getQuetypeId() == 2) {
            SubjectInfoVo sInfo = new SubjectInfoVo();
            sInfo.setSubjectId(tkSubject.getSubjectId());
            sInfo.setType(2);
            tkSubjectWithBLOBs = tkSubjectMapper.selectByPrimaryKey(tkSubject.getSubjectId());
            //得到题干
            sInfo.setSubjectName(tkSubjectWithBLOBs.getSubjectName());
            //得到题目解析
            sInfo.setSubjectSolution(tkSubjectWithBLOBs.getSubjectSolution());
            return sInfo;
        } else if (tkSubject.getQuetypeId() == 3) {
            SubjectInfoVo sInfo = new SubjectInfoVo();
            sInfo.setSubjectId(tkSubject.getSubjectId());
            sInfo.setType(3);
            tkSubjectWithBLOBs = tkSubjectMapper.selectByPrimaryKey(tkSubject.getSubjectId());
            //得到题干
            sInfo.setSubjectName(tkSubjectWithBLOBs.getSubjectName());
            //分割填空题
            String strings[] = tkSubjectWithBLOBs.getSubjectAnswer().split("@#%");
            sInfo.setBlankamount(strings.length);
            //得到题目解析
            sInfo.setSubjectSolution(tkSubjectWithBLOBs.getSubjectSolution());
            return sInfo;
        } else if (tkSubject.getQuetypeId() == 4) {
            SubjectInfoVo sInfo = new SubjectInfoVo();
            sInfo.setSubjectId(tkSubject.getSubjectId());
            sInfo.setType(4);
            tkSubjectWithBLOBs = tkSubjectMapper.selectByPrimaryKey(tkSubject.getSubjectId());
            //得到题干
            sInfo.setSubjectName(tkSubjectWithBLOBs.getSubjectName());
            //得到题目解析
            sInfo.setSubjectSolution(tkSubjectWithBLOBs.getSubjectSolution());
            return sInfo;
        }
        return null;
    }

    /**
     * 创建章节学习内容
     * @param tkStudypage
     * @return
     */
    @Override
    public boolean creStudyContent(TkStudypage tkStudypage) {
        return false;
    }

    /**
     * 删除章节学习内容
     * @param studypageId
     * @return
     */
    @Override
    public boolean delStudyContent(Long studypageId) {
        return false;
    }

    /**
     * 修改章节学习内容
     * @param tkStudypage
     * @return
     */
    @Override
    public boolean updStudyContent(TkStudypage tkStudypage) {
        return false;
    }

    /**
     *
     * @param courseId
     * @return
     */
    @Override
    public boolean sekStudyContent(Short courseId) {
        return false;
    }

}
