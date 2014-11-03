package labnotebook.entries;

import java.util.*;

public abstract class ChemicalEntry {
	protected String name, sUnit, fUnit, qUnit;
	protected double sConc, fConc, quant;
	protected boolean wet;
	
	public ChemicalEntry(){
		name = new String();
		sUnit = new String();
		fUnit = new String();
		qUnit = new String();
	}
	
	public abstract ArrayList<String> getProperties();
}
