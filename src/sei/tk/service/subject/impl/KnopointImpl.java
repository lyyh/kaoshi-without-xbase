package sei.tk.service.subject.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.TkKnopointMapper;
import sei.tk.service.dao.model.TkKnopoint;
import sei.tk.service.dao.model.TkKnopointExample;
import sei.tk.service.subject.KnowledgePointService;
import sei.tk.util.Page;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuruijie on 2016/5/12.
 */
@Service
public class KnopointImpl implements KnowledgePointService {
    @Resource
    TkKnopointMapper tkKnopointMapper;

    @Override
    public Page getAllKnowledgePointByPage(Integer page, Integer rows, TkKnopoint knopoint) {
        TkKnopointExample tkKnopointExample=new TkKnopointExample();
        TkKnopointExample.Criteria criteria = tkKnopointExample.createCriteria();
        if(knopoint.getKnopointName()!=null){
            criteria.andKnopointNameLike(knopoint.getKnopointName());
        }
        if(knopoint.getChapterId()!=null){
            criteria.andChapterIdEqualTo(knopoint.getChapterId());
        }
        if(knopoint.getCourseId()!=null){
            criteria.andCourseIdEqualTo(knopoint.getCourseId());
        }
        if(page==null||rows==null){
            tkKnopointExample.setStart(null);
        }else{
            tkKnopointExample.setStart((page-1)*rows);
        }
        tkKnopointExample.setRows(rows);
        List<TkKnopoint> tkKnopointList=tkKnopointMapper.selectByExample(tkKnopointExample);
        int total=tkKnopointMapper.countByExample(tkKnopointExample);
        return new Page(tkKnopointList,total);
    }

    @Override
    public void updateKnopoint(TkKnopoint knopoint) {
        if(tkKnopointMapper.updateByPrimaryKeySelective(knopoint)==0){
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteKnopoint(Long id) {
        tkKnopointMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TkKnopoint getKnopointById(Long id) {
        return tkKnopointMapper.selectByPrimaryKey(id);
    }

    @Override
    public void insertKnopoint(TkKnopoint knopoint) {
        if(tkKnopointMapper.insertSelective(knopoint)==0){
            throw new RuntimeException();
        }
    }
}
