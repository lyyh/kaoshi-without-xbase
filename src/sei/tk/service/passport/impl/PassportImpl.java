package sei.tk.service.passport.impl;

import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.PassportMapper;
import sei.tk.service.dao.model.Passport;
import sei.tk.service.passport.PassportService;
import sei.tk.util.Page;
import sei.tk.util.TkConfig;
import sei.tk.util.exception.TKException;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuruijie on 2016/5/22.
 */
@Service
public class PassportImpl implements PassportService {
    @Resource
    PassportMapper passportMapper;

    @Override
    public void changePsw(long passportId, String oldPsw, String newPsw) {
        String result=passportMapper.changePsw(passportId, oldPsw, newPsw);
        if("".equals(result)){
            throw new TKException(TkConfig.INVALID_ACTION,"旧密码输入错误");
        }
    }

    @Override
    public Page list(Integer currentPage, Integer rows, Passport passport) {
        if(currentPage!=null&&rows!=null){
            passport.setStart((currentPage - 1) * rows);
            passport.setRows(rows);
        }
        return new Page(passportMapper.selectByPage(passport),passportMapper.countForPage(passport));
    }

    @Override
    public Passport show(Integer id) {
        Passport passport=new Passport();
        passport.setId(id);
        List<Passport> passportList=passportMapper.selectByPage(passport);
        if(passportList.size()==0){
            throw new TKException(TkConfig.INVALID_ACTION,"未找到该资源");
        }
        return passportList.get(0);
    }

    @Override
    public int update(Passport passport) {
        return passportMapper.updateByPrimaryKeySelective(passport);
    }

    @Override
    public int delete(Integer id) {
        return passportMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int add(Passport passport) {
        return passportMapper.insertSelective(passport);
    }
}
