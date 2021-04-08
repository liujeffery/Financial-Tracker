package ia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LoginPanel extends JFrame implements ActionListener{
	
	private JTextField loginUserText;
	private JPasswordField loginPassText;
	private JTextField registerUserText;
	private JPasswordField registerPassText;
	private JPanel login;
	private JPanel register;
	private CardLayout card;
	private Container c;
	private boolean isLogin;
	private int registerX;
	private int tries;
	private FileWriter writer;
	private FileReader reader;
	private BufferedReader buffer;
	private JLabel user;
	private JLabel pass;
	private JLabel logo;
	private ImageIcon icon;
	private JButton loginButton;
	private JButton registerButton;
	
	public LoginPanel() throws IOException{
		//basic creation of the frame
		super("Money Manager");
		
		writer = new FileWriter("login.txt", true);
		
		//initializing key variables
		registerX = 0;
		isLogin = true;
		tries = 5;
		c = getContentPane();
		card = new CardLayout();
		login = new JPanel();
		register = new JPanel();
		c.setLayout(card);
		
		addFields(true, login);
		addFields(false, register);
		
		//adding panels into card layout to show
		c.add(login, "login");
		c.add(register, "register");
		card.show(c, "login");
	}
	
	//adding in the important buttons and text fields for each panel
	public void addFields(boolean isLoginPanel, JPanel panel) {
		panel.setLayout(null);
    	panel.setBackground(Color.WHITE);
    	
    	//elements of login screen
    	user = new JLabel("Username: ");
    	user.setBounds(400, 300, 70, 30);
    	panel.add(user);
    	
    	icon = new ImageIcon("Images/logo.png");
    	logo = new JLabel(icon);
    	logo.setBounds(230, 80, 700, 200);
    	panel.add(logo);

    	pass = new JLabel("Password: ");
    	pass.setBounds(400, 350, 70, 30);
    	panel.add(pass);
    	
    	//dependent on whether or not program is showing login or register screen
    	if (isLoginPanel) {
        	loginUserText = new JTextField();
        	loginUserText.setBounds(490, 300, 150, 30);
        	panel.add(loginUserText);
        	
        	loginPassText = new JPasswordField();
        	loginPassText.setBounds(490, 350, 150, 30);
        	panel.add(loginPassText);
        	
			registerX = 540;
    		
    		loginButton = new JButton("Login");
    		MainFrame.setButton(loginButton, "login", 400, 400, 100, 30);
    		loginButton.addActionListener(this);
        	panel.add(loginButton);
		}
    	
    	else {
    		//register x is the x coordinate of the register button
    		registerX = 470;
    		registerUserText = new JTextField();
        	registerUserText.setBounds(490, 300, 150, 30);
        	panel.add(registerUserText);
        	
        	registerPassText = new JPasswordField();
        	registerPassText.setBounds(490, 350, 150, 30);
        	panel.add(registerPassText);
    	}
    	
    	registerButton = new JButton("Register");
    	MainFrame.setButton(registerButton, "register", registerX, 400, 100, 30);
    	registerButton.addActionListener(this);
    	panel.add(registerButton);
	}
	
	//checking if login credentials are correct
	public boolean checkLogin(String user, String pass) {
		String line = "";
		try {
			newReadInstance();
			line = buffer.readLine();
			//if there is nothing inside the file, then there are no registered users
			if (line == null) {
				showErrorMessage("No registered user, please use register to enter!");
				return false;
			}
			//both user and password must be met to proceed
			if (user.equals(line)) {
				line = buffer.readLine();
				if (pass.equals(line)) 
					return true;
			}
			//user has 5 tries to input username and password correctly
			if (tries == 0) {
				System.exit(0);
			}
			showErrorMessage("Incorrect credentials! You have " + tries + " tries.");
			tries = tries - 1;
			return false;
		}
		catch (IOException e) {
			showErrorMessage("Unexpected error reading file.");
		}
		return false;
	}
	
	//method to show popup window when something happens
	public void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(c, message, "", JOptionPane.ERROR_MESSAGE);
	}
	
	//used to reread the data file from the beginning each time
	public void newReadInstance() {
		try {
			reader = new FileReader("login.txt");
			buffer = new BufferedReader(reader);
		}
		catch(IOException e) {
			showErrorMessage("Error reading file.");
		}
	}
	
	//reacting based on the buttons that are pressed
	public void actionPerformed(ActionEvent e){
		if (e.getActionCommand() == "login") {
			String user = this.loginUserText.getText();
			String pass = "";
			
			//password is encrypted by shifting characters up two ascii codes
			for (int i = 0; i < this.loginPassText.getPassword().length; i = i + 1) {
				pass = pass + (char)(this.loginPassText.getPassword()[i] + 2);
			}
			
			//if credentials are correct, proceed
			if (checkLogin(user, pass)) {
				try {
					MainFrame.loadMain();
				}
				catch(Exception e1) {
					showErrorMessage("Error loading next panel.");
				}
			}
		}
		//if original page is login, switches to the register page
		else if (e.getActionCommand() == "register") {
			if (isLogin) {
				card.show(c, "register");
			}
			else {
				String user = this.registerUserText.getText();
				String pass = "";
				boolean validRegister = true;
				for (int i = 0; i < this.registerPassText.getPassword().length; i = i + 1) {
					pass = pass + (char)(this.registerPassText.getPassword()[i] + 2);
				}
				newReadInstance();
				try {
					if (buffer.readLine() != null) {
						showErrorMessage("There is already a registered user!");
						validRegister = false;
					}
				}
				catch(IOException e1) {
					showErrorMessage("Unexpected error reading file.");
				}
				if (user.length() <= 5) {
					showErrorMessage("Username must be more than 5 characters!");
					validRegister = false;
				}
				if (pass.length() <= 8) {
					showErrorMessage("Password must be more than 10 characters!");
					validRegister = false;
				}
				if (validRegister) {
					try {
						writer.write(user + "\n");
						writer.write(pass);
						JOptionPane.showMessageDialog(c, "User successfully registered!\n Keep the information handy, there is no way to change the information through the program.");
						writer.close();
					}
					catch(IOException e1) {
						showErrorMessage("Error writing on file.");
					}
				}
				card.show(c, "login");
			}
			isLogin = !isLogin;
		}
	}
}
