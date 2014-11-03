package labnotebook.GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import java.text.SimpleDateFormat;

public class TimerGUI extends InputDialog{
	private static final long serialVersionUID = 1L;
	private JSpinner hourSpinner, minSpinner, secSpinner;
	
	public TimerGUI(Main main){
		super(main);
	}
	
	protected void initialize(){
		super.initialize();

		setSize(new Dimension(500, 200));
		setLayout(new BorderLayout());
		setTitle("Add New Timer");
		setLocation(400, 300);
		setResizable(false);
		
		JLabel hourLabel = new JLabel("Hours: ");
		JLabel minLabel = new JLabel("Minutes: ");
		JLabel secLabel = new JLabel("Seconds: ");
		
		SpinnerModel hourModel = new SpinnerNumberModel(0, 0, 24, 1);
		SpinnerModel minModel = new SpinnerNumberModel(0, 0, 60, 5);
		SpinnerModel secModel = new SpinnerNumberModel(0, 0, 60, 5);
		hourSpinner = new JSpinner(hourModel);
		minSpinner = new JSpinner(minModel);
		secSpinner = new JSpinner(secModel);
		JPanel inputPanel = new JPanel();
		
		inputPanel.setLayout(new FlowLayout());
		inputPanel.add(hourLabel);
		inputPanel.add(hourSpinner);
		inputPanel.add(minLabel);
		inputPanel.add(minSpinner);
		inputPanel.add(secLabel);
		inputPanel.add(secSpinner);
		
		JPanel buttonPanel = new JPanel();
		JButton addButton = new JButton("Add Timer");
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				new TimerPanel(getHour(), getMin(), getSec());
				dispose();
			}
		});
		JButton cancelButton = new JButton("Cancel");
		buttonPanel.add(addButton);
		buttonPanel.add(cancelButton);
		
		add(inputPanel, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);
						
		setVisible(true);
	}
	
	private int getHour(){
		return (Integer) hourSpinner.getValue();
	}
	private int getMin(){
		return (Integer) minSpinner.getValue();
	}
	private int getSec(){
		return (Integer) secSpinner.getValue();
	}
	
	public class TimerPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private int h, m, s, totalTime;
		private JLabel timerLabel;
		private Calendar startTime, endTime;
		private SimpleDateFormat timeFormat;
		private Timer timer;
		public TimerPanel(int h, int m, int s){
			super();
			this.h = h;
			this.m = m;
			this.s = s;
			totalTime = timeToSeconds(h, m, s);
			
			this.setPreferredSize(new Dimension(300, 120));
			this.setLayout(new FlowLayout());
			
			startTime = new GregorianCalendar();
			startTime.setTime(new Date());
			timeFormat = new SimpleDateFormat("hh:mm:ss a");
			
			endTime = new GregorianCalendar();
			endTime.setTime(startTime.getTime());
			endTime.add(Calendar.SECOND, totalTime);
			
			timerLabel = new JLabel();
			JLabel startTimeLabel = new JLabel();
			JLabel endTimeLabel = new JLabel();
			
			startTimeLabel.setText("Experiment started at " + timeFormat.format(startTime.getTime()));
			endTimeLabel.setText("and ends at "	+ timeFormat.format(endTime.getTime()));
			
			JButton dismissButton = new JButton("Dismiss Timer");
			
			
			this.add(timerLabel);
			this.add(startTimeLabel);
			this.add(endTimeLabel);	
			this.add(dismissButton);
			
			timer = new Timer(1000, new CountdownTaskListener());
			timer.start();
		}
		
		public class DismissButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				
			}
		}
		
		public class CountdownTaskListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				s--;
				if (s < 0){
					s = 59;
					m--;
				}
				if (m < 0){
					m = 59;
					h--;
				}
				timerLabel.setText("Time remaining: " + h + "h" + m + "m" + s + "s");
				totalTime--;
				if (totalTime == 0){
					timer.stop();
					JOptionPane.showMessageDialog(main, "Time's up!");
				}
			}
		}
		
		public int timeToSeconds(int h, int m, int s){
			return (h * 3600) + (m * 60) + s;
		}
	}
}
