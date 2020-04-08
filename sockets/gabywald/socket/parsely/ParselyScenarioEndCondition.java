package gabywald.socket.parsely;

import java.util.ArrayList;
import java.util.List;

public class ParselyScenarioEndCondition {
	private String messageForWin;
	private String whereToCheck;
	private List<String> whatToCheck;
	
	public ParselyScenarioEndCondition(String reason) {
		this.messageForWin	= new String();
		// String whyWinning	= reason.substring(0, reason.indexOf("[") - 1);
		String condition	= reason.substring(reason.indexOf("[") + 1, reason.indexOf("]") );
		this.whereToCheck	= condition.split(":")[0];
		this.whatToCheck	= new ArrayList<String>();
		String[] whats		= condition.split(":")[1].split(";");
		for (int i = 0 ; i < whats.length ; i++) 
			{ this.whatToCheck.add(whats[i]); }
		
	}
	
	public String getWinningMessage()				{ return this.messageForWin; }
	public void setWinningMessage(String message)	{ this.messageForWin = message; }
	
	public boolean isWinning(List<String> inventory)	{
		boolean toReturn = false;
		if (this.whereToCheck.equals("INVENTORY")) {
			int commonSize = this.whatToCheck.size();
			boolean tableOfBooleans[] = new boolean[commonSize];
			for (int i = 0 ; i < commonSize ; i++) 
				{ tableOfBooleans[i] = false; }
			
			for (int i = 0 ; i < commonSize; i++)
				{ tableOfBooleans[i] = tableOfBooleans[i] || inventory.contains(this.whatToCheck.get(i)); }
			
			toReturn = true;
			for (int i = 0 ; (i < commonSize) && (toReturn) ; i++)
				{ toReturn = toReturn && tableOfBooleans[i]; }
		}
		// TODO ... (other cases)
		return toReturn;
	}
}
