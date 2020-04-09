package gabywald.socket.parsely.v2;

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
		String toReturn = new String();
		
		switch(this.getState()) {
		case(0): 
			toReturn += "Welcome to Parsely Game, choose : \n";
			toReturn += "\t 0- exit\n";
			for (int i = 1 ; i < this.setOfScenarios.length+1 ; i++) 
				{ toReturn += "\t " + (i) + "- '" + this.setOfScenarios[i-1].getName() + "'\n"; }
			break;
		case(1):
			toReturn += this.getObject().getContentToShow();
		} /** END "switch(this.getState())" */
		
		return toReturn;
	}
	
	
}
