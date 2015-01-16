package vell.bibi.vvip.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import vell.bibi.vvip.util.StrUtil;

@Entity
@DynamicUpdate
public class User {
	public static final int FORZEN = 0;
	public static final int NORMAL = 1;
	public static final int MANAGER = 2;
	public static final int SUPERMANAGER = 3;
	
	private int id;
	private String name;
	private String phone;
	private String studentId;
	private double balance = 0;
	private String wechatId;
	private int status = 1;
	private String info;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public double getBalance() {
		return balance;
	}
	public String getInfo() {
		return info;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getWechatId() {
		return wechatId;
	}
	public int getStatus() {
		return status;
	}
	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		String str = id + " " + name + "\n"
				+ (StrUtil.isEmpty(phone) ? "" : ("phone:" + phone) ) 
				+ (StrUtil.isEmpty(studentId) ? "" : (" studentId:" + studentId) ) 
				+ (StrUtil.isEmpty(wechatId) ? "" : (" wechatId:" + wechatId) )
				+ "\n余额: " + balance + "元"
				+ (StrUtil.isEmpty(info) ? "" : ("\n" + info));
		return str;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof User) {
			User user = (User) obj;
			if(user.id == this.id 
					&& StrUtil.equals(user.name, this.name) 
					&& StrUtil.equals(user.phone, this.phone)
					&& StrUtil.equals(user.studentId, this.studentId))
				return true;
		}
		return false;
	}
	
}
