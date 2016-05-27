package sei.tk.service.module.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import sei.tk.service.dao.mapper.AccessMapper;
import sei.tk.service.dao.mapper.ModuleMapper;
import sei.tk.service.dao.model.Module;
import sei.tk.service.dao.model.SessionPassport;
import sei.tk.service.module.ModuleService;
import sei.tk.service.module.vo.TreeMenu;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuruijie on 2016/5/17.
 */
@Service
public class ModuleImpl implements ModuleService{
    @Resource
    AccessMapper accessMapper;
    @Resource
    ModuleMapper moduleMapper;

    @Override
    public List<TreeMenu> menuList(SessionPassport sessionPassport) {
        List<TreeMenu> menuList=new ArrayList<>();
        List<Module> moduleList=null;
        Map<Integer,TreeMenu> menuMap=new HashMap<>();
        String result=moduleMapper.menuList(sessionPassport.getRoleId(), sessionPassport.getGroupId(), sessionPassport.getPassportId().intValue());
        if(result==null||"".equals(result)){return menuList;}
        else{
            moduleList= JSON.parseArray(result, Module.class);
            moduleList.remove(moduleList.size()-1);
            for(Module module:moduleList){
                TreeMenu treeMenu=new TreeMenu();
                treeMenu.setId(module.getId());
                treeMenu.setPid(module.getModulePid());
                treeMenu.setText(module.getName());
                treeMenu.setState(module.getStatus());
                treeMenu.setUrl(module.getModuleUrl());
                menuMap.put(module.getId(),treeMenu);
                menuList.add(treeMenu);
            }
            for (Integer id:menuMap.keySet()){
                TreeMenu treeMenu=menuMap.get(id);
                if(treeMenu.getPid()==0)continue;
                TreeMenu menu=menuMap.get(treeMenu.getPid());
                if(menu==null)continue;
                menu.getChildren().add(treeMenu);
                menuList.remove(treeMenu);
            }
        }
        return menuList;
    }
}
