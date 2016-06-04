package sei.tk.service.exam.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.ExamMapper;
import sei.tk.service.dao.mapper.TkStudentMapper;
import sei.tk.service.dao.model.TkStudent;
import sei.tk.service.dao.model.TkStudentExample;
import sei.tk.service.exam.AfterExamService;
import sei.tk.service.exam.vo.Analyse;
import sei.tk.service.exam.vo.GenExam;
import sei.tk.service.exam.vo.MyExam;
import sei.tk.util.Page;
import sei.tk.util.TkConfig;
import sei.tk.util.exception.TKException;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

/**
 * Created by liuruijie on 2016/6/2.
 */
@Service
public class AfterExamImpl implements AfterExamService{

    @Resource
    ExamMapper examMapper;
    @Resource
    TkStudentMapper tkStudentMapper;

    @Override
    public List<MyExam> listMyExam(Long passportId) {
        return examMapper.getMyExam(passportId);
    }

    @Override
    public Page listAllGradsByPage(Integer currentPage, Integer rows, GenExam genExam) {
        if(currentPage==null||rows==null){
            genExam.setStart(null);
        }else{
            genExam.setStart((currentPage-1)*rows);
        }

        if(genExam.getStuCode()!=null){
            genExam.setStuCode("%"+genExam.getStuCode()+"%");
        }
        if(genExam.getStuName()!=null){
            genExam.setStuName("%"+genExam.getStuName()+"%");
        }
        genExam.setRows(rows);

        return new Page(examMapper.selectGradeByPage(genExam),examMapper.countGradeByPage(genExam));
    }

    @Override
    public Analyse getAnalyse(Long testpaperId, Long passportId) {
        GenExam genExam = new GenExam();//查询条件

        Analyse analyse = new Analyse();//视图对象

        TkStudentExample tkStudentExample=new TkStudentExample();
        TkStudentExample.Criteria criteria = tkStudentExample.createCriteria();
        criteria.andPassportIdEqualTo(passportId);
        List<TkStudent> students=tkStudentMapper.selectByExample(tkStudentExample);

        Long classId=0L;
        if(students.size() != 0){
            classId = students.get(0).getStuClassId();
        }else{
            throw new TKException(TkConfig.INVALID_ACTION,"没有这个学生");
        }

        genExam.setStuClass(classId);
        genExam.setTestpaperId(testpaperId);
        //查询班级中的所有学生成绩
        List<GenExam> genExamList=examMapper.selectGradeByPage(genExam);

        //班级
        analyse.setKlass(""+students.get(0).getStuClassId());
        //姓名
        analyse.setName(students.get(0).getStuName());
        //班级的挂科率
        analyse.setClassFailPercent(failPercent(genExamList));
        //班级的各项人数
        Integer[] nums=scoreDetails(genExamList);
        analyse.setClassFailNum(nums[0]);//挂科人数
        analyse.setClassPassNum(nums[1]);//过了的人数
        analyse.setClassGreatNum(nums[2]);//优秀的人数
        //在班级中的排名
        analyse.setMyRankInClass(rank(students.get(0).getStudentId(),genExamList));
        //所在班级的平均分
        analyse.setClassAvgScore(avg(genExamList));

//        genExam.setStuClass(genExam.getStuClass() + "%");
        genExam.setStuClass(null);
        genExamList=examMapper.selectGradeByPage(genExam);
        //在年级里的排名
        analyse.setMyRankInGrade(rank(students.get(0).getStudentId(),gradeOfGrade(students.get(0).getStuClassId(),genExamList)));
        //班级在年级中的排名
        analyse.setClassRankInGrade(rankClass(students.get(0).getStuClassId(),avgOfEvery(gradeOfGrade(students.get(0).getStuClassId(),genExamList))));
        //得分
        genExam.setTestpaperId(testpaperId);
        genExam.setStuCode(students.get(0).getStudentId());
        genExamList=examMapper.selectGradeByPage(genExam);
        if(genExamList.size()==0)throw new TKException(TkConfig.INVALID_ACTION,"无法分析");
        analyse.setScore(genExamList.get(0).getScore());

        return analyse;
    }

    private int rank(String studentCode,List<GenExam> genExams){
        int i=0;
        for(GenExam genExam:genExams){
            if(genExam.getStuCode().equals(studentCode)){
                return i+1;
            }
            i++;
        }
        throw new TKException(TkConfig.INVALID_ACTION,"未找到学生");
    }

    private double avg(List<GenExam> genExams){
        double sum=0;
        for(GenExam genExam:genExams){
            sum += genExam.getScore();
        }
        return sum/genExams.size();
    }

    private List<GenExam> gradeOfGrade(Long classId,List<GenExam> genExams){
        String grad = (""+classId).substring(3,5);
        for(int i=0;i<genExams.size();i++){
            GenExam genExam = genExams.get(i);
            String ngrad = (""+genExam.getStuClass()).substring(3,5);
            if(!ngrad.equals(grad)){
                genExams.remove(i);
                i--;
            }
        }
        return genExams;
    }

    private Map<Long,Double> avgOfEvery(List<GenExam> genExams){
        Map<Long,Double> avgMap=new HashMap<>();
        Map<Long,List<GenExam>> classExamMap=new HashMap<>();
        //map
        for(GenExam genExam1:genExams){
            Long classId=genExam1.getStuClass();
            if(classExamMap.containsKey(classId))continue;
            List<GenExam> genExamList=new ArrayList<>();
            for(GenExam genExam:genExams){
                if(classId.equals(genExam.getStuClass())){
                    genExamList.add(genExam);
                }
            }
            classExamMap.put(classId,genExamList);
        }
        //reduce
        for(Long classId:classExamMap.keySet()){
            avgMap.put(classId,avg(classExamMap.get(classId)));
        }
        return avgMap;
    }

    private int rankClass(Long classId,Map<Long,Double> avgMap){
        List<Long> classIds=new ArrayList<>();
        classIds.addAll(avgMap.keySet());
        List<Double> avgs=new ArrayList<>();

        for(Long cid:classIds){
            avgs.add(avgMap.get(cid));
        }

        for(int i=0;i<avgs.size();i++){
            for(int j=0;j<avgs.size()-1-i;j++){
                if(avgs.get(j)<avgs.get(j+1)){
                    Double temp0 = avgs.get(j);
                    avgs.set(j,avgs.get(j+1));
                    avgs.set(j+1,temp0);

                    Long temp = classIds.get(j);
                    classIds.set(j,classIds.get(j+1));
                    classIds.set(j+1,temp);
                }
            }
        }

        int rank = 0;
        for(Long cid:classIds){
            if(cid.equals(classId)){
                return rank+1;
            }
        }
        throw new RuntimeException();
    }

    private Double failPercent(List<GenExam> genExams){
        double fail=0;
        double pass=0;
        for(GenExam genExam:genExams){
            if(genExam.getScore()<genExam.getMaxScore()*60/100){
                fail++;
            }else{
                pass++;
            }
        }
        return fail/genExams.size();
    }

    private Integer[] scoreDetails(List<GenExam> genExams){
        if (genExams.size()==0)return new Integer[3];
        double fail=genExams.get(0).getMaxScore()*60/100;
        double pass=genExams.get(0).getMaxScore()*80/100;
//        double good=genExams.get(0).getMaxScore()*90/100;

        Integer[] nums={0,0,0};

        for(GenExam genExam:genExams){
            if(genExam.getScore()<fail){
                nums[0]++;
            }else if(genExam.getScore()<=pass){
                nums[1]++;
            }else{
                nums[2]++;
            }
        }
        return nums;
    }
}
