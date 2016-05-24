package sei.tk.service.dao.model;

/**
 * Created by liuruijie on 2016/5/16.
 */
public class SessionPassport {
    private String userId;//用户账号
    private String userName;//用户名称
    private Long passportId;//账号编号
    private Integer groupId;//组编号
    private Integer roleId;//角色编号

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Long getPassportId() {
        return passportId;
    }

    public void setPassportId(Long passportId) {
        this.passportId = passportId;
    }
}
