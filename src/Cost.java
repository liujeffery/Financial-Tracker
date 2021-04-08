package ia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;

public class Cost{
	
	private String name;
	private double cost;
	private LocalDate date;
	
	public Cost(String name, double cost, LocalDate date) {
		this.name = name;
		this.cost = cost;
		this.date = date;
		cost = Math.round(cost * 100) / 100;
	}
	//getter and setter methods
	
	//specific constructor for investment
	public Cost(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public double getCost() {
		return cost;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
}
