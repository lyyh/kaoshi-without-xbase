package sei.tk.service.module.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuruijie on 2016/3/28.
 */
public class TreeMenu {
    private int id;//菜单节点id
    private String text;//菜单名称
    private int state;//菜单状态
    private String url;//url
    private List<TreeMenu> children;//子菜单
    private int pid;//父菜单
    private Map<String,Object> attributes;

    public TreeMenu(){
        attributes=new HashMap<>();
        children=new ArrayList<>();
    }

    public void addAttribute(String key,Object value){
        attributes.put(key,value);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public List<TreeMenu> getChildren() {
        return children;
    }

    public void setChildren(List<TreeMenu> children) {
        this.children = children;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
