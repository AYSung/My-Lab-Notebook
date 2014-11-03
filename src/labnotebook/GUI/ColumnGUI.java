package labnotebook.GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.text.*;

import javax.swing.*;
import labnotebook.entries.*;

public class ColumnGUI extends InputDialog {
	private static final long serialVersionUID = 1L;
	private JTextField fullNameField, manufacturerField;
	private JComboBox<String> typeBox;
	private JSpinner volSpinner, flowSpinner, pressureSpinner, sampleSpinner;
	private ColumnEntry column;
	
	public ColumnGUI(Main main){
		super(main);
	}
	
	protected void initialize(){
		super.initialize();
		setLayout(new GridLayout(9, 1));
		setSize(new Dimension(420, 300));
		setTitle("Add Column");
		
		fullNameField = new JTextField(10);
		JLabel rowLabel = new JLabel("Full name:");
		
		initializeRow(rowLabel, fullNameField);

		
	}
	
	private void initializeRow(JLabel label, JTextField textField){
		JPanel rowPanel = new JPanel();
		SpringLayout rowLayout = new SpringLayout();
//		rowLayout.putConstraint(SpringLayout.EAST, );
		
		add(rowPanel);
	}
	
	private class ColumnEntry extends LabEntry{
		private ColumnEntry(){
			super();
		}
		
		protected void createOutput(){
			output.add(createTitle());
			output.add("");
			for (String s : getProperties()){
				output.add(s);
			}
		}
	}
	
	public ArrayList<String> getProperties(){
		return new ArrayList<String>();
	}
}
