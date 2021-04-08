package ia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainFrame extends JFrame{
	
	static JFrame frame;
	static FileReader reader;
	static BufferedReader buffer;
	static LinkedList investmentList;
	static LinkedList staticList;
	
	static final Color BG = new Color (130, 178, 255);
	static final Color POSITIVE = new Color(76, 255, 0);
	static final Color NEGATIVE =  new Color(255, 77, 23);
	static final Color NEUTRAL = new Color(200, 200, 200);
	
    public static void main (String[] args) throws IOException{
    	initializeVariables();
    	
    	File data = new File("data.txt");
    	
	 	frame = new LoginPanel();
	 	setFrameOptions();
    }
    
    static void setFrameOptions() {
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(1100, 700);
    	frame.setResizable(false);
     	frame.setVisible(true);
    }
    
    public static void loadMain(){
    	frame.dispose();
    	frame = new MainPanel();
    	setFrameOptions();
    }
    
    public static void loadTable(boolean isStatic, boolean isAscending) {
    	frame.dispose();
    	frame = new TableFrame(isStatic, isAscending);
    	setFrameOptions();
    }

	public static void setButton(JButton button, String identifier, int x, int y, int w, int h) {
		button.setBackground(Color.WHITE);
    	button.setActionCommand(identifier);
    	button.setBounds(x, y, w ,h);
	}
	
	public static void setPanel(JPanel panel, Color c, int x, int y, int w, int h) {
		panel.setLayout(null);
		panel.setBackground(c);
		panel.setBounds(x, y, w, h);
	}
	
	public static JLabel setLabel(JLabel label, String content, int x, int y, int w, int h) {
		label = new JLabel(content, JLabel.CENTER);	
		label.setFont(new Font("Calibri", Font.PLAIN, 20));
		label.setBounds(x, y, w, h);
		return label;
	}
	
	public static void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(frame, message, "", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void initializeVariables() {
		investmentList = new LinkedList();
		staticList = new LinkedList();
	}
	
	public static void rewriteFile(){
		//flushes out data file
		try {
			PrintWriter pw = new PrintWriter("data.txt");
			pw.close();
			
			FileWriter writer = new FileWriter("data.txt", true);
			
			writer.write(staticList.toString(true));
			//used to separate information in event that both investment and static list hold info
			if (staticList.getSize() > 0 && investmentList.getSize() > 0)
				writer.write("\n");
			writer.write("");
			writer.write(investmentList.toString(false));
			writer.close();
		}
		catch (IOException e) {
			showErrorMessage("Error writing on data file.");
		}
	}
}