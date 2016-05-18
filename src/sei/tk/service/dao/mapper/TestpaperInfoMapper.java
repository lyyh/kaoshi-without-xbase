package sei.tk.service.dao.mapper;

import sei.tk.service.dao.model.TkSubject;
import sei.tk.service.dao.model.vo.test.TestpaperInfVo;
import sei.tk.service.dao.model.vo.test.TypeAmount;

import java.util.List;
import java.util.Map;

/**
 * Created by liuruijie on 2016/4/7.
 */
public interface TestpaperInfoMapper {
    TestpaperInfVo selectTestpaperInfoById(Long testpaperId);
    List<TypeAmount> selectTestpaperSubAmountById(Long testpaperId);
    List<TkSubject> selectSubjectsFromTestpaper(Long testpaperId);

}
