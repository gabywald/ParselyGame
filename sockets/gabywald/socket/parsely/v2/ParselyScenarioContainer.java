package gabywald.socket.parsely.v2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class defines a Container for ParselyScenario's instances. 
 * <br><i>DPSingleton</i>
 * @author Gabriel Chandesris (2013)
 * @see ParselyScenario
 */
public class ParselyScenarioContainer {
	/** Base directory repository for scenarios' files. */
	private static String pathToScenarios = "resources/datas/scenarios/";
	
	private static ParselyScenarioContainer instance;
	
	private List<ParselyScenario> scenarii;
	private List<String> scenariiNames;
	
	private ParselyScenarioContainer() {
		this.scenarii		= new ArrayList<ParselyScenario>();
		this.scenariiNames	= new ArrayList<String>();
		File directory = new File( ParselyScenarioContainer.pathToScenarios );
		if (directory.isDirectory()) { 
			String[] setOfFiles = directory.list();
			for (int i = 0 ; i < setOfFiles.length ; i++) {
				String currentFile = setOfFiles[i];
				if (currentFile.endsWith(".scen")) {
					ParselyScenario scenario = 
						new ParselyScenario(ParselyScenarioContainer.pathToScenarios + currentFile);
					this.scenarii.add(scenario);
					this.scenariiNames.add(currentFile);
				}
			}
		} /** END "if (directory.isDirectory())" */
	}
	
	public static ParselyScenarioContainer getInstance() {
		if (ParselyScenarioContainer.instance == null) 
			{ ParselyScenarioContainer.instance = new ParselyScenarioContainer(); }
		return ParselyScenarioContainer.instance;
	}

	public ParselyScenario[] getScenarios()
		{ return this.scenarii.toArray(new ParselyScenario[0]); }
	
	public String[] getScenarioFileNames() 
		{ return this.scenariiNames.toArray(new String[0]); }
}
