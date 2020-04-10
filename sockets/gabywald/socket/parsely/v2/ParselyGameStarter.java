package gabywald.socket.parsely.v2;

import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

public class ParselyGameStarter extends StateMachine<ParselyScenario> {

	private static ParselyGameStarter instance;
	
	private ParselyScenario[] setOfScenarios;
	
	private ParselyGameStarter() 
		{ this.setOfScenarios = ParselyGameStarter.listScenarios();; }
	
	public static ParselyGameStarter getInstance() {
		if (ParselyGameStarter.instance == null) 
			{ ParselyGameStarter.instance = new ParselyGameStarter() ; }
		return ParselyGameStarter.instance;
	}
	
	private static ParselyScenario[] listScenarios()
		{ return ParselyScenarioContainer.getInstance().getScenarios(); }
	
//	private static String[] listFileScenarios() 
//		{ return ParselyScenarioContainer.getInstance().getScenarioFileNames(); }
	
	public void quitCurrentScenario() {
		this.setObject(null);
		this.setState(0);
	}
	
	public void nextStep(String command) {
		
		Logger.printlnLog(LoggerLevel.LL_INFO, "PGS.next: {" + command + "} (" + this.getState() + ")");
		
		switch(this.getState()) {
		case(0):
			try { 
				int menuElt = Integer.parseInt(command);
				if ( (menuElt >= 0) && (menuElt <= this.setOfScenarios.length+1 ) ) {
					switch(menuElt) {
					case (0) : System.exit(0);break;
					default:
						this.setState(1);
						this.setObject(this.setOfScenarios[menuElt-1]);
					} /** END "switch(menuElt)" */
				} /** END "if ( (menuElt >= 0) && (menuElt <= setOfScenarios.length+1 ) )" */
			} catch (NumberFormatException e) /* !! */ { ; } /* !! */
			break;
		case(1): 
			this.getObject().nextStep(command);
			/** Check if we quit ! */
			if (command.equals(ParselyScenario.BASIC_COMMANDS[2])) 
				{ this.quitCurrentScenario(); }
			break;
		} /** END "switch(this.getState())" */
	}
	
	public String getContentToShow() {
		StringBuilder toReturn = new StringBuilder();
		
		switch(this.getState()) {
		case(0): 
			toReturn.append("Welcome to Parsely Game, choose : ").append("\\n");
			toReturn.append("\\t 0- exit").append("\\n");
			for (int i = 1 ; i < this.setOfScenarios.length+1 ; i++) 
				{ toReturn.append("\\t ").append(i).append("- '")
					.append(this.setOfScenarios[i-1].getName()).append("'")
					.append("\\n"); }
			break;
		case(1):
			toReturn.append(this.getObject().getContentToShow());
		} /** END "switch(this.getState())" */
		
		return toReturn.toString();
	}
	
	
}
