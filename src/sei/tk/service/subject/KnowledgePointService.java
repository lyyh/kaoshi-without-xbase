package sei.tk.service.subject;

import sei.tk.service.dao.model.TkKnopoint;
import sei.tk.util.Page;

/**
 * Created by liuruijie on 2016/5/12.
 */
public interface KnowledgePointService {
    Page getAllKnowledgePointByPage(Integer page, Integer rows, TkKnopoint knopoint);
    void updateKnopoint(TkKnopoint knopoint);
    void deleteKnopoint(Long id);
    TkKnopoint getKnopointById(Long id);
    void insertKnopoint(TkKnopoint knopoint);
}
