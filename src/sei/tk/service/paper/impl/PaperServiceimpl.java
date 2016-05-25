package sei.tk.service.paper.impl;

import org.springframework.stereotype.Service;
import sei.tk.util.Page;
import sei.tk.service.dao.mapper.*;
import sei.tk.service.dao.model.*;
import sei.tk.service.dao.model.vo.paper.PaperInfo;
import sei.tk.service.dao.model.vo.subject.SubjectInfo;
import sei.tk.service.paper.PaperService;
import sei.tk.util.DateFormat;
import sei.tk.util.LittleUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by ywl on 2016/4/10.
 */
@Service
public class PaperServiceimpl  implements PaperService {
    @Resource
    TkTestpaperMapper tkTestpaperMapper;
    @Resource
    PaperInfoMapper paperInfoMapper;
    @Resource
    TkTestsubjectMapper tkTestsubjectMapper;
    @Resource
    TkMkpaperMapper tkMkpaperMapper;
    @Resource
    SubjectInfoMapper subjectInfoMapper;
    @Resource
    TkMkpaperruleMapper tkMkpaperruleMapper;
    @Resource
    TkTestscheduleMapper testscheduleMapper;

    public Page selectAllpaper(PaperInfo paperInfo,Integer page, Integer rows){
        paperInfo.setStart((page-1)*rows);
        paperInfo.setRow(rows);
        List<PaperInfo> paperInfos=paperInfoMapper.selectAllPaper(paperInfo);
        for(int i=0;i<paperInfos.size();i++){
            PaperInfo p=paperInfos.get(i);
            p.setTimeString(DateFormat.DateToString(p.getPpassportTime(), "yyyy-MM-dd"));
            p.setPaperName(LittleUtil.filterImg(p.getPaperName()));
        }
        return new Page(paperInfos,paperInfoMapper.countPaperInfo(paperInfo));
        }
    public PaperInfo selectpaperinfobyId(Long  testpaperId){
       PaperInfo paperInfo= paperInfoMapper.selectById(testpaperId);
        List<TkSubjectWithBLOBs> subjects=paperInfoMapper.selectSubjects(testpaperId); // 这里做了更改
        paperInfo.setSubjects(subjects);
        return paperInfo;
    }
    public int deletePaperBatch(Long[] testpaperIds){
       List<Long> tkmkpaperIds=new ArrayList<Long>();
        for(int i=0;i<testpaperIds.length;i++){
            tkmkpaperIds.add(tkTestpaperMapper.selectMkpaperid(testpaperIds[i]));
        }
        TkTestsubjectExample testsubjectExample=new TkTestsubjectExample();    //删除testsubject
        TkTestsubjectExample.Criteria criteria= testsubjectExample.createCriteria();
        criteria.andTestpaperIdIn(Arrays.asList(testpaperIds));
        tkTestsubjectMapper.deleteByExample(testsubjectExample);

        TkTestpaperExample tkTestpaperExample=new TkTestpaperExample();    //删除Testpaper
        TkTestpaperExample.Criteria criteria1=tkTestpaperExample.createCriteria();
        criteria1.andTestpaperIdIn(Arrays.asList(testpaperIds));
        tkTestpaperMapper.deleteByExample(tkTestpaperExample);

        TkMkpaperruleExample tkMkpaperruleExample=new TkMkpaperruleExample();  //删除制卷规则
        TkMkpaperruleExample.Criteria criteria2 =tkMkpaperruleExample.createCriteria();
        criteria2.andMkpaperIdIn(tkmkpaperIds);
        tkMkpaperruleMapper.deleteByExample(tkMkpaperruleExample);

        TkMkpaperExample tkMkpaperExample=new TkMkpaperExample();
        TkMkpaperExample.Criteria criteria3=tkMkpaperExample.createCriteria();
        criteria3.andMkpaperIdIn(tkmkpaperIds);
       return  tkMkpaperMapper.deleteByExample(tkMkpaperExample);
    }
     public boolean updatepaper(TkMkpaper tkMkpaper,TkTestsubject tkTestsubject ,TkTestpaper tktestpaper){
         int a=tkMkpaperMapper.updateByPrimaryKey(tkMkpaper);
         int b=tkTestpaperMapper.updateByPrimaryKey(tktestpaper);
         int c=tkTestsubjectMapper.updateByPrimaryKey(tkTestsubject);
        if (a==b && b==c)
            return true;
         else
            return false;
    }
    public boolean deletePaper(long testpaperId){
        long mkpaperid= tkTestpaperMapper.selectMkpaperid(testpaperId);
        TkTestscheduleExample tkTestscheduleExample=new TkTestscheduleExample();
        TkTestscheduleExample.Criteria criteria=tkTestscheduleExample.createCriteria();
        criteria.andTestpaperIdEqualTo(testpaperId);
        if (testscheduleMapper.selectByExample(tkTestscheduleExample)==null | testscheduleMapper.selectByExample(tkTestscheduleExample).size()==0) {
            tkTestsubjectMapper.deletebytestpaperid(testpaperId);
            tkTestpaperMapper.deleteByPrimaryKey(testpaperId);
            tkMkpaperruleMapper.deletebymkpaperid(mkpaperid);
            tkMkpaperMapper.deleteByPrimaryKey(mkpaperid);
            return true;
        }
         else
            return  false;
    }

}



