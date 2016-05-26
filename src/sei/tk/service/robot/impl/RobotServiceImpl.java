package sei.tk.service.robot.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.*;
import sei.tk.service.dao.model.*;
import sei.tk.service.dao.model.vo.robot.Question;
import sei.tk.service.dao.model.vo.robot.SubNum;
import sei.tk.service.robot.RobotService;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by ywl on 2016/3/15.
 */
@Service
public class RobotServiceImpl implements RobotService {
    @Resource
    TkSubjectMapper tkSubjectMapper;
    @Resource
    TkMkpaperruleMapper tkMkpaperruleMapper;
    @Resource
    TkMkpaperMapper tkMkpaperMapper;
    @Resource
    TkTestpaperMapper tkTestpaperMapper;
    @Resource
    TkTestsubjectMapper tkTestsubjectMapper;


    public int insertTkMkpaperrule(TkMkpaperrule tkMkpaperrule) {
        return tkMkpaperruleMapper.insertSelective(tkMkpaperrule);
    }

    public TkMkpaperrule getOneTkMkpaperrule(TkMkpaperrule tkMkpaperrule) {

        return tkMkpaperruleMapper.selectByPrimaryKey(tkMkpaperrule.getMkpaperruleId());
    }

    public int updateTkMkpaperrule(TkMkpaperrule tkMkpaperrule) {
        return tkMkpaperruleMapper.updateByPrimaryKey(tkMkpaperrule);

    }

    public List<SubNum> getSubNum(TkSubject tkSubject) {
        List<SubNum> subNums = tkSubjectMapper.selectandGetNum(tkSubject);
        List<SubNum> subNums1 = new ArrayList<SubNum>();
        if (subNums.size() == 3)
            return subNums;
        else if (subNums.size() == 2) {
            int a = subNums.get(0).getLevelId();
            int b = subNums.get(1).getLevelId();
            if (a == 1 && b == 2) {
                subNums1.add(subNums.get(0));
                subNums1.add(subNums.get(1));
                subNums1.add(new SubNum(subNums.get(0).getQuetypeId(), subNums.get(0).getknopointId(), (byte) 3, 0));
            } else if (a == 1 && b == 3) {
                subNums1.add(subNums.get(0));
                subNums1.add(new SubNum(subNums.get(0).getQuetypeId(), subNums.get(0).getknopointId(), (byte) 2, 0));
                subNums1.add(subNums.get(1));
            } else {
                subNums1.add(new SubNum(subNums.get(0).getQuetypeId(), subNums.get(0).getknopointId(), (byte) 1, 0));
                subNums1.add(subNums.get(0));
                subNums1.add(subNums.get(1));
            }
            return subNums1;
        } else if (subNums.size() == 1) {
            int a = subNums.get(0).getLevelId();
            if (a == 1) {
                subNums1.add(subNums.get(0));
                subNums1.add(new SubNum(subNums.get(0).getQuetypeId(), subNums.get(0).getknopointId(), (byte) 2, 0));
                subNums1.add(new SubNum(subNums.get(0).getQuetypeId(), subNums.get(0).getknopointId(), (byte) 3, 0));
            } else if (a == 2) {
                subNums1.add(new SubNum(subNums.get(0).getQuetypeId(), subNums.get(0).getknopointId(), (byte) 1, 0));
                subNums1.add(subNums.get(0));
                subNums1.add(new SubNum(subNums.get(0).getQuetypeId(), subNums.get(0).getknopointId(), (byte) 3, 0));
            } else {
                subNums1.add(new SubNum(subNums.get(0).getQuetypeId(), subNums.get(0).getknopointId(), (byte) 1, 0));
                subNums1.add(new SubNum(subNums.get(0).getQuetypeId(), subNums.get(0).getknopointId(), (byte) 2, 0));
                subNums1.add(subNums.get(0));
            }
            return subNums1;
        } else {
            subNums1.add(new SubNum(tkSubject.getQuetypeId(), tkSubject.getKnopointId(), (byte) 1, 0));
            subNums1.add(new SubNum(tkSubject.getQuetypeId(), tkSubject.getKnopointId(), (byte) 2, 0));
            subNums1.add(new SubNum(tkSubject.getQuetypeId(), tkSubject.getKnopointId(), (byte) 3, 0));
            return subNums1;
        }
    }

    public int getQueAmount(int courseId, int quetypeId, int chapterId, int levelId) {
        TkSubjectExample tkSubjectExample = new TkSubjectExample();
        TkSubjectExample.Criteria subjectCriteria = tkSubjectExample.createCriteria();
        subjectCriteria.andChapterIdEqualTo((byte) chapterId);
        subjectCriteria.andCourseIdEqualTo((short) courseId);
        subjectCriteria.andQuetypeIdEqualTo((short) quetypeId);
        subjectCriteria.andLevelIdEqualTo((byte) levelId);
        tkSubjectMapper.selectByExample(tkSubjectExample);
        return tkSubjectMapper.countByExample(tkSubjectExample);
    }

    public long insertTkTestpaperAndgettestpaperId(long mkpareId,Long ppassportId) {
        TkTestpaper tkTestpaper = new TkTestpaper();
        tkTestpaper.setMkpaperId(mkpareId);
        tkTestpaper.setPpassportId(ppassportId);
        tkTestpaperMapper.insertSelective(tkTestpaper);
        return tkTestpaperMapper.selectlastinsertid();
    }

    public void insertTkTestsubject(TkTestsubject tkTestsubjects) {
        tkTestsubjectMapper.insertSelective(tkTestsubjects);
    }

    public int getcourseId(TkMkpaperrule tkMkpaperrule) {
        return tkMkpaperMapper.selectCourseid(tkMkpaperrule.getMkpaperId());
    }

    public List<TkMkpaper> getTkMkmaper() {
        TkMkpaperExample tkMkpaperExample = new TkMkpaperExample();
        List<TkMkpaper> tkMkpapers = tkMkpaperMapper.selectByExample(tkMkpaperExample);
        return tkMkpapers;
    }

    public TkMkpaperrule[] getTkMkpaperrules(long mkpaperId) {
        TkMkpaperruleExample tkMkpaperruleExample = new TkMkpaperruleExample();
        TkMkpaperruleExample.Criteria criteria = tkMkpaperruleExample.createCriteria();
        criteria.andMkpaperIdEqualTo(mkpaperId);
        List<TkMkpaperrule> tkMkpaperrules = tkMkpaperruleMapper.selectByExample(tkMkpaperruleExample);
        if (tkMkpaperrules.size() == 0)
            return null;
        else
            return tkMkpaperrules.toArray(new TkMkpaperrule[tkMkpaperrules.size()]);
    }


    public Long insertTkMkpaerAndgetmkpareId(TkMkpaper tkMkpaper) {
        tkMkpaperMapper.insertSelective(tkMkpaper);
        return tkMkpaperMapper.selectlastinsertid();

    }

    public List<TkSubject> robotmakepaper(LinkedList<Question> questions) {
        List<TkSubject> test = new LinkedList<>();
        int i = 0;
        Question que = new Question(questions.get(0).getCourseId(), questions.get(0).getQuetypeId(), questions.get(0).getKnopointId(), questions.get(0).getLevelId(), 0, null);
        for (Question quetion : questions) {
            LinkedList<Integer> a=quetion.getTargetList();
            for(int j=0;j<quetion.getAmount();j++) {
                que.setAmount(a.get(j));
                test.addAll(tkSubjectMapper.selectRandSubject(que));
            }
            i++;
        }
        return test;
    }

    public LinkedList<Question> getmakePaper(TkMkpaperrule[] tkMkpaperrules) {
         /*
        * 每一个制卷规则记录对应一种题型
        * 有多少个题型就有多少条制卷规则记录
        * */

        /*
        * 说明：
        * 常量：
        * final int MAX_LEVEL 最大难度
        * Integer[][] levelList 难度列表 [难度等级][该等级下的难度]
        *
        * 变量：
        * int courseId 课程编号
        * int levelId 难度等级
        *
        * int typeAmount 题型总数
        * int typeAmount5 题型总数
        * int knopointAount (该题型下得而)知识点总数
        * int levelAmount 难度总数
        * int[] questionAmout 需要的题目数量
        *
        * int levelTemp 临时变量 当前难度
        *
        * int typeCount 题型计数器
        * int knopointCount 知识点计数器
        * int levelCount 难度计数器
        *
        * LinkedList<Question> questionList 这个就是要返回的题目列表
        * LinkedList<SubNum> subNumList 题目数据列表
        * List<SubNum> subNumListTemp 临时用来保存从数据库获取到的题目数量列表
        * SubNum[] subNums 题目数量数组
        * Long[][] knopointList 知识点列表
        *
        * */
        /*
        * 本算法设计之初是按照讨论结果，将所有序号从0开始编号
        * 现在为了配合前端改成从1开始编号
        * 所有可能有未知bug
        * 祝以后维护这个的人好运，以上
        * */
        final int MAX_LEVEL = 3;//最大难度
        Integer[][] levelList = {{}, {1, 1, 1, 1, 2, 3}, {1, 1, 1, 2, 3}, {1, 1, 2, 3}};//难度数组，每种难度对应的题目数，1→简单，2→中等，3→困难

        int courseId;//课程编号
        int[] levelId;//难度等级

        int typeAmount;//题型总数
        int knopointAount;//(该题型下得而)知识点总数
        int levelAmount;//难度总数
        int[] questionAmout;//需要的题目数量

        int typeTemp;
        int levelTemp;//临时变量 当前难度

        int typeCount;//题型计数器
        int knopointCount;//知识点计数器
        int levelCount;//难度计数器

        int[] typeList;
        LinkedList<Question> questionList;//这个就是要返回的题目列表
        LinkedList<SubNum> subNumList;//题目数据列表
        List<SubNum> subNumListTemp;//临时用来保存从数据库获取到的题目数量列表
        SubNum[] subNums;//题目数量数组
        Long[][] knopointList;//知识点列表

        /*==================================================================================*/

        Arrays.sort(tkMkpaperrules, new Comparator<TkMkpaperrule>() {//讲tkMkpaperrule以题目分数降序排列
            @Override
            public int compare(TkMkpaperrule tkMkpaperrule, TkMkpaperrule t1) {
                //return tkMkpaperrule.getMkpaperruleScore() >= t1.getMkpaperruleScore() ? -1 : 1;//降序
                return tkMkpaperrule.getQuetypeId() >= t1.getQuetypeId() ? 1 : -1;//升序
            }
        });//以题型id升序排序

        typeAmount = tkMkpaperrules.length + 1;//获取题型数量
        courseId = getcourseId(tkMkpaperrules[0]);//获取课程id
        levelId = new int[typeAmount];

        levelAmount = MAX_LEVEL;//设置难度数量

        typeList = new int[typeAmount];
        questionAmout = new int[typeAmount];

        subNumList = new LinkedList<>();
        knopointList = new Long[typeAmount][];
        questionList = new LinkedList<>();

        for (int i = 1, tkMkpaperrulesCounter = 0; i < typeAmount; i++, tkMkpaperrulesCounter++) {
            typeList[i] = tkMkpaperrules[tkMkpaperrulesCounter].getQuetypeId();
            questionAmout[i] = tkMkpaperrules[tkMkpaperrulesCounter].getMkpaperruleAmount();//获取题目数量
            levelId[i] = tkMkpaperrules[tkMkpaperrulesCounter].getLevelId() <= MAX_LEVEL ? tkMkpaperrules[tkMkpaperrulesCounter].getLevelId() : MAX_LEVEL;
            String[] temp = tkMkpaperrules[tkMkpaperrulesCounter].getKnopointId().split(",");//获取知识点字符串
            knopointList[i] = new Long[temp.length];
            for (int j = 0; j < temp.length; j++) {
                //将tkMkpaperrules的知识点ID字符串转化成数组
                knopointList[i][j] = Long.parseLong(temp[j]);
                //通过课程id,题型id，知识点id 得到该题型该知识点每种难度的题目数
                subNumListTemp = getSubNum(new TkSubject((short) courseId, knopointList[i][j], (short) typeList[i]));//临时保存一下
                for (SubNum subNum : subNumListTemp) {
                    subNum.setNumOfTotal(subNum.getNum());
                    subNumList.add(subNum);//把subNumListTemp合并至subNumList
                }
            }
            knopointList[i] = this.randomSort(knopointList[i]);//打乱知识点
        }
        subNums = subNumList.toArray(new SubNum[subNumList.size()]);//转化成数组
//        for (int i = 0; i < levelList.length; i++) {
//            for (int j = 0, len = levelList[i].length; j < len; j++) {
//                levelList[levelId[i]] = this.randomSort(levelList[levelId[i]]);//打乱难度
//            }
//        }


        for (int i = 1; i < typeAmount && questionAmout[i] != 0; ) {//外层是用来遍历题型的
            knopointAount = knopointList[i].length;//获取该题型下知识点数量
            for (int j = 1; j <= knopointAount && questionAmout[i] != 0; j++) {//中层遍历知识点
                for (int k = 1; k <= levelAmount && questionAmout[i] != 0; k++) {//内层遍历难度
                    /*
                    * 这个地方，是为了将题型、知识点和难度的计数器和其本身分离
                    * 底下有一个题目数量检查，如果当前条件下题库中的题目数量不足，则有可能造成计数器被更改，所以用临时计数器保存一下
                    * */
                    typeCount = i;
                    knopointCount = j;
                    levelCount = k;
                    levelTemp = levelList[levelId[i]][levelCount];//这个是当前难度，要通过难度计数器来获取当前难度
                    //另外，这的难度只是为了查询剩余题数，记录的还是难度计数器（不是当前难度!）
                    //不然底下将会有数组越界的风险（calcLoaton依赖于题型、知识点和难度计数器（不是对应具体的值！））
                    while (!subNums[calcLocaltion(typeCount, (int) knopointCount, levelTemp, typeAmount, knopointAount, levelAmount)].isAmountLeft()) {//判断题目数量是否足够
                        /*
                        * 这好像有点问题
                        * */
                        levelCount = ((levelCount + 1) % levelAmount) + 1;//若当前题型下的当前知识点在当前难度的题目数量不足，就查看下一难度有没有剩余题目，若有，则拿来填充
                        levelTemp = levelList[levelId[i]][levelCount];
                        if (levelCount == k) {//同上，若改变难度还是不行，那就改变知识点
                            /*
                            * 这好像也有点问题
                            * */
                            knopointCount = ((knopointCount + 1) % knopointAount) + 1;
                            if (knopointCount == j) {
                                //到这一步就是GG了
                                //会抛出一个异常，然后返回null，机器制卷失败
                                try {

                                    throw new Exception();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println("题目数量不足");
                                    return null;
                                }
                            }
                        }
                    }
                    subNums[calcLocaltion(typeCount, (int) knopointCount, levelTemp, typeAmount, knopointAount, levelAmount)].takeSubject();//打个标记，代表取了一道题，记录下对应的计数器
                    questionAmout[i]--;
                }
            }
            if (questionAmout[i] == 0) {
                i++;
            }//如果当前题型要求的题目数取完了
        }
        for (int i = 1; i < typeAmount; i++) {
            knopointAount = knopointList[i].length;//总的知识点数量
            for (int j = 1; j <= knopointAount; j++) {
                for (int k = 1; k <= levelAmount; k++) {
                    int locationTemp = calcLocaltion(i, j, k, typeAmount, knopointAount, levelAmount);//这里才是真正意义上的录题，把刚刚SubNum里面的计数器转化成对应的数
                    if (subNums[locationTemp].getNumOfTaken() != 0) {
                        questionList.add(new Question(courseId, (short) i, knopointList[i][j - 1], (byte) k, subNums[locationTemp].getNumOfTaken(), makeList(subNums[locationTemp].getNumOfTaken(), subNums[locationTemp].getNumOfTotal())));

                    }
                }
            }
        }
        return questionList;
    }

    public int calcLocaltion(int typeId, int knopointLocation, int levelId, int typeAmount, int knopointAmount, int levelAmount) {
        /*
        * typeId：题型编号，从1开始
        * knopointLocation：知识点在knopointList中的位置，从1开始
        * levelId：难度编号 从1开始
        * */
        return (typeId - 1) * (knopointAmount * levelAmount) + (knopointLocation - 1) * levelAmount + (levelId - 1);
    }

    public Long[] randomSort(Long[] arr) {//这个是用来打乱数组的
        List list = Arrays.asList(arr);
        Collections.shuffle(list);
        Long[] temp = (Long[]) list.toArray();
        return temp;
    }

    public Integer[] randomSort(Integer[] arr) {//这个是用来打乱数组的
        List list = Arrays.asList(arr);
        Collections.shuffle(list);
        Integer[] temp = (Integer[]) list.toArray();
        return temp;
    }

    public LinkedList<Integer> makeList(int amount, int totalAmount) {
        Integer[] amountFlag = new Integer[totalAmount];
        for (int i = 1; i <= totalAmount; i++) {
            amountFlag[i - 1] = i-1;
        }
        amountFlag = randomSort(amountFlag);
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < amount; i++) {
            list.add(amountFlag[i]);
        }
        return list;
    }
    public void delete(Long mkpaperId){
        tkMkpaperruleMapper.deletebymkpaperid(mkpaperId);
        tkMkpaperMapper.deleteByPrimaryKey(mkpaperId);
    }
}