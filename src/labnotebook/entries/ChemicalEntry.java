package labnotebook.entries;

import java.util.*;

public abstract class ChemicalEntry {
	protected String name, sUnit, fUnit, qUnit;
	protected double sConc, fConc, quant;
	protected Quantity sQuant, fQuant, unkQuant;
	protected boolean wet;
	
	public ChemicalEntry(){
		name = new String();
		sUnit = new String();
		fUnit = new String();
		qUnit = new String();
		
		sQuant = new Quantity(){
			protected double standardizeUnit(Quantity q){
				double standardQuant;
				switch (q.getUnit()){
					case "M":		standardQuant = q.getQuant() * 1000; 	
									break;
					case "mM":		standardQuant = q.getQuant();
									break;
					case "g/mol":	standardQuant = q.getQuant();	
					default:		standardQuant = 0;
				}
				return standardQuant;
			}
		};
		
		fQuant = new Quantity(){
			protected double standardizeUnit(Quantity q){
				double standardQuant;
				switch (q.getUnit()){
					case "M":		standardQuant = q.getQuant() * 1000; 	
									break;
					case "mM":		standardQuant = q.getQuant();
									break;
					case "%":		standardQuant = q.getQuant();	
					default:		standardQuant = 0;
				}
				return standardQuant;
			}
		};
		
	}
	
	public abstract ArrayList<String> getProperties();
	
	protected class Quantity{
		private double quantity;
		private String unit;
		
		public Quantity(){
			
		}
		
		protected double getQuant(){
			return quantity;
		}
		
		protected void setQuant(double quantity){
			this.quantity = quantity;
		}
		
		protected String getUnit(){
			return this.unit;
		}
		
		protected void setUnit(String unit){
			this.unit = unit;
		}
		
		protected double standardizeUnit(Quantity q){
			double standardQuant = q.getQuant();
			return standardQuant;
		}
	}
}
