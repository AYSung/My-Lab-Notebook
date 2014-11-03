package labnotebook.GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.text.*;

public class Main extends JFrame{
	private static final long serialVersionUID = 1L; 
	private Main mainFrame;
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy.MMMdd");
	private Date date = new Date();
	private JTextArea outputArea;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Main();
				} catch (Exception e) {
				}
			}
		});
	}

	private Main() {
		super();
		initialize();
	}

	private void initialize() {
		mainFrame = this;
		setBounds(100, 100, 600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		centerWindow(this);
		setLayout(new BorderLayout());
		setTitle("My Lab Notebook");
		setResizable(false);
		
		MenuBar menuBar = new MenuBar();
		
		outputArea = new JTextArea();
		outputArea.setFont(new Font("monospaced", Font.PLAIN, 12));
		outputArea.setWrapStyleWord(true);
		outputArea.setEditable(false);
		JScrollPane outScrollPane = new JScrollPane(outputArea);
		
		setJMenuBar(menuBar);
		add(outScrollPane, BorderLayout.CENTER);
//		mainGUIFrame.add(timerScrollPane, BorderLayout.EAST);
		
		setVisible(true);	
	}
	
	public static void centerWindow(Window w){
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - w.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - w.getHeight()) / 2);
		w.setLocation(x, y);
	}
	
	public void printArray(ArrayList<String> input){
		for (String s: input){
			this.outputArea.append(s + "\n");
		}
		this.outputArea.append("\n");
		this.outputArea.append("\n");
	}
	
	private ArrayList<String> createOutput(String s, ArrayList<String> output){
		int end = s.indexOf("\n");
		if (end != -1){
			output.add(s.substring(0, end));
			createOutput(s.substring(end + 1, s.length()), output);
		}
		return output;
	}
	
	private ArrayList<String> getFileContent(File f) throws FileNotFoundException{
		ArrayList<String> fileContent = new ArrayList<String>();
		Scanner fileScanner = new Scanner(f);
		while (fileScanner.hasNext()){
			fileContent.add(fileScanner.nextLine());
		}
		fileScanner.close();
		return fileContent;
	}

	private class MenuBar extends JMenuBar{
		private static final long serialVersionUID = 1L;
		private MenuBar(){
			super();
			JMenu menu;
			JMenuItem menuItem, subMenu;
						
			menu = new JMenu("File");
			menu.setMnemonic(KeyEvent.VK_F);
			
			menuItem = new JMenuItem("New Project");
			menuItem.setMnemonic(KeyEvent.VK_S);
			menuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event){
					outputArea.setText("");
					printArray(createHeader('*', JOptionPane.showInputDialog(mainFrame, "Project name?")));
				}
			});
//			menuItem.addActionListener(new SaveFileListener());
			// create project header (method)
			menu.add(menuItem);
			
			menuItem = new JMenuItem("Open Project");
			menuItem.setMnemonic(KeyEvent.VK_O);
			menuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event){
					openFile("PROJECT", "prj");
				}
			});		
			menu.add(menuItem);
			
			menuItem = new JMenuItem("Save Project");
			menuItem.setMnemonic(KeyEvent.VK_S);
			menuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event){
					saveFile("PROJECT", "prj");
				}
			});		
			menu.add(menuItem);
			
			menu.addSeparator();
			
			menuItem = new JMenuItem("Export");
			menuItem.setMnemonic(KeyEvent.VK_E);
			menuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event){
					saveFile("TEXT", "txt");
				}
			});
			menu.add(menuItem);
			
			menu.addSeparator();
			
			menuItem = new JMenuItem("Exit");
			menuItem.setMnemonic(KeyEvent.VK_X);
			menuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event){
					mainFrame.dispose();
				}
			});
			menu.add(menuItem);
			
			add(menu);
			
			menu = new JMenu("Experiment");
			menu.setMnemonic(KeyEvent.VK_E);
			
			menuItem = new JMenuItem("Insert Title");
			menuItem.setMnemonic(KeyEvent.VK_T);
			menuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event){
					printArray(createHeader('-', dateFormat.format(date) + " " 
							+ JOptionPane.showInputDialog(mainFrame, "Experiment Title")));
				}
			});
			menu.add(menuItem);
			
			subMenu = new JMenu("Add Buffer");
			subMenu.setMnemonic(KeyEvent.VK_B);
			
			menuItem = new JMenuItem("New...");
			menuItem.setMnemonic(KeyEvent.VK_N);
			menuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event){
					try{
						int components = Integer.parseInt(JOptionPane.showInputDialog(null,
								"Number of buffer components?"));
						new BufferGUI(mainFrame, components);
					} catch (NumberFormatException e) {
					}	
				}
			});
			subMenu.add(menuItem);
			
			menuItem = new JMenuItem("From existing...");
			menuItem.setMnemonic(KeyEvent.VK_E);
			menuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event){
					openFile("BUFFER", "buf");
				}
			});
			subMenu.add(menuItem);
				
			menu.add(subMenu);
			
			subMenu = new JMenu("Add Protocol");
			subMenu.setMnemonic(KeyEvent.VK_P);
			
			menuItem = new JMenuItem("New...");
			menuItem.setMnemonic(KeyEvent.VK_N);
			menuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event){
					new ProtocolGUI(mainFrame);
				}
			});
			subMenu.add(menuItem);
			
			menuItem = new JMenuItem("From existing...");
			menuItem.setMnemonic(KeyEvent.VK_E);
			menuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event){
					openFile("PROTOCOL", "prt");
				}
			});
			subMenu.add(menuItem);
				
			menu.add(subMenu);
			
//			menuItem = new JMenuItem("Add Timer");
//			menuItem.setMnemonic(KeyEvent.VK_T);
//			menuItem.addActionListener(new NewTimerListener());			
//			menu.add(menuItem);
//			
//			menu.addSeparator();
			
			add(menu);

		}
		
		private void openFile(String fileTypeFull, String fileTypeShort){
			JFileChooser openChooser = new JFileChooser("C:\\Users\\Cheeky Chan\\Box Sync\\LabRetriever");
			openChooser.setFileFilter(new FileNameExtensionFilter(fileTypeFull + " files", fileTypeShort));
			int returnVal = openChooser.showOpenDialog(mainFrame);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				File file = openChooser.getSelectedFile();
				try {
					printArray(getFileContent(file));
				} catch (FileNotFoundException e){}
			}
		}
		
		private void saveFile(String fileTypeFull, String fileTypeShort){
			JFileChooser saveChooser = new JFileChooser("C:\\Users\\Cheeky Chan\\Box Sync\\LabRetriever");
			saveChooser.setFileFilter(new FileNameExtensionFilter(fileTypeFull + "  files", fileTypeShort));
			int returnVal = saveChooser.showSaveDialog(mainFrame);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				try {
					String fileName = saveChooser.getSelectedFile().getName();
					if (fileName.indexOf("." + fileTypeShort) != -1){
						fileName = fileName.substring(0, fileName.indexOf(".")) + "." + fileTypeShort;
					} else {
						fileName += "." + fileTypeShort;
					}
					File f = new File(fileName);
					if (f.isFile()){
						int confirmVal = JOptionPane.showConfirmDialog(mainFrame, "File exists, overwrite?");
						if (confirmVal == JOptionPane.YES_OPTION){
							PrintStream fileOut = new PrintStream(f);
							for (String s: createOutput(outputArea.getText(), new ArrayList<String>())){
								fileOut.println(s);
							}
							fileOut.close();
						} else if (confirmVal == JOptionPane.NO_OPTION){
							saveFile(fileTypeFull, fileTypeShort);
						}
					}
				} catch (FileNotFoundException e){}
			}
		}
		
//		private void saveFile(){
//			JFileChooser saveChooser = new JFileChooser("C:\\Users\\Cheeky Chan\\Box Sync\\LabRetriever");
//			
//			int returnVal = saveChooser.showSaveDialog(mainFrame);
//			if (returnVal == JFileChooser.APPROVE_OPTION){
//				try {
//					String fileName = saveChooser.getSelectedFile().getName();
//					if (fileName.indexOf(".prj") != -1){
//						fileName = fileName.substring(0, fileName.indexOf(".prj")) + ".prj";
//					} else {
//						fileName += ".prj";
//					}
//					File f = new File(fileName);
//					if (f.isFile()){
//						int confirmVal = JOptionPane.showConfirmDialog(mainFrame, "File exists, overwrite?");
//						if (confirmVal == JOptionPane.YES_OPTION){
//							PrintStream fileOut = new PrintStream(f);
//							for (String s: createOutput(outputArea.getText(), new ArrayList<String>())){
//								fileOut.println(s);
//							}
//							fileOut.close();
//						} else if (confirmVal == JOptionPane.NO_OPTION){
//							saveFile();
//						}
//					}
//					
////					ArrayList<String> output = new ArrayList<String>();
//					
//				} catch (FileNotFoundException e){}
//			}
//		}
		
		private ArrayList<String> createHeader(char c, String s){
			while (s.length() < 70){
				s = " " + s + " ";
				if (s.length() > 70) s = s.substring(1, s.length());
			}
			ArrayList<String> header = new ArrayList<String>();
			String border = new String();
			for (int i = 0; i < s.length(); i++){
				border += c;
			}
			header.add(border);
			header.add(s);
			header.add(border);
			return header;
		}

//		public class NewTimerListener implements ActionListener{
//			public void actionPerformed(ActionEvent event){
//				new TimerGUI();
//			}
//		}
	}

}
