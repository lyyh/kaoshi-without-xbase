package sei.tk.service.dao.mapper;

import sei.tk.service.dao.model.vo.subject.QueCollInfo;

import java.util.List;

/**
 * Created by ywl on 2016/6/1.
 */
public interface  QuecollInfoMapper {
    int countQuecoll(QueCollInfo queCollInfo);
    List<QueCollInfo> selectAllQuecoll(QueCollInfo queCollInfo);
    QueCollInfo selectByid( Long quecollId);

}
