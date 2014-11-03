package labnotebook.entries;

import java.util.*;
import java.io.*;

public abstract class LabEntry {
	protected String name, type, fileType;
	protected ArrayList<String> output;
	
	public LabEntry(){
		this.output = new ArrayList<String>();
	}
	
	public String getName(){
		return name;
	}
	
	public String getType(){
		return type;
	}
	
	public String getFileType(){
		return fileType;
	}
	
	public ArrayList<String> getOutput(){
		return output;
	}
	
	protected abstract void createOutput();
	
	protected String createTitle(){
		String s = new String();
		s += type.toUpperCase() + ": " + name;
		return s;
	}
	
	protected String addTab(String s){
		if (s.length() < 8){
			s += "\t\t";
		} else if (s.length() < 16){
			s += "\t";
		}
		return s;
	}
	
	protected void printFile() throws FileNotFoundException{
		PrintStream fileOut = new PrintStream(new File(name + fileType));
		fileOut.println(type.toUpperCase() + ": " + name);
		fileOut.close();
	}

}
