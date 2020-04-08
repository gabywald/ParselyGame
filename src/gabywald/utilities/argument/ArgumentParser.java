package gabywald.utilities.argument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * 
 * @author Gabriel Chandesris (2013)
 */
public class ArgumentParser {
	/** Default help argument. */
	private Argument helpArgument;
	/** Arguments definition... */
	private HashMap<String, Argument> argumentsDefinition;
	/** Set of bad arguments found. */
	private List<String> badArguments;
	
	/** Default Constructor. 
	 * Adding help comand. */
	public ArgumentParser() {
		this.argumentsDefinition	= new HashMap<String, Argument>();
		this.badArguments			= new ArrayList<String>();
		
		String toSplitForHelp		= "h";
		this.helpArgument			= new Argument("help", "To show help message and arguments", toSplitForHelp.split("|"));
		this.addArgument(this.helpArgument);
	}
	
	public void addArgument(Argument argument) 
		{ this.argumentsDefinition.put(argument.getName(), argument); }
	
	public boolean parseArguments(String[] args) {
		Argument currentArgument	= null;
		boolean hasAPresence		= false;
		
		for (int i = 0 ; i < args.length ; i++) {
			String current = args[i];
			if (current.startsWith("--"))		{
				String currentLess	= current.substring(2);
				currentArgument		= this.argumentsDefinition.get(currentLess);
				if (currentArgument == null) 
					{ this.badArguments.add(current); }
				else {
					currentArgument.setPresence(true);
					hasAPresence = true;
				}
			} /** END "if (current.startsWith("--"))" */
			else if (current.startsWith("-"))	{
				String currentLess	= current.substring(1);
				currentArgument		= null;
				Iterator<Entry<String, Argument>> iterator	= this.argumentsDefinition.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, Argument> pair	= iterator.next();
					Argument currArg				= pair.getValue();
					if (currArg.isAbbrevValid(currentLess)) 
						{ currentArgument = currArg; }
				} /** END "while (iterator.hasNext())" */
				if (currentArgument == null) 
					{ this.badArguments.add(current); }
				else {
					currentArgument.setPresence(true);
					hasAPresence = true;
				}
				
			} /** END "else if (current.startsWith("-"))" */
			else {
				if ( (hasAPresence) && (currentArgument != null) ) 
					{ currentArgument.addAwaitedValue(current); }
				else { this.badArguments.add(current); }
			} /** END else ... */
		} /** END "for (int i = 0 ; i < args.length ; i++)" */
		
		boolean areAllValid	= true;
		String helpMessage	= new String();
		Iterator<Entry<String, Argument>> iterator	= this.argumentsDefinition.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Argument> pair	= iterator.next();
			Argument currArg				= pair.getValue();
			if ( ! currArg.isHidden()) 
				{ helpMessage += currArg.toString(); }
			areAllValid = currArg.isValid() && areAllValid;
		} /** END "while (iterator.hasNext())" */
		
		String badyMessage	= new String();
		badyMessage += (this.badArguments.size() > 0)?"\t***** ***** ***** ***** ***** ***** *****\n":"";
		for (int i = 0 ; i < this.badArguments.size() ; i++) 
			{ badyMessage += "\t'" + this.badArguments.get(i) + "' is not a valid argument !\n"; }
		badyMessage += (this.badArguments.size() > 0)?"\t***** ***** ***** ***** ***** ***** *****\n":"";
		
		if (this.badArguments.size() > 0) 
			{ System.out.println(badyMessage); }
		
		if ( (this.helpArgument.isPresent()) || ( ! areAllValid) ) {
			System.out.println(helpMessage);
			return false;
		} /** END "if (this.helpArgument.isValid())" */
		
		// if (this.badArguments.size() > 0) { System.out.println(helpMessage); }
		
		return true;
	}
}
