package labnotebook.entries;

import java.util.*;

public abstract class RecipeEntry extends LabEntry {
	protected double vol;
	protected String vUnit;
	protected ArrayList<ChemicalEntry> chemicals;
	
	public RecipeEntry(){
		super();
		chemicals = new ArrayList<ChemicalEntry>();
	}
	
	protected String createRow(ArrayList<String> a){
		String s = new String();
		for (int i = 0; i < a.size() - 1; i++){
			s += addTab(a.get(i));
		}
		s += a.get(a.size()-1);
		return s;
	}
}
