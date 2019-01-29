package com.ucmed.shiro.model.bean.vo;

import java.util.Date;
import java.util.List;

import com.ucmed.shiro.model.bean.pojo.UserRoleKey;

public class UserRolesVO {
	private Integer id;
    private String username;
    private String realname;
    private String mobile;
    private String email;
    private String password;
    private String salt;
    private Integer insertUid;
    private Date insertTime;
    private Date updateTime;
    private Boolean isDel;
    private Boolean isJob;
    private String extend;
	private List<UserRoleKey> userRoles;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}
	public Integer getInsertUid() {
		return insertUid;
	}
	public void setInsertUid(Integer insertUid) {
		this.insertUid = insertUid;
	}
	public boolean getDel() {
		return isDel;
	}
	public void setDel(boolean del) {
		isDel = del;
	}
	public boolean getJob() {
		return isJob;
	}
	public void setJob(boolean job) {
		isJob = job;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Boolean getIsDel() {
		return isDel;
	}
	public void setIsDel(Boolean isDel) {
		this.isDel = isDel;
	}
	public Boolean getIsJob() {
		return isJob;
	}
	public void setIsJob(Boolean isJob) {
		this.isJob = isJob;
	}
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	public List<UserRoleKey> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(List<UserRoleKey> userRoles) {
		this.userRoles = userRoles;
	}

	@Override
	public String toString() {
		return "UserRolesVO [id=" + id + ", username=" + username + ", mobile="
				+ mobile + ", email=" + email + ", password=" + password
				+ ", insertUid=" + insertUid + ", insertTime=" + insertTime
				+ ", updateTime=" + updateTime + ", isDel=" + isDel
				+ ", isJob=" + isJob + ", userRoles=" + userRoles
				+ "]";
	}

}