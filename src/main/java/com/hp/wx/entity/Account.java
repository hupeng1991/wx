package com.hp.wx.entity;

/**
 * 管理账号
 * @author phu
 *
 */
public class Account {

	/**
	 * 主键
	 */
	private int acc_no;
	/**
	 * 账号名
	 */
	private String acc_name;
	/**
	 * 登录密码
	 */
	private String acc_password;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 头像
	 */
	private String head_img;
	/**
	 * 账号类型
	 */
	private String type_code;
	
	public Account(){
		
	}
	public Account(int acc_no,String acc_name){

	}
	public int getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(int acc_no) {
		this.acc_no = acc_no;
	}
	public String getAcc_name() {
		return acc_name;
	}
	public void setAcc_name(String acc_name) {
		this.acc_name = acc_name;
	}
	public String getAcc_password() {
		return acc_password;
	}
	public void setAcc_password(String acc_password) {
		this.acc_password = acc_password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHead_img() {
		return head_img;
	}
	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)  
			return false;  
		if(getClass() != obj.getClass())  
			return false;
		Account other = (Account)obj;  
		if (acc_no!=other.getAcc_no()){
			return false;  
		}
		return true;  
	}
	
	@Override
	public String toString() {
		return "用户["+acc_name+"]:"+email+","+head_img+","+type_code;
	}
	
}
