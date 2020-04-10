package gabywald.socket.parsely.v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import gabywald.global.data.Fichier;

public class ParselyScenario extends StateMachine<ParselyScenarioNode> {
	private Fichier loader;
	private String name;
	private String intro;
	private boolean hasStarted;
	private HashMap<String, ParselyScenarioNode> locations;
	private List<String> commands;
	private List<String> inventory;
	private List<String> sillyAnswers;
	/** To use for "unknown situations" or when adding something to the inventory. */
	private String previousData;
	private int previousState;
	
	public static String[] BASIC_COMMANDS = {
		"help", "inventory", "quit", "go", "walk", "get", "take", "where"
	};
	
	public static String[] SILLY_ANSWER = {
		"I don't know what to do with that thing : XXXXX... ", 
		"What do you mean with 'XXXXX' ? ", 
		"Are you sure of your \"XXXXX\" ? ", 
		"\"XXXXX\" : It's sounds funny !", 
		"I am a superior being, please write correctly !", 
		"You probably mis-write something, somewhat or somebody... " 
	};
	
	public ParselyScenario(String fileName) {
		this.loader			= new Fichier(fileName);
		this.reloadScenario();
	}
	
	public String getName()		{ return this.name; }
	public String getIntro()	{ return this.intro; }
	
	public ParselyScenarioNode getNode(String iKey) 
		{ return this.locations.get(iKey); }
	
	private void reloadScenario() {
		this.hasStarted		= false;
		this.locations		= new HashMap<String, ParselyScenarioNode>();
		this.commands		= new ArrayList<String>();
		for (int i = 0 ; i < ParselyScenario.BASIC_COMMANDS.length ; i++) 
			{ this.commands.add(ParselyScenario.BASIC_COMMANDS[i]); }
		this.inventory		= new ArrayList<String>();
		this.sillyAnswers	= new ArrayList<String>();
		for (int i = 0 ; i < ParselyScenario.SILLY_ANSWER.length ; i++) 
			{ this.sillyAnswers.add(ParselyScenario.SILLY_ANSWER[i]); }
		this.previousData	= new String();
		this.previousState	= 0;
		
		String table[]		= this.loader.getTable();
		if (!table[0].matches("ERROR")) {
			for (int i = 0 ; i < table.length ; i++) {
				String[] split = table[i].split("\t");
				if (split.length > 1) {
					String firstColumn = split[0];
					if (firstColumn.equals("name")) 
						{ this.name = split[1]; }
					else if (firstColumn.equals("introduction")) 
						{ this.intro = split[1]; }
					else if (firstColumn.equals("silly")) { 
						String[] moreSilly = split[1].split(";");
						for (int j = 0 ; j < moreSilly.length ; j++) 
							{ this.sillyAnswers.add(moreSilly[j]); }
					}
					else if (firstColumn.equals("location")) {
						if (this.locations.containsKey(split[1]))
							{ this.locations.get(split[1]).setName(split[2]); }
						else 
							{ this.locations.put(split[1], new ParselyScenarioNode(split[2])); }
					}
					else if (firstColumn.equals("description")) {
						if (this.locations.containsKey(split[1]))
							{ this.locations.get(split[1]).setDescription(split[2]); }
					}
					else if (firstColumn.equals("exits")) {
						if (this.locations.containsKey(split[1]))
							{ this.locations.get(split[1]).setExits(split[2]); }
					}
					else if (firstColumn.equals("objects")) {
						if (this.locations.containsKey(split[1]))
							{ this.locations.get(split[1]).setObjects(split[2]); }
					}
					else if (firstColumn.equals("howtowin")) {
						if (this.locations.containsKey(split[1]))
							{ this.locations.get(split[1]).setCondition(split[2]); }
					}
					else if (firstColumn.equals("msgwhenwin")) {
						if (this.locations.containsKey(split[1]))
							{ this.locations.get(split[1]).setWinningMessage(split[2]); }
					}
				} /** END "if (split.length > 1)" */
			} /** END "for (int i = 0 ; i < table.length ; i++)" */
		} /** END "if (!table[0].matches("ERROR"))" */
		
		this.setState(0);
	}
	
	public void nextStep(String command) {
//		switch(this.getState()) {
//		case(0):
//			break;
//		} /** END "switch(this.getState())" */
		
		/** 'help' */
		if (command.equals(ParselyScenario.BASIC_COMMANDS[0])) 
			{ this.setTemporaryState(-1); }
		else /** 'inventory' */
		if (command.equals(ParselyScenario.BASIC_COMMANDS[1])) 
			{ this.setTemporaryState(-2); }
		else /** 'quit' */
			if (command.equals(ParselyScenario.BASIC_COMMANDS[2])) 
				{ this.reloadScenario(); }
		else /** 'go||walk' */
			if ( (command.startsWith(ParselyScenario.BASIC_COMMANDS[3]))
					|| (command.startsWith(ParselyScenario.BASIC_COMMANDS[4])) ) { 
				String[] going = command.split(" ");
				if ( (going.length > 1) 
						&& ( (going[0].equals(ParselyScenario.BASIC_COMMANDS[3]))
								|| (going[0].startsWith(ParselyScenario.BASIC_COMMANDS[4])) ) ) {
					String locationToGo = going[1];
					ParselyScenarioNode currentNode = this.locations.get(this.getState() + "");
					if (currentNode != null) { 
						int exit = this.locations.get(this.getState() + "").getExitID(locationToGo);
						this.setTemporaryState(exit);
					} else { this.reloadScenario(); }
				} else { this.caseOfUnknownSituation(command); }
			}
		else /** 'get||take' */
			if ( (command.startsWith(ParselyScenario.BASIC_COMMANDS[5]))
					|| (command.startsWith(ParselyScenario.BASIC_COMMANDS[6])) ) { 
				// Logger.printlnLog(LoggerLevel.LL_INFO, "get||take");
				String[] geting = command.split(" ");
				if ( (geting.length > 1) 
						&& ( (geting[0].equals(ParselyScenario.BASIC_COMMANDS[5]))
								|| (geting[0].startsWith(ParselyScenario.BASIC_COMMANDS[6])) ) ) {
					// Logger.printlnLog(LoggerLevel.LL_INFO, "\t " + geting[0]);
					// Logger.printlnLog(LoggerLevel.LL_INFO, "\t " + geting[1]);
					String objectToTake = geting[1];
					ParselyScenarioNode currentNode = this.locations.get(this.getState() + "");
					if (currentNode != null) { 
						if (currentNode.takingObject(objectToTake)) {
							this.inventory.add(objectToTake);
							this.previousData = "\t[" + objectToTake + "] added to your inventory. \n";
							this.setTemporaryState(-4);
						} /** END "if (currentNode.takingObject(objectToTake))" */
						else { this.caseOfUnknownSituation(command); }
					} else { this.reloadScenario(); }
				} else { this.caseOfUnknownSituation(command); }
			}
		else /** 'where' */
			if (command.equals(ParselyScenario.BASIC_COMMANDS[7])) 
				{ /** Just turning around... */; }
		
		
		else { this.caseOfUnknownSituation(command); }
	}

	public String getContentToShow() {
		String toReturn = new String();
		
		switch(this.getState()) {
		case(-1): /** 'help' */
			toReturn += this.listOfAvailableCommands();
		this.comeBack();
			break;
		case(-2): /** 'inventory' */
			toReturn += "Content of inventory : \n\t";
			if (this.inventory.size() == 0) 
				{ toReturn += "<nothing>. \n"; }
			else {
				for (int i = 0 ; i < this.inventory.size(); i++) 
					{ toReturn += ((i > 0)?" ; ":"") + this.inventory.get(i); }
				toReturn += ". \n";
			} /** END else of "if (this.inventory.size() == 0)" */
			this.comeBack();
			break;
		case(-3): /** unknown situation */
			toReturn += this.getAsillyResponse() + "\n";
			this.comeBack();
			break;
		case(-4): /** getting / taking some object... */
			toReturn += this.previousData + "\n";
			this.comeBack();
			break;
		default: /** 0 and + : ParselyScenarioNodes... */
			if ( ! this.hasStarted) {
				toReturn += "Scenario \"" + this.name + "\"\n";
				if (this.commands.size() > 0) 
					{ toReturn += this.listOfAvailableCommands(); }
				toReturn += this.intro + "\n";
				this.hasStarted = true;
			} /** END "if ( ! this.hasStarted)" */
			ParselyScenarioNode currentNode = this.locations.get(this.getState() + "");
			if (currentNode != null) { 
				toReturn += currentNode.toString();
				if (currentNode.isAtEnd(this.inventory)) { 
					toReturn += currentNode.getWinningMessage() + "\n";
					ParselyGameStarter.getInstance().quitCurrentScenario();
					toReturn += ParselyGameStarter.getInstance().getContentToShow();
					this.reloadScenario();
				} /** END "if (currentNode.isAtEnd(this.inventory))" */
			} else { this.reloadScenario(); toReturn += " !! Scenario Reloading... !! "; }
			break;
		} /** END "switch(this.getState())" */
				
		return toReturn;
	}
	
	private void setTemporaryState(int state) {
		this.previousState = this.getState();
		this.setState(state);
	}
	
	private void comeBack() { this.setState(this.previousState); }

	private void caseOfUnknownSituation(String command) {
		/** unknown situation */
		this.previousData	= command;
		this.previousState	= this.getState();
		this.setState(-3); 
	}
	
	private String listOfAvailableCommands() {
		String toReturn = new String();
		toReturn += "List of available commands : \n\t";
		for (int i = 0 ; i < this.commands.size(); i++) 
			{ toReturn += ((i > 0)?" ; ":"") + this.commands.get(i); }
		toReturn += ". \n";
		return toReturn;
	}
	
	private String getAsillyResponse() {
		String toReturn = new String();
		
		int toRand	= this.sillyAnswers.size();
		int soluti	= (new Random()).nextInt(toRand);
		
		// toReturn	+= "[" + soluti + "/" + (toRand-1) + "]\t";
		toReturn	+= this.sillyAnswers.get(soluti);
		
		return toReturn.replaceAll("XXXXX", this.previousData);
	}
	
	
}
