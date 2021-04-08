package ia;

import java.time.LocalDate;

//stored as a linked list

public class LinkedList {
	private Node head, tail;
	private int size;
	
	public LinkedList () {
		head = tail = null;
		size = 0;
	}
	
	public void addFirst(Node node) {
		node.setNext(head);
		head = node;
		size = size + 1;
	}
	
	public void addLast(Node node) {
		if (tail == null) {
			node.setNext(null);
			head = tail = node;
		}
		else {
			tail.setNext(node);
			node.setNext(node);
			tail = node;
		}
		size = size + 1;
	}
	
	public void addMiddle(int position, Node node) {
		Node hold = head;
		//1 means the node directly after the head, size of linked list - 1 is the node directly before tail
		for (int i = 0; i < position - 1; i = i + 1) {
			hold = hold.getNext();
		}
		node.setNext(hold.getNext());
		hold.setNext(node);
		size = size + 1;
	}
	
	public Node getNode(int position) {
		Node hold = head;
		for (int i = 0; i < position; i = i + 1) {
			hold = hold.getNext();
		}
		return hold;
	}
	
	public int getSize() {
		return size;
	}
	
	public void removeFirst() {
		head = head.getNext();
		size = size - 1;
	}
	
	public void removeLast() {
		tail = getNode(size - 1);
		tail.setNext(null);
		size = size - 1;
	}
	
	public void purge() {
		head = tail = null;
		size = 0;
	}
	
	public void removeMiddle(int position) {
		//1 means the node directly after the head, size of linked list - 1 is the node directly before tail
		Node hold1 = getNode(position - 1);
		Node hold2 = getNode(position + 1);
		hold1.setNext(hold2);
		size = size - 1;
	}
	
	public void sortAlpha(boolean isAscending) {
		//selection sort based on ascii code
		String min = getNode(0).getData().getName();
		int pos = 0;
		String temp = "";
		for (int i = 0; i < size - 1; i = i + 1) {
			pos = i;
			min = getNode(pos).getData().getName();
			//finding the highest alphabetically ranked name
			for (int j = i + 1; j < size; j = j + 1) {
				temp = getNode(j).getData().getName();
				for (int k = 0; k < Math.min(min.length(), temp.length()); k = k + 1) {
					if (isAscending) {
						if (min.charAt(k) > temp.charAt(k)) {
							min = temp;
							pos = j;
							break;
						}
					}
					else {
						if (min.charAt(k) < temp.charAt(k)) {
							min = temp;
							pos = j;
							break;
						}
					}
				}
			//swapping data into alphabetical order
			}
			swap(i, pos);
		}
	}
	
	//quicksort
	public void sortNumber(String type, boolean isAscending) {
		quickSort(0, size, type, isAscending);
	}
	
	public void swap(int i, int pos){
		Cost temp = getNode(i).getData();
		getNode(i).setData(getNode(pos).getData());
		getNode(pos).setData(temp);
	}
	
	//bubble sort
	public void sortDate(boolean isAscending) {
		int temp1 = 0;
		int temp2 = 0;
		boolean isSorted = false;
		while (!isSorted) {
			isSorted = true;
			for (int i = 0; i < size - 1; i = i + 1) {
				temp1 = clearHyphens(getNode(i).getData().getDate());
				temp2 = clearHyphens(getNode(i + 1).getData().getDate());
				if (isAscending) {
					if (temp1 > temp2) {
						isSorted = false;
						swap(i, i + 1);
					}
				}
				else {
					if (temp1 < temp2) {
						isSorted = false;
						swap(i, i + 1);
					}
				}
			}
		}
	}
	
	public int clearHyphens(LocalDate date) {
		String converted = "";
		String temp = date.toString();
		for (int i = 0; i < temp.length(); i = i + 1) {
			if (temp.charAt(i) != '-')
				converted = converted + temp.charAt(i);
		}
		return Integer.parseInt(converted);
	}
	
	public int partition(int low, int high, String type, boolean isAscending){
		double pivot = returnNumber(high, type);
		
	    int i = (low - 1);
	 
	    for(int j = low; j <= high - 1; j++){
	    	if (isAscending) {
		        if (returnNumber(j, type) < pivot) {
		            i++;
		            swap(i, j);
		        }
	    	}
	    	else {
	    		if (returnNumber(j, type) > pivot) {
		            i++;
		            swap(i, j);
		        }
	    	}
	    }
	    swap(i + 1, high);
	    return (i + 1);
	}
	 
	public double returnNumber(int pos, String type) {
		double num = 0;
		if (type.equals("cost"))
			return getNode(pos).getData().getCost();
		Investment temp = (Investment)getNode(pos).getData();
		switch(type) {
		case "principal":
			num = temp.getPrincipal();
			break;
		case "growth":
			num = temp.getGrowth();
			break;
		case "compoundRate":
			num = temp.getCompoundRate();
			break;
		}
		return num;
	}
	
	public void quickSort(int low, int high, String type, boolean isAscending){
	    if (low < high){
	        int pi = partition(low, high, type, isAscending);
	 
	        quickSort(low, pi - 1, type, isAscending);
	        quickSort(pi + 1, high, type, isAscending);
	    }
	}
	
	public String toString(boolean isStatic) {
		//assembling information to be used by file writer
		String hold = "";
		if (size > 0) {
			if (getNode(0).getData() instanceof Investment) {
				Investment temp = null;
				for (int i = 0; i < size; i = i + 1) {
					temp = ((Investment)getNode(i).getData());
					hold = hold + "true^" + temp.getName() + "^" + temp.getPrincipal() + "^" + temp.getGrowth() + "^" + temp.getCompoundRate() + "^" + 
							temp.getDate().getYear() + "^" + temp.getDate().getMonthValue() + "^" + temp.getDate().getDayOfMonth();
					if (i != size - 1)
						hold = hold + "\n";
				}
			}
			else {
				Cost temp = null;
				for (int i = 0; i < size; i = i + 1) {
					temp = getNode(i).getData();
					hold = hold + "false^" + temp.getName() + "^" + temp.getCost() + "^" + temp.getDate().getYear() + "^" + 
							temp.getDate().getMonthValue() + "^" + temp.getDate().getDayOfMonth();
					if (i != size - 1)
						hold = hold + "\n";
				}
			}
		}
		return hold;
	}
}
