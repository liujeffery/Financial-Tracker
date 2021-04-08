package ia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;

public class MainPanel extends JFrame implements ActionListener{

	private JPanel main;
	private BufferedReader buffer;
	private FileReader reader;
	private ImageIcon icon;
	private JLabel logo;
	private JPanel staticTotalPanel;
	private JPanel investmentTotalPanel;
	private JPanel totalPanel;
	private JButton allStaticCosts;
	private JButton addStaticCost;
	private JButton allInvestments;
	private JButton addInvestment;
	private JLabel staticLabel;
	private JLabel staticLabelTotal;
	private JLabel investmentLabel;
	private JLabel investmentLabelTotal;
	private JLabel totalLabel;
	private JLabel totalLabelTotal;
	private double staticTotal;
	private double investmentTotal;
	private double total;
	private static int counter;
	private int fileLength;
	
	public MainPanel(){
		super("Money Manager");
		try {
			//to initialize file
			FileWriter writer = new FileWriter("data.txt", true);
			writer.write("");
			writer.close();
			
			newReadInstance();
			fileLength = 0;
			while (buffer.readLine() != null)
				fileLength = fileLength + 1;
		}
		catch (IOException e) {
			MainFrame.showErrorMessage("Error loading data from file.");
		}
		loadMain();
	}
	
	public void loadMain(){
		//initialization
		main = new JPanel();
		totalPanel = new JPanel();
		staticTotalPanel = new JPanel();
		investmentTotalPanel = new JPanel();
		
		main.setLayout(null);
		main.setBackground(MainFrame.BG);
		
		icon = new ImageIcon("Images/logo.png");
		logo = new JLabel(icon);
		logo.setBounds(200, -10, 700, 200);
		main.add(logo);
		
		readStaticCostsAndInvestments();
		
		MainFrame.setPanel(totalPanel, MainFrame.NEUTRAL, 400, 150, 300, 300);
		main.add(totalPanel);
		
		MainFrame.setPanel(staticTotalPanel, MainFrame.NEUTRAL, 50, 150, 300, 300);
		main.add(staticTotalPanel);

		MainFrame.setPanel(investmentTotalPanel, MainFrame.NEUTRAL, 750, 150, 300, 300);
		main.add(investmentTotalPanel);
		
		allStaticCosts = new JButton("See all static costs");
		allStaticCosts.addActionListener(this);
		MainFrame.setButton(allStaticCosts, "allStaticCosts", 100, 490, 200, 30);
		main.add(allStaticCosts);
		
		addStaticCost = new JButton("Add a static cost");
		addStaticCost.addActionListener(this);
		MainFrame.setButton(addStaticCost, "addStaticCost", 100, 540, 200, 30);
		main.add(addStaticCost);
		
		allInvestments = new JButton("See all investments");
		allInvestments.addActionListener(this);
		MainFrame.setButton(allInvestments, "allInvestments", 800, 490, 200, 30);
		main.add(allInvestments);
		
		addInvestment = new JButton("Add an investment");
		addInvestment.addActionListener(this);
		MainFrame.setButton(addInvestment, "addInvestment", 800, 540, 200, 30);
		main.add(addInvestment);

		staticTotal = calculateCostTotal();
		investmentTotal = calculateReturn();
		total = staticTotal + investmentTotal;
		
		//setting custom colours depending on the total gain/loss
		if (staticTotal > 0)
			staticTotalPanel.setBackground(MainFrame.POSITIVE);
		else if (staticTotal < 0)
			staticTotalPanel.setBackground(MainFrame.NEGATIVE);
		
		//investments will always have a positive return
		if (investmentTotal > 0)
			investmentTotalPanel.setBackground(MainFrame.POSITIVE);
		
		if (total > 0)
			totalPanel.setBackground(MainFrame.POSITIVE);
		else if (total < 0)
			totalPanel.setBackground(MainFrame.NEGATIVE);
		
		staticLabel = MainFrame.setLabel(staticLabel, "Your added costs and expenses:", (staticTotalPanel.getWidth() - 300) / 2, 50, 300, 50);
		staticLabelTotal = MainFrame.setLabel(staticLabelTotal, "$" + String.format("%.2f", staticTotal), (staticTotalPanel.getWidth() - 300) / 2, 100, 300, 50);
		
		staticTotalPanel.add(staticLabel);
		staticTotalPanel.add(staticLabelTotal);
		
		investmentLabel = MainFrame.setLabel(investmentLabel, "Your return on investments:", (investmentTotalPanel.getWidth() - 300) / 2, 50, 300, 50);
		investmentLabelTotal = MainFrame.setLabel(investmentLabelTotal, "$" + String.format("%.2f", investmentTotal), (investmentTotalPanel.getWidth() - 300) / 2, 100, 300, 50);
		
		investmentTotalPanel.add(investmentLabel);
		investmentTotalPanel.add(investmentLabelTotal);
		
		totalLabel = MainFrame.setLabel(totalLabel, "Your total gain/loss thus far:", (totalPanel.getWidth() - 300) / 2, 50, 300, 50);
		totalLabelTotal = MainFrame.setLabel(totalLabelTotal, "$" + String.format("%.2f", total), (totalPanel.getWidth() - 300) / 2, 100, 300, 50);
		
		totalPanel.add(totalLabel);
		totalPanel.add(totalLabelTotal);

		add(main);
	}
	
	public void readStaticCostsAndInvestments() {
		try {
			String line = "";
			newReadInstance();
		
			boolean isInvestment = false;
			double principal = 0;
			double growth = 0;
			int compoundRate = 0;
			String name = "";
			double cost = 0;
			int year = 0;
			int month = 0;
			int day = 0;
			Node hold;
			
			MainFrame.staticList.purge();
			MainFrame.investmentList.purge();
			
			for (int i = 0; i < fileLength; i = i + 1) {
				line = buffer.readLine();
				//format of the file allows for some hard coding
				//static: false^name^cost^year^month^day
				//investment: true^name^principal^percent growth^compound rate^year^month^day

				isInvestment = Boolean.parseBoolean(getInfoFromFile(line));
				counter = counter + 1;
				
				name = getInfoFromFile(line);
				counter = counter + 1;
				
				if (isInvestment) {
					principal = Double.parseDouble(getInfoFromFile(line));
					counter = counter + 1;
					
					growth = Double.parseDouble(getInfoFromFile(line));
					counter = counter + 1;
					
					compoundRate = Integer.parseInt(getInfoFromFile(line));
					counter = counter + 1;
				}
				else {
					cost = Double.parseDouble(getInfoFromFile(line));
					counter = counter + 1;
				}
				
				year = Integer.parseInt(getInfoFromFile(line));
				counter = counter + 1;
				
				month = Integer.parseInt(getInfoFromFile(line));
				counter = counter + 1;
				
				day = Integer.parseInt(getInfoFromFile(line));
				
				hold = new Node();
				if (isInvestment) {
					hold.setData(new Investment(name, principal, growth, compoundRate, LocalDate.of(year, month, day)));
					MainFrame.investmentList.addLast(hold);
				}
				else {
					hold.setData(new Cost(name, cost, LocalDate.of(year, month, day)));
					MainFrame.staticList.addLast(hold);
				}
				counter = 0;
			}
		}
		catch (IOException e) {
			MainFrame.showErrorMessage("Error reading from data file.");
		}
	}

	//method used with format in file
	public String getInfoFromFile(String line) {
		String hold = "";
		while (counter < line.length() && line.charAt(counter) != '^') {
			hold = hold + line.charAt(counter);
			counter = counter + 1;
		}
		return hold;
	}
	
	//new read instance to start reading from top
	public void newReadInstance() {
		try {
			reader = new FileReader("data.txt");
			buffer = new BufferedReader(reader);
		}
		catch(IOException e) {
			MainFrame.showErrorMessage("Error reading file.");
		}
	}
	
	public double calculateCostTotal() {
		double total = 0;
		for (int i = 0; i < MainFrame.staticList.getSize(); i = i + 1)
			total = total + MainFrame.staticList.getNode(i).getData().getCost();
		return total;
	}
	
	public double calculateReturn() {
		double total = 0;
		for (int i = 0; i < MainFrame.investmentList.getSize(); i = i + 1) {
			total = total + ((Investment)(MainFrame.investmentList.getNode(i).getData())).calculateReturn();
		}
		return total;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("allStaticCosts")) {
			MainFrame.loadTable(true, false);
		}
		else if (e.getActionCommand().equals("allInvestments")) {
			MainFrame.loadTable(false, false);
		}
		//initialize the "add cost/investment" tab
		else if (e.getActionCommand().equals("addStaticCost") || e.getActionCommand().equals("addInvestment")) {
			boolean isStatic = e.getActionCommand().equals("addStaticCost");
			String title = "";
			final String[] POSSIBILITIES = {"4 months", "6 months", "12 months"};
			
			JPanel add = new JPanel();
			add(add);
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
				
				add.add(cost);
				
				add.setPreferredSize(new Dimension(300, 100));
			}
			else {
				title = "Parameters for investment";
				
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
					Node hold = new Node();
					if (isStatic) {
						hold.setData(new Cost(nameField.getText(), Double.parseDouble(costField.getText()), LocalDate.now()));
						MainFrame.staticList.addLast(hold);			
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
						hold.setData(new Investment(nameField.getText(), Double.parseDouble(costField.getText()),
								Double.parseDouble(growthField.getText()), compoundRateInt, LocalDate.now()));
						MainFrame.investmentList.addLast(hold);
					}
					JOptionPane.showMessageDialog(this, "Added successfully!");
				}
				MainFrame.rewriteFile();
				MainFrame.loadMain();
			}
		}
	}
}
