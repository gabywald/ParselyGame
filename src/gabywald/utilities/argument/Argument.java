package gabywald.utilities.argument;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * 
 * @author Gabriel Chandesris (2013)
 */
public class Argument {
	/**
	 * 		- for each : awaited letter(s) / 'name', 
	 * 		- 'usage' (description for '--help'	),
	 * 		- 'mandatory' (false by default, or true), 
	 * 		- 'hidden' (false by default, or true : showing or not in --help), 
	 * 		- 'awaited' (0 by default, else is number of items after), 
	 * 		- ...
	 */
	private HashMap<String, String> parameters;
	
	/** If current instance awaited values : they are put here ! */
	private List<String> forAwaited;
	
	public static String	NAME		= "name", 
							USAGE		= "usage", 
							ABBREV		= "abbrev", 
							MANDATORY	= "mandatory", 
							HIDDEN		= "hidden", 
							PRESENCE	= "presence", 
							AWAITED		= "awaited";

	/** Constructor. 
	 * Default values : 
	 * <ul>
	 * 		<li>Mandatory : false. </li>
	 * 		<li>Hidden : false. </li>
	 * 		<li>Presence : false. </li>
	 * 		<li>Awaited : 0. </li>
	 * </ul>
	 * @param name (String)
	 * @param usage (String)
	 * @param abbrevs (String[])
	 */
	public Argument(String name, String usage, String[] abbrevs) {
		String abbrev = new String("");
		for (int i = 0 ; i < abbrevs.length ; i++) 
			{ abbrev += abbrevs[i] + (( (i > 0) && (i < abbrevs.length - 1) )?"|":""); }
		this.forAwaited = new ArrayList<String>();
		this.parameters	= new HashMap<String, String>();
		this.parameters.put(Argument.NAME,		name);
		this.parameters.put(Argument.USAGE,		usage);
		this.parameters.put(Argument.ABBREV,	abbrev);
		this.parameters.put(Argument.MANDATORY,	"false");
		this.parameters.put(Argument.HIDDEN,	"false");
		this.parameters.put(Argument.PRESENCE,	"false");
		this.parameters.put(Argument.AWAITED,	"0");
	}
	
	public String getName() 
		{ return this.parameters.get(Argument.NAME); }
	
	public boolean isAbbrevValid(String testAbbrev) {
		boolean testResult	= false;
		String[] abbrevs	= this.parameters.get(Argument.ABBREV).split("|");
		for (int i = 0 ; (i < abbrevs.length) && ( ! testResult) ; i++) 
			{ testResult = testAbbrev.equals(abbrevs[i]); }
		return testResult;
	}
	
	/**
	 * To check validity of argument after parsing. 
	 * If mandatory : has to be present too to be valid. 
	 * More than awaited elements have to be present after (-||--) argument. 
	 * @return (boolean)
	 */
	public boolean isValid() {
		boolean toReturn = true;
		
		if (this.isMandatory()) 
			{ toReturn	= ( ( this.isMandatory()) && (this.isPresent())) && toReturn; }
		// else { toReturn	= (!this.isMandatory()) && toReturn; }
		
		int waited = this.hasAwaited();
		if (waited > 0) 
			{ toReturn = (this.forAwaited.size() >= waited) && toReturn; }
		
		return toReturn;
	}
	
	public String toString() {
		String toReturn	= new String("");
		toReturn 		+= "\t" + this.parameters.get(Argument.NAME) + " ";
		toReturn		+= "[(-|--)" + this.parameters.get(Argument.ABBREV) + "] ";
		toReturn		+= "(" + this.hasAwaited() + " waited argument" 
								+ ((this.hasAwaited() > 1)?"s":"") + ")";
		toReturn		+= " : " + this.parameters.get(Argument.USAGE) + "\n";
		return toReturn;
	}
	
	public String getParameter(String key) 
		{ return this.parameters.get(key); }
	
	public boolean isMandatory() 
		{ return this.parameters.get(Argument.MANDATORY).equals("true"); }
	
	public boolean isHidden() 
		{ return this.parameters.get(Argument.HIDDEN).equals("true"); }
	
	public boolean isPresent() 
		{ return this.parameters.get(Argument.PRESENCE).equals("true"); }
	
	public int hasAwaited() 
		{ return Integer.parseInt(this.parameters.get(Argument.AWAITED)); }
	
	public boolean setMandatory(boolean isMandatory) { 
		String was = this.parameters.put(Argument.MANDATORY, isMandatory?"true":"false");
		return was.equals("true");
	}
	
	public boolean setHidden(boolean isHidden) { 
		String was = this.parameters.put(Argument.HIDDEN, isHidden?"true":"false");
		return was.equals("true");
	}
	
	public boolean setPresence(boolean isPresent) { 
		String was = this.parameters.put(Argument.PRESENCE, isPresent?"true":"false");
		return was.equals("true");
	}
	
	public int setAwaited(int hasAwaited) { 
		String was = this.parameters.put(Argument.AWAITED, "" + hasAwaited + "");
		return Integer.parseInt(was);
	}
	
	public void addAwaitedValue(String value) 
		{ this.forAwaited.add(value); }
	
}
