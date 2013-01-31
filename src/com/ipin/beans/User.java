package com.ipin.beans;

import java.io.Serializable;
import java.util.Set;

@SuppressWarnings("unchecked")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer userId;

	/** persistent field */
	private String userAccount;

	/** persistent field */
	private String password;

	/** nullable persistent field */
	private String userTrueName;

	/** nullable persistent field */
	private String userTel;

	/** nullable persistent field */
	private String userNickName;

	/** nullable persistent field */
	private String userImage;

	/** nullable persistent field */
	private Integer userCredit;

	/** nullable persistent field */
	private String userEmail;

	/** persistent field */
	private Set calltaxiorders;

	/** full constructor */
	public User(String userAccount, String password, String userTrueName,
			String userTel, String userNickName, String userImage,
			Integer userCredit, String userEmail, Set calltaxiorders) {
		this.userAccount = userAccount;
		this.password = password;
		this.userTrueName = userTrueName;
		this.userTel = userTel;
		this.userNickName = userNickName;
		this.userImage = userImage;
		this.userCredit = userCredit;
		this.userEmail = userEmail;
		this.calltaxiorders = calltaxiorders;
	}

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String userAccount, String password, Set calltaxiorders) {
		this.userAccount = userAccount;
		this.password = password;
		this.calltaxiorders = calltaxiorders;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserTrueName() {
		return this.userTrueName;
	}

	public void setUserTrueName(String userTrueName) {
		this.userTrueName = userTrueName;
	}

	public String getUserTel() {
		return this.userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserNickName() {
		return this.userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getUserImage() {
		return this.userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public Integer getUserCredit() {
		return this.userCredit;
	}

	public void setUserCredit(Integer userCredit) {
		this.userCredit = userCredit;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Set getCalltaxiorders() {
		return this.calltaxiorders;
	}

	public void setCalltaxiorders(Set calltaxiorders) {
		this.calltaxiorders = calltaxiorders;
	}

}
