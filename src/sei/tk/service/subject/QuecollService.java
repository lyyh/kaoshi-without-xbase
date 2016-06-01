package sei.tk.service.subject;

import sei.tk.service.dao.model.vo.subject.QueCollInfo;
import sei.tk.util.Page;

import java.util.List;

/**
 * Created by ywl on 2016/6/1.
 */
public interface QuecollService {
    Page selectAllQuecoll(QueCollInfo queCollInfo,Integer page, Integer rows);
    QueCollInfo selectByid( Long quecollId);
}
