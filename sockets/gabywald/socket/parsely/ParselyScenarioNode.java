package gabywald.socket.parsely;

import java.util.ArrayList;
import java.util.List;

public class ParselyScenarioNode {
	private String name;
	private String description;
	private List<String> exits;
	private List<String> objects;
	private ParselyScenarioEndCondition endCondition;

	public ParselyScenarioNode(	String name ) {
		this.name			= name;
		this.description	= new String();
		this.exits			= new ArrayList<String>();
		this.objects		= new ArrayList<String>();
		this.endCondition	= null;
	}

	public String getName()				{ return this.name; }
	public String getDescription()		{ return this.description; }
	public List<String> getExits()		{ return this.exits; }
	public List<String> getObjects()	{ return this.objects; }

	public void setName(String name)				{ this.name			= name; }
	public void setDescription(String description) 	{ this.description	= description; }
	public void setExits(List<String> exits)		{ this.exits		= exits; }
	public void setObjects(List<String> objects)	{ this.objects		= objects; }
	
	public void setExits(String exits)		{ 
		this.exits		= new ArrayList<String>();
		String[] table	= exits.split(";");
		for (int i = 0 ; i < table.length ; i++)
			{ this.exits.add(table[i]); }
	}
	
	public void setObjects(String objects)	{ 
		this.objects		= new ArrayList<String>();
		String[] table	= objects.split(";");
		for (int i = 0 ; i < table.length ; i++)
			{ this.objects.add(table[i]); }
	}
	
	public void setCondition(String howToWin) 
		{ this.endCondition = new ParselyScenarioEndCondition(howToWin); }
	
	public void setWinningMessage(String message) 
		{ if (this.endCondition != null) 
			{ this.endCondition.setWinningMessage(message); } }
	
	public String getWinningMessage() { 
		if (this.endCondition != null) 
			{ return this.endCondition.getWinningMessage(); } 
		else { return new String(""); }
	}
	
	public boolean isAtEnd(List<String> inventory) { 
		if (this.endCondition != null) 
			{ return this.endCondition.isWinning(inventory); }
		return false;
	}
	
	public boolean takingObject(String object) {
		boolean hasObject = false;
		for (int i = 0 ; (i < this.objects.size()) && ( ! hasObject) ; i++) {
			hasObject = this.objects.get(i).equals(object);
			if (hasObject) { this.objects.remove(i); }
		} /** END "for (int i = 0 ; (i < this.objects.size()) && ( ! hasObject) ; i++)" */
		return hasObject;
	}
	
	/** @deprecated ... */
	public boolean hasExit(String exit) {
		boolean toReturn = false;
		for (int i = 0 ; (i < this.exits.size()) && ( ! toReturn) ; i++) {
			String currentTest = this.exits.get(i).split("/")[0];
			toReturn = currentTest.equals(exit);
		} /** END "for (int i = 0 ; (i < this.exits.size()) && ( ! toReturn) ; i++)" */
		return toReturn;
	}
	
	public int getExitID(String exit) {
		int toReturn = -3;
		
		boolean hasExit = false;
		for (int i = 0 ; (i < this.exits.size()) && ( ! hasExit) ; i++) {
			String currentTest = this.exits.get(i).split("/")[0];
			hasExit = currentTest.equals(exit);
			if (hasExit) 
				{ toReturn = Integer.parseInt(this.exits.get(i).split("/")[1]); }
		} /** END "for (int i = 0 ; (i < this.exits.size()) && ( ! toReturn) ; i++)" */
		
		return toReturn;
	}
	
	public String toString() {
		String toReturn = new String();
		
		toReturn += "[" + this.name + "] " + this.description + "\n";
		
		if (this.objects.size() > 0) { toReturn += "\tThere is "; }
		for (int i = 0 ; i < this.objects.size() ; i++) 
			{ toReturn += ((i > 0)?((i != this.objects.size()-1 )?", ":" and "):"")
								+ this.objects.get(i); }
		if (this.objects.size() > 0) { toReturn += ". \n"; }
		
		if (this.exits.size() == 0) 
			{ toReturn += "\tNo exit. \n"; }
		else {
			toReturn += "\tExits are : ";
			for (int i = 0 ; i < this.exits.size() ; i++) 
				{ toReturn += ((i > 0)?", ":"") + this.exits.get(i).split("/")[0]; }
			toReturn += ". \n";
		} /** END else of "if (this.exits.size() == 0)" */
		
		return toReturn;
	}
	
	
}
