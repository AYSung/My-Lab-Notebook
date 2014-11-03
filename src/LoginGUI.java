import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import labnotebook.GUI.*;

public class LoginGUI extends JDialog{
	private static final long serialVersionUID = 1L;
	private String username = new String("AYSung");
	private char[] password = {'1', '2', '3', '4'};
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	public LoginGUI(JFrame parent){
		SpringLayout masterLayout = new SpringLayout();
		this.setLayout(masterLayout);
		this.setSize(new Dimension(260,150));
		this.setTitle("LOGIN");
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setModal(true);
		
		JPanel usernamePanel = new JPanel(new FlowLayout());
		JLabel usernameLabel = new JLabel("Username: ");
		usernameField = new JTextField(15);
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameField);
		
		JPanel passwordPanel = new JPanel(new FlowLayout());
		JLabel passwordLabel = new JLabel("Password: ");
		passwordField = new JPasswordField(15);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new LoginListener());
		
		this.add(usernamePanel);
		this.add(passwordPanel);
		this.add(loginButton);
		
		masterLayout.putConstraint(SpringLayout.NORTH, usernamePanel, 5, 
				SpringLayout.NORTH, this);
		masterLayout.putConstraint(SpringLayout.NORTH, passwordPanel, 5, 
				SpringLayout.SOUTH, usernamePanel);
		masterLayout.putConstraint(SpringLayout.NORTH, loginButton, 10, 
				SpringLayout.SOUTH, passwordPanel);
		masterLayout.putConstraint(SpringLayout.WEST, usernamePanel, 5, 
				SpringLayout.WEST, this);
		masterLayout.putConstraint(SpringLayout.WEST, passwordPanel, 5, 
				SpringLayout.WEST, this);
		masterLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, loginButton, 
				0, SpringLayout.HORIZONTAL_CENTER, passwordPanel);
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
//		new LoginGUI();
	}
	
	public class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			boolean usernameCorrect = usernameField.getText().equalsIgnoreCase(username);
			boolean passwordCorrect = Arrays.equals(passwordField.getPassword(), password);
			if (usernameCorrect && passwordCorrect){
				JOptionPane.showMessageDialog(null, "Login Successful");
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Please try again");
			}
		}	
	}
}
