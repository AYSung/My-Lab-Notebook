package labnotebook.GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.text.*;
import javax.swing.*;

import labnotebook.entries.*;

public class BufferGUI extends InputDialog{
	private static final long serialVersionUID = 1L;
	private int components;
	private BufferNamePanel namePanel;
	private BufferInputPanel inputPanel;
	private BufferButtonPanel buttonPanel;
	private BufferEntry buffer;
	
	public BufferGUI(Main main, int n){
		super(main);
		this.components = n;
		initialize();
	}
	
	protected void initialize(){
		super.initialize();
		
		SpringLayout mainLayout = new SpringLayout();
		setLayout(mainLayout);
		setSize(new Dimension(420, components * 30 + 170));
		setTitle("Add Buffer");

		namePanel = new BufferNamePanel();
		inputPanel = new BufferInputPanel();
		buttonPanel = new BufferButtonPanel();
		
		add(namePanel);
		add(inputPanel);
		add(buttonPanel);
		
		mainLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, namePanel,
				0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		mainLayout.putConstraint(SpringLayout.NORTH, namePanel, 10, 
				SpringLayout.NORTH, this.getContentPane());
		mainLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, inputPanel,
				0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		mainLayout.putConstraint(SpringLayout.NORTH, inputPanel, 10,
				SpringLayout.SOUTH, namePanel);
		mainLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buttonPanel,
				0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		mainLayout.putConstraint(SpringLayout.NORTH, buttonPanel, 20,
				SpringLayout.SOUTH, inputPanel);
		
		setVisible(true);
	}
	
	public class BufferNamePanel extends JPanel{
		private static final long serialVersionUID = 1L;
		private JTextField bNameField, bVolField;
		private BUnitBox bUnitBox;
		
		public BufferNamePanel(){
			super();
			setLayout(new FlowLayout());
			JLabel bNameLabel = new JLabel("Buffer name: ");
			JLabel bVolLabel = new JLabel("Total volume: ");
			bNameField = new JTextField (10);
			bVolField = new JTextField(4);
			bUnitBox = new BUnitBox();
			add(bNameLabel);
			add(bNameField);
			add(bVolLabel);
			add(bVolField);
			add(bUnitBox);
		}
		
		protected class BUnitBox extends JComboBox<String>{
			private static final long serialVersionUID = 1L;
			public BUnitBox(){
				super();
				addItem("L");
				addItem("ml");
			}
			public String getUnit(){
				return " " + getSelectedItem().toString();
			}
		}
		
		private String getBName(){
			return bNameField.getText();
		}
		
		private double getBVol(){
			double bVol = Double.parseDouble(bVolField.getText());
			if (bUnitBox.getSelectedIndex() == 0){
				bVol *= 1000;
			}
			return bVol;
		}
	}
	
	private class BufferInputPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		private ArrayList<InputRow> inputs;
		
		public BufferInputPanel(){
			super();
			setLayout(new GridLayout(components + 1, 1));
			setPreferredSize(new Dimension(420, (components + 1) * 30));
			add(new LabelPanel());
			inputs = new ArrayList<InputRow>();
			for (int i = 0; i < components; i++){
				inputs.add(new InputRow());
				add(inputs.get(i));
			}
		}
		
		public ArrayList<InputRow> getInputs(){
			return inputs;
		}
		
		private class LabelPanel extends JPanel{
			private static final long serialVersionUID = 1L;
			public LabelPanel(){
				super();
				SpringLayout labelLayout = new SpringLayout();
				setLayout(labelLayout);
				setPreferredSize(new Dimension(getWidth(), 20));
				JLabel cNameLabel = new JLabel("Chemical Name: ");
				JLabel cStockLabel = new JLabel("[STOCK]");
				JLabel cFinalLabel = new JLabel("[FINAL]");
				add(cNameLabel);
				add(cStockLabel);
				add(cFinalLabel);
				labelLayout.putConstraint(SpringLayout.WEST, cNameLabel, 40, 
						SpringLayout.WEST, this);
				labelLayout.putConstraint(SpringLayout.WEST, cStockLabel, 52, 
						SpringLayout.EAST, cNameLabel);
				labelLayout.putConstraint(SpringLayout.WEST, cFinalLabel, 71, 
						SpringLayout.EAST, cStockLabel);
			}
		}
		
		private class InputRow extends JPanel{
			private static final long serialVersionUID = 1L;
			private JTextField nameField, sConcBox, fConcBox;
			private SUnitBox sUnitBox;
			private FUnitBox fUnitBox;
			
			public InputRow(){
				super();
				nameField = new JTextField(10);
				sConcBox = new JTextField(4);
				sUnitBox = new SUnitBox();
				fConcBox = new JTextField(4);
				fUnitBox = new FUnitBox();
				fUnitBox.addActionListener(new PercentUnitListener());
				
				add(nameField);
				add(sConcBox);
				add(sUnitBox);
				add(fConcBox);
				add(fUnitBox);
			}
			
			private class SUnitBox extends JComboBox<String>{
				private static final long serialVersionUID = 1L;
				public SUnitBox(){
					super();
					addItem("M");
					addItem("mM");
					addItem("g/mol");
				}
				protected String getUnit(){
					return " " + getSelectedItem().toString();
				}
			}
			
			private class FUnitBox extends JComboBox<String>{
				private static final long serialVersionUID = 1L;
				public FUnitBox(){
					super();
					addItem("mM");
					addItem("M");
					addItem("%");
				}
				protected String getUnit(){
					return " " + getSelectedItem().toString();
				}
			}
			
			private String getCName(){
				return nameField.getText();
			}
			
			private double getSConc(){
				double sConc;
				if (fUnitBox.getSelectedIndex() != 2){
					sConc = Double.parseDouble(sConcBox.getText());
					if (sUnitBox.getSelectedIndex() == 0){
						sConc *= 1000;
					}
				} else {
					sConc = 100;
				}
				return sConc;
			}
			
			private double getFConc(){
				double fConc = Double.parseDouble(fConcBox.getText());
				if (fUnitBox.getUnit().equals(" M")){
					fConc *= 1000;
				}
				return fConc;
			}
			
			private class PercentUnitListener implements ActionListener{
				public void actionPerformed(ActionEvent event){
			        if (((FUnitBox)event.getSource()).getSelectedIndex() == 2){
			        	sUnitBox.setEnabled(false);
			        	sConcBox.setEnabled(false);
			        	sConcBox.setText("");
			        } else {
			        	sUnitBox.setEnabled(true);
			        	sConcBox.setEnabled(true);
			        }
				}
			}
		}
	}
	
	private class BufferButtonPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		private JCheckBox saveCheck;
		public BufferButtonPanel(){
			super();
			SpringLayout buttonLayout = new SpringLayout();
			setLayout(buttonLayout);
			setPreferredSize(new Dimension(420, 30));
			saveCheck = new JCheckBox("Save buffer as file");
			saveCheck.setSelected(true);
			JButton addButton = new JButton("Add Buffer");
			addButton.addActionListener(new AddBufferListener());
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new CancelListener());
			add(saveCheck);
			add(addButton);
			add(cancelButton);
			buttonLayout.putConstraint(SpringLayout.WEST, saveCheck, 10, 
					SpringLayout.WEST, this);
			buttonLayout.putConstraint(SpringLayout.EAST, cancelButton, -10, 
					SpringLayout.EAST, this);
			buttonLayout.putConstraint(SpringLayout.EAST, addButton, -10, 
					SpringLayout.WEST, cancelButton);
		}
		
		private class CancelListener implements ActionListener {
			public void actionPerformed(ActionEvent event){
				dispose();
			}
		}
		
		private class AddBufferListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				try {
					buffer = new BufferEntry();

					if (saveCheck.isSelected()){
						PrintStream out;
						try {
							out = new PrintStream(new File(buffer.getName() + buffer.getFileType()));
							for (String s : buffer.getOutput()){
								out.println(s);
							}
							out.close();
						} catch (FileNotFoundException e) {
						}
					}
					main.printArray(buffer.getOutput());
					dispose();
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(getInputDialog(), "Please check all fields have valid values.");
				}
			}
		}
	}
	
	private class BufferEntry extends RecipeEntry {
		
		private BufferEntry(){
			super();
				name = new String(namePanel.getBName()); 
				type = new String("Buffer");
				fileType = new String(".buf");
				vol = namePanel.getBVol();
				vUnit = new String(namePanel.bUnitBox.getUnit());
				for (int i = 0; i < components; i++){
					chemicals.add(new BChemical(i));
				}
			createOutput();
		}
		
		private class BChemical extends ChemicalEntry{
			
			private BChemical(int i){
				super();
				name = inputPanel.getInputs().get(i).getCName();
				sConc = inputPanel.getInputs().get(i).getSConc();
				sUnit = inputPanel.getInputs().get(i).sUnitBox.getUnit();
				fConc = inputPanel.getInputs().get(i).getFConc();
				fUnit = inputPanel.getInputs().get(i).fUnitBox.getUnit();
				
				if (!sUnit.equals(" g/mol")){
					wet = true;
				} else {
					wet = false;
				}
				if (wet){
					quant = vol * (fConc / sConc);
					qUnit = " ml";
				} else {
					quant = vol * (sConc * fConc) / Math.pow(10, 6);
					qUnit = " g";
				}
				if (fUnit.equals(" %")){
					sUnit = fUnit;
					qUnit = " g";
				}
			}
			
			public ArrayList<String> getProperties(){
				ArrayList<String> properties = new ArrayList<String>();
				DecimalFormat df = new DecimalFormat("#.##");
				if (fUnit.equals(" mM") || fUnit.equals(" %")){
					properties.add(df.format(fConc) + fUnit);
				} else if (fUnit.equals(" M")){
					properties.add(df.format(fConc/1000) + fUnit);
				}
				properties.add(name);
				if (wet){
					if (sUnit.equals(" M")){
						properties.add(df.format(sConc/1000) + sUnit);
					} else if (sUnit.equals(" mM") || sUnit.equals(" %")){
						properties.add(df.format(sConc) + sUnit);
					}
				} else {
					properties.add(df.format(sConc) + sUnit);
				}
				properties.add(df.format(quant) + qUnit);
				
				return properties;
			}
		}
		
		protected String createTitle(){
			String s = super.createTitle();
			DecimalFormat df = new DecimalFormat("#.##");
			s += " (";
			if (namePanel.bUnitBox.getUnit().equals(" L")){
				s += (df.format(vol/1000)) + vUnit + ")";
			} else if (namePanel.bUnitBox.getUnit().equals(" ml")){
				s += (df.format(vol) + vUnit + ")");
			}
			return s;
		}
		
		private ArrayList<String> createRowHeaders(){
			ArrayList<String> headers = new ArrayList<String>();
			headers.add("[FINAL]");
			headers.add("CHEMICAL");
			headers.add("[STOCK]");
			headers.add("QUANTITY");
			return headers;
		}
		
		protected void createOutput(){
			output.add(createTitle());
			output.add("");
			output.add(createRow(createRowHeaders()));
			for (ChemicalEntry c : chemicals){
				output.add(createRow(c.getProperties()));
			}
		}
	}
}
