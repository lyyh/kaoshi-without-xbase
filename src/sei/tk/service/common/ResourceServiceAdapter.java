package sei.tk.service.common;

import sei.tk.service.dao.model.common.ResourceVo;
import sei.tk.util.Page;
import sei.tk.util.ResourceMapper;
import sei.tk.util.TkConfig;
import sei.tk.util.exception.TKException;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuruijie on 2016/5/23.
 */
public class ResourceServiceAdapter<K extends ResourceMapper<T>,T extends ResourceVo> implements ResourceService<T>{


    protected K resourceMapper;

    @Resource
    public void setResourceMapper(K resourceMapper) {
        this.resourceMapper = resourceMapper;
    }

    @Override
    public Page list(Integer currentPage, Integer rows, T resourceVo) {
        if(currentPage!=null&&rows!=null){
            resourceVo.setStart((currentPage-1)*rows);
            resourceVo.setRows(rows);
        }
        return new Page(resourceMapper.selectByPage(resourceVo),resourceMapper.countForPage(resourceVo));
    }

    @Override
    public T show(Integer id) {
        T resourceVo= (T) new ResourceVo();
        resourceVo.setId(id);
        List<T> resourceVoList=resourceMapper.selectByPage(resourceVo);
        if(resourceVoList.size()==0){
            throw new TKException(TkConfig.INVALID_ACTION,"未找到该资源");
        }
        return resourceVoList.get(0);
    }

    @Override
    public int update(T resourceVo) {
        return resourceMapper.updateByPrimaryKeySelective(resourceVo);
    }

    @Override
    public int delete(Integer id) {
        return resourceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int add(T resourceVo) {
        return resourceMapper.insertSelective(resourceVo);
    }
}
