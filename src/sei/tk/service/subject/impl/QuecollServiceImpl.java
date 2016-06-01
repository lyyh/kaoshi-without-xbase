package sei.tk.service.subject.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.QuecollInfoMapper;
import sei.tk.service.dao.model.vo.subject.QueCollInfo;
import sei.tk.service.subject.QuecollService;
import sei.tk.util.Page;

import javax.annotation.Resource;


/**
 * Created by Administrator on 2016/6/1.
 */
@Service
public class QuecollServiceImpl implements QuecollService{
@Resource
    QuecollInfoMapper quecollInfoMapper;

    @Override
    public Page selectAllQuecoll(QueCollInfo queCollInfo, Integer page, Integer rows) {
        queCollInfo.setStart((page-1)*rows);
        queCollInfo.setRow(rows);

        return new Page(quecollInfoMapper.selectAllQuecoll(queCollInfo),quecollInfoMapper.countQuecoll(queCollInfo));
    }

    @Override
    public QueCollInfo selectByid(Long quecollId) {
        QueCollInfo queCollInfo=quecollInfoMapper.selectByid(quecollId);
            if(queCollInfo.getSubjectOption() != null || queCollInfo.getSubjectOption().equals("") ) {
                String tem = queCollInfo.getSubjectOption();
                String temp1 = "";
                String[] temps = tem.split("@#%");
                for (int i = 0; i < temps.length; i++) {
                    temps[i] = temps[i].replaceAll("<p>", "<p>" + (char) (65 + i) + ".");
                    temp1 += temps[i];
                }
                queCollInfo.setSubjectOption(temp1);
            }
        return queCollInfo;
    }
}
