package ia;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Node extends JPanel{
	
	protected Cost cost;
	protected Node next;
	
	public Node() {
	}
	
	//getter and setter methods
	
	public Node getNext() {
		return next;
	}
	
	public void setNext(Node next) {
		this.next = next;
	}
	
	public Cost getData() {
		return cost;
	}
	
	public void setData(Cost cost) {
		this.cost = cost;
	}
	
	public String toString() {
		return cost.getName();
	}
}
