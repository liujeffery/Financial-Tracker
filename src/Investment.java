package ia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;

public class Investment extends Cost{
	
	private String name;
	private double principal;
	private double growth;
	private int compoundRate;
	private LocalDate date;
	
	public Investment(String name, double principal, double growth, int compoundRate, LocalDate date) {
		super(name);
		this.principal = principal;
		this.growth = growth;
		this.compoundRate = compoundRate;
		this.date = date;
	}
	
	//getter and setter methods
	
	public double getPrincipal() {
		return principal;
	}
	
	public double getGrowth() {
		return growth;
	}
	
	public int getCompoundRate() {
		return compoundRate;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setPrincipal(double principal) {
		this.principal = principal;
	}
	
	public void setGrowth(double growth) {
		this.growth = growth;
	}
	
	public void setCompoundRate(int compoundRate) {
		this.compoundRate = compoundRate;
	}
	public double calculateReturn() {
		double total = 0;
		int monthsPassed = 0;
		int numberOfCompounds = 0;
		LocalDate now = LocalDate.now();
		
		//finding the number of months that have passed
		monthsPassed = (now.getYear() - date.getYear()) * 12 + now.getMonthValue() - date.getMonthValue();
		
		numberOfCompounds = monthsPassed / compoundRate;
		
		total = principal * Math.pow(1 + growth/100, numberOfCompounds) - principal;
		total = Math.round(total * 100) / 100;
		
		return total;
	}
}
