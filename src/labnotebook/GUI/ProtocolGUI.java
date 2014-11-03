package labnotebook.GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import labnotebook.entries.*;

public class ProtocolGUI extends InputDialog {
	private static final long serialVersionUID = 1L;
	private JTextArea outTextArea;
	private JTextField inputField;
	private ProtocolEntry protocol;
	
	public ProtocolGUI(Main main) {
		super(main);
		protocol = new ProtocolEntry();
		initialize();
	}

	protected void initialize(){
		super.initialize();
		
		SpringLayout mainLayout = new SpringLayout();
		setLayout(mainLayout);
		setSize(new Dimension(400, 375));
		setTitle("Add Protocol");
		
		JLabel nameLabel = new JLabel(protocol.getName());

		outTextArea = new JTextArea();
		outTextArea.setFont(new Font("monospaced", Font.PLAIN, 12));
		outTextArea.setWrapStyleWord(true);
		outTextArea.setEditable(false);
		outTextArea.setFocusable(false);
		JScrollPane outScrollPane = new JScrollPane(outTextArea);
		outScrollPane.setPreferredSize(new Dimension(375, 200));
		
		InputPanel inputPanel = new InputPanel();
		
		add(nameLabel);
		add(outScrollPane);
		add(inputPanel);	
		
		mainLayout.putConstraint(SpringLayout.WEST, nameLabel, 10, 
				SpringLayout.WEST, getContentPane());
		mainLayout.putConstraint(SpringLayout.WEST, outScrollPane, 10, 
				SpringLayout.WEST, getContentPane());
		mainLayout.putConstraint(SpringLayout.WEST, inputPanel, 10, 
				SpringLayout.WEST, getContentPane());
		mainLayout.putConstraint(SpringLayout.NORTH, nameLabel, 10, 
				SpringLayout.NORTH, getContentPane());
		mainLayout.putConstraint(SpringLayout.NORTH, outScrollPane, 10, 
				SpringLayout.SOUTH, nameLabel);
		mainLayout.putConstraint(SpringLayout.NORTH, inputPanel, 10, 
				SpringLayout.SOUTH, outScrollPane);
		
		setVisible(true);
	}
	
	public class InputPanel extends JPanel{
		private JCheckBox saveCheck;
		private static final long serialVersionUID = 1L;
		public InputPanel(){
			SpringLayout inputLayout = new SpringLayout();
			setLayout(inputLayout);
			setPreferredSize(new Dimension(375, 100));
			JLabel inputLabel = new JLabel("Add Step:");
			inputField = new JTextField();
			inputField.setPreferredSize(new Dimension(375, 20));
			inputField.addActionListener(new AddStepListener());
			JButton doneButton = new JButton("Done");
			doneButton.addActionListener(new DoneListener());
			saveCheck = new JCheckBox("Save protocol as file");
			saveCheck.setSelected(true);
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new CancelListener());
			

			inputLayout.putConstraint(SpringLayout.WEST, inputLabel, 0, 
					SpringLayout.WEST, this);
			inputLayout.putConstraint(SpringLayout.WEST, inputField, 0, 
					SpringLayout.WEST, this);
			inputLayout.putConstraint(SpringLayout.NORTH, inputField, 10, 
					SpringLayout.SOUTH, inputLabel);
			inputLayout.putConstraint(SpringLayout.EAST, doneButton, -10, 
					SpringLayout.WEST, cancelButton);
			inputLayout.putConstraint(SpringLayout.NORTH, doneButton, 10, 
					SpringLayout.SOUTH, inputField);
			inputLayout.putConstraint(SpringLayout.WEST, saveCheck, 0, 
					SpringLayout.WEST, this);
			inputLayout.putConstraint(SpringLayout.NORTH, saveCheck, 10, 
					SpringLayout.SOUTH, inputField);
			inputLayout.putConstraint(SpringLayout.EAST, cancelButton, -1, 
					SpringLayout.EAST, this);
			inputLayout.putConstraint(SpringLayout.NORTH, cancelButton, 10, 
					SpringLayout.SOUTH, inputField);
			
			this.add(inputLabel);
			this.add(inputField);
			this.add(doneButton);
			this.add(saveCheck);
			this.add(cancelButton);
		}
		
		public class DoneListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				protocol.createOutput();
				if (saveCheck.isSelected()){
					PrintStream out;
					try {
						out = new PrintStream(new File(protocol.getName() + protocol.getFileType()));
						for (String s : protocol.getOutput()){
							out.println(s);
						}
						out.close();
					} catch (FileNotFoundException e) {
					}
				}
				main.printArray(protocol.getOutput());
				dispose();
			}
		}
		
		public class AddStepListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				protocol.addStep(protocol.getStepNumber() +
						inputField.getText());
				outTextArea.append(protocol.getLatestStep() + "\n");
				inputField.setText("");
			}
		}
		
		public class CancelListener implements ActionListener{
			public void actionPerformed (ActionEvent event){
				dispose();
			}
		}
	}

	protected class ProtocolEntry extends LabEntry{
		private ArrayList<String> steps;
		
		public ProtocolEntry(){
			super();
			name = new String(JOptionPane.showInputDialog(main, "Protocol Name?"));
			type = new String("Protocol");
			fileType = new String(".prt");
			steps = new ArrayList<String>();
		}
		
		public void addStep(String s){
			steps.add(s);
		}
		
		public String getLatestStep(){
			return steps.get(steps.size()-1);
		}
		
		protected void createOutput(){
			output.add(createTitle());
			output.add("");
			for (String s : steps){
				output.add(s);
			}
		}
		
		public String getStepNumber(){
			String s = new String((steps.size() + 1) + ". ");
			while (s.length() < 4){
				s += " ";
			}
			return s;
		}
	}
}