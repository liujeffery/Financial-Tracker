package ia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class TableFrame extends JFrame implements ActionListener{

	boolean isAscending;
	boolean isStatic;
	
	public TableFrame(boolean isStatic, boolean isAscending) {
		super("Money Manager");
		
		JPanel panel = new JPanel();
		int buttonX = 0;
		int tableX = 0;
		char identifier = 'i';
		
		this.isStatic = isStatic;
		this.isAscending = isAscending;
		
		final String[] STATIC_COLUMNS = {"Name", "Cost", "Date",};
		final String[] INVESTMENT_COLUMNS = {"Name", "Principal", "Growth", "Compound Rate", "Date", "Return to Date"}; 
		
		//default table model since table don't need as much functionality
		DefaultTableModel model = new DefaultTableModel();
		JTable table = new JTable(model);
		 
		//makes table uneditable by user
		table.setEnabled(false);
		table.getTableHeader().setResizingAllowed(false);
		
		table.setRowHeight(30);
		
		JScrollPane scrollpane = new JScrollPane(table);
		
		panel.setLayout(null);
		
		if (isStatic) {
			buttonX = 870;
			tableX = 280;
			//adding columns to table
			for (int i = 0; i < STATIC_COLUMNS.length; i = i + 1)
				model.addColumn(STATIC_COLUMNS[i]);
			//adding all rows
			for (int i = 0; i < MainFrame.staticList.getSize(); i = i + 1) {
				Cost temp = MainFrame.staticList.getNode(i).getData();
				model.addRow(new Object[]{temp.getName(), String.format("%.2f", temp.getCost() ), temp.getDate()});
			}
			JButton sortName = new JButton("Sort by name");
			sortName.addActionListener(this);
			MainFrame.setButton(sortName, "sortName", 25, 100, 190, 30);
			panel.add(sortName);
			
			JButton sortDate = new JButton("Sort by date");
			sortDate.addActionListener(this);
			MainFrame.setButton(sortDate, "sortDate", 25, 150, 190, 30);
			panel.add(sortDate);
			
			JButton sortCost = new JButton("Sort by cost");
			sortCost.addActionListener(this);
			MainFrame.setButton(sortCost, "sortCost", 25, 200, 190, 30);
			panel.add(sortCost);
			
			//setting custom size
			scrollpane.setBounds(tableX, 20, 540, 550);
		}
		
		else {
			buttonX = 900;
			tableX = 235;
			for (int i = 0; i < INVESTMENT_COLUMNS.length; i = i + 1)
				model.addColumn(INVESTMENT_COLUMNS[i]);
			for (int i = 0; i < MainFrame.investmentList.getSize(); i = i + 1) {
				Investment temp = (Investment)MainFrame.investmentList.getNode(i).getData();
				model.addRow(new Object[] {temp.getName(), String.format("%.2f", temp.getPrincipal()), temp.getGrowth(), temp.getCompoundRate(),
						temp.getDate(), String.format("%.2f", temp.calculateReturn())});
			}
			
			JButton sortName = new JButton("Sort by name");
			sortName.addActionListener(this);
			MainFrame.setButton(sortName, "sortName", 25, 100, 190, 30);
			panel.add(sortName);
			
			JButton sortDate = new JButton("Sort by date");
			sortDate.addActionListener(this);
			MainFrame.setButton(sortDate, "sortDate", 25, 150, 190, 30);
			panel.add(sortDate);
			
			JButton sortPrincipal = new JButton("Sort by principal");
			sortPrincipal.addActionListener(this);
			MainFrame.setButton(sortPrincipal, "sortPrincipal", 25, 200, 190, 30);
			panel.add(sortPrincipal);
			
			JButton sortGrowth = new JButton("Sort by growth");
			sortGrowth.addActionListener(this);
			MainFrame.setButton(sortGrowth, "sortGrowth", 25, 250, 190, 30);
			panel.add(sortGrowth);
			
			JButton sortCompoundRate = new JButton("Sort by compound rate");
			sortCompoundRate.addActionListener(this);
			MainFrame.setButton(sortCompoundRate, "sortCompoundRate", 25, 300, 190, 30);
			panel.add(sortCompoundRate);
			
			scrollpane.setBounds(tableX, 20, 630, 550);
		}
		
		if (isStatic)
			identifier = 's';
		
		for (int i = 0; i < table.getRowCount(); i = i + 1) {
			JButton edit = new JButton("Edit");
			edit.addActionListener(this);
			MainFrame.setButton(edit, identifier + "edit" + i, buttonX, 40 + i * 30, 80, 30);
			panel.add(edit);
			
			JButton remove = new JButton("Remove");
			remove.addActionListener(this);
			MainFrame.setButton(remove, identifier + "remove" + i, buttonX + 90, 40 + i * 30, 80, 30);
			panel.add(remove);
		}
		
		JButton sortOrder = new JButton("Sorting order: Descending");
		if (isAscending) {
			sortOrder.setText("Sorting order: Ascending");
		}
		sortOrder.addActionListener(this);
		MainFrame.setButton(sortOrder, "changeSortOrder", 25, 50, 190, 30);
		panel.add(sortOrder);
		
		panel.add(scrollpane);
		
		JButton returnToMain = new JButton("Return to main menu");
		returnToMain.addActionListener(this);
		MainFrame.setButton(returnToMain, "return", 450, 600, 200, 30);
		panel.add(returnToMain);
		
		MainFrame.setPanel(panel, MainFrame.BG, 0, 0, 800, 800);
		
		add(panel);
	}

	public void modifyData (boolean isStatic, int position) {
		String title = "";
		final String[] POSSIBILITIES = {"4 months", "6 months", "12 months"};
		
		JPanel add = new JPanel();
		add.setLayout(null);
		
		JLabel name = new JLabel("Name: ");
		name.setBounds(0, 0, 40, 30);
		add.add(name);
		
		JTextField nameField = new JTextField();
		nameField.setBounds(120, 0, 150, 30);
		add.add(nameField);
		
		JLabel cost = new JLabel("Cost: ");			
		cost.setBounds(0, 50, 40, 30);
		
		JLabel principal = new JLabel("Principal: ");
		principal.setBounds(0, 50, 100, 30);
		
		JTextField costField = new JTextField();
		costField.setBounds(120, 50, 150, 30);
		add.add(costField);
		
		JLabel growth = new JLabel("Percent growth: ");
		growth.setBounds(0, 100, 100, 30);
		
		JTextField growthField = new JTextField();
		growthField.setBounds(120, 100, 150, 30);
		
		JLabel compoundRate = new JLabel("Compound rate: ");
		compoundRate.setBounds(0, 150, 100, 30);
		
		JComboBox compoundRateField = new JComboBox(POSSIBILITIES);
		compoundRateField.setActionCommand("compoundRateField");
		compoundRateField.setBounds(120, 150, 150, 30);
		//due to scope, all fields must be initialized and the appropriate ones are added
		if(isStatic) {
			title = "Parameters for cost: ";
			
			Cost temp = MainFrame.staticList.getNode(position).getData();
			
			nameField.setText(temp.getName());
			costField.setText("" + temp.getCost());
			
			add.add(cost);
			
			add.setPreferredSize(new Dimension(300, 100));
		}
		else {
			title = "Parameters for investment";
			
			Investment temp = (Investment)MainFrame.investmentList.getNode(position).getData();
			
			nameField.setText(temp.getName());
			costField.setText("" + temp.getPrincipal());
			growthField.setText("" + temp.getGrowth());
			compoundRateField.setSelectedIndex(temp.getCompoundRate() / 4 + temp.getCompoundRate() % 4 - 1);
			
			add.add(principal);
			add.add(growth);
			add.add(growthField);
			add.add(compoundRate);
			add.add(compoundRateField);
			
			add.setPreferredSize(new Dimension(300, 220));
		}
		int result = JOptionPane.showConfirmDialog(null, add, 
	             title, JOptionPane.OK_CANCEL_OPTION);
		
		boolean isValid = true;
		//user input only goes through if it is valid
		if (result == JOptionPane.OK_OPTION) {
			if (isStatic){
				try {
					Double.parseDouble(costField.getText());
				}
				catch (Exception e1) {
					MainFrame.showErrorMessage("Cost is invalid!");
					isValid = false;
				}
			}
			else {
				try {
					double test1 = Double.parseDouble(costField.getText());
					double test2 = Double.parseDouble(growthField.getText());
					if (test1 <= 0 || test2 <= 0) {
						MainFrame.showErrorMessage("Neither of these values can be negative!");
						isValid = false;
					}
				}
				catch (Exception e1) {
					MainFrame.showErrorMessage("Fields are invalid!");
					isValid = false;
				}
			}
			if (isValid) {
				if (isStatic) {
					Cost temp = MainFrame.staticList.getNode(position).getData();
					temp.setName(nameField.getText());
					temp.setCost(Double.parseDouble(costField.getText()));
					
					MainFrame.staticList.getNode(position).setData(temp);;
				}
				else {
					int compoundRateInt = 1;
					switch((String)compoundRateField.getSelectedItem()) {
						case "4 months":
							compoundRateInt = 4;
							break;
						case "6 months":
							compoundRateInt = 6;
							break;
						case "12 months":
							compoundRateInt = 12;
							break;
					}
					Investment temp = (Investment)MainFrame.investmentList.getNode(position).getData();
					temp.setName(nameField.getText());
					temp.setPrincipal(Double.parseDouble(costField.getText()));
					temp.setGrowth(Double.parseDouble(growthField.getText()));
					temp.setCompoundRate(compoundRateInt);
					
					MainFrame.investmentList.getNode(position).setData(temp);
				}
				MainFrame.rewriteFile();
				JOptionPane.showMessageDialog(this, "Edited successfully!");
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		String temp = e.getActionCommand();
		if (temp.equals("return")) {
			MainFrame.loadMain();
		}
		else if(temp.equals("changeSortOrder")) {
			isAscending = !isAscending;
			MainFrame.loadTable(this.isStatic, this.isAscending);
		}
		else if (temp.substring(0, 4).equals("sort")) {
			LinkedList hold;
			if (isStatic) {
				hold = MainFrame.staticList;
				if (temp.equals("sortName")) {
					hold.sortAlpha(isAscending);
				}
				else if (temp.equals("sortDate")) {
					hold.sortDate(isAscending);
				}
				else if (temp.equals("sortCost")) {
					hold.sortNumber("cost", isAscending);
				}
			}
			else {
				hold = MainFrame.investmentList;
				switch (temp) {
					case "sortName":
						hold.sortAlpha(isAscending);
						break;
					case "sortDate":
						hold.sortDate(isAscending);
						break;
					case "sortPrincipal":
						hold.sortNumber("principal", isAscending);
						break;
					case "sortGrowth":
						hold.sortNumber("growth", isAscending);
						break;
					case "sortCompoundRate":
						hold.sortNumber("compoundRate", isAscending);
						break;
				}
			}
			MainFrame.rewriteFile();
			MainFrame.loadTable(isStatic, isAscending);
		}
		else {
			for (int i = 0; i < MainFrame.staticList.getSize(); i = i + 1) {
				if (temp.equals("sedit" + i)) {
					modifyData(true, i);
					MainFrame.loadTable(true, this.isAscending);
					break;
				}
				else if (temp.equals("sremove" + i)){
					if (i == 0) {
						MainFrame.staticList.removeFirst();
					}
					else if (i == MainFrame.staticList.getSize() - 1) {
						MainFrame.staticList.removeLast();
					}
					else {
						MainFrame.staticList.removeMiddle(i);
					}
					MainFrame.loadTable(true, this.isAscending);
					break;
				}
			}
			for (int i = 0; i < MainFrame.investmentList.getSize(); i = i + 1) {
				if (temp.equals("iedit" + i)) {
					modifyData(false, i);
					MainFrame.loadTable(false, this.isAscending);
					break;
				}
				else if (temp.equals("iremove" + i)) {
					if (i == 0) {
						MainFrame.investmentList.removeFirst();
					}
					else if (i == MainFrame.investmentList.getSize() - 1) {
						MainFrame.investmentList.removeLast();
					}
					else {
						MainFrame.investmentList.removeMiddle(i);
					}
					MainFrame.loadTable(false, this.isAscending);
					break;
				}
			}
			MainFrame.rewriteFile();
		}
	}
}
