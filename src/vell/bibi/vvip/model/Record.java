package vell.bibi.vvip.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Record {
	public static final SimpleDateFormat RecordDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private int id;
	private User operator;
	private User customer;
	private double value = 0;
	private String info = "";
	private Date date = new Date();
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	@OneToOne
	public User getOperator() {
		return operator;
	}
	@OneToOne
	public User getCustomer() {
		return customer;
	}
	public double getValue() {
		return value;
	}
	public String getInfo() {
		return info;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setOperator(User operator) {
		this.operator = operator;
	}
	public void setCustomer(User customer) {
		this.customer = customer;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		String str = Record.RecordDateFormat.format(date) + " " + "oper:" + operator.getName() + "\n"
			+ customer.getName() + ": " + (value >= 0.0? "+" : "") + value + " " + info;
		
		return str;
	}
	
}
