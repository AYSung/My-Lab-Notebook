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
	
	protected abstract class Quantity{
		private double quantity;
		private String unit;
		
		public Quantity(){
			
		}
		
		protected double getConc(){
			return quantity;
		}
		
		protected void setConc(double quantity){
			this.quantity = quantity;
		}
		
		protected String getUnit(){
			return this.unit;
		}
		
		protected void setUnit(String unit){
			this.unit = unit;
		}
		
		protected abstract double standardizeUnit(Quantity q);
	}
	
	public class SQuant extends Quantity{
		
		public SQuant(){
			super();
		}
		protected double standardizeUnit(Quantity q){
			double d = 0;
			return d;
		}
	}
}
