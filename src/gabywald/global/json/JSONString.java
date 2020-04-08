package gabywald.global.json;

/**
 * 
 * @author Gabriel Chandesris (2014)
 */
public class JSONString extends JSONValue {
	private String value;

	public JSONString() {
		super(JSON_VALUE_TYPE.STRING);
		this.value = new String ( "" );
	}
	
	public JSONString(String value) {
		super(JSON_VALUE_TYPE.STRING);
		String tmp = value;
		if (tmp.startsWith("\""))	{ tmp = tmp.substring( 1 ); }
		if (tmp.endsWith("\""))		{ tmp = tmp.substring( 0, tmp.length() - 1 ); }
		this.value = JSONString.escaping( tmp );
	}
	
	public String getValue() 
		{ return this.value; }

	public String toString() 
		{ return "\"" + this.value + "\""; }
	
	public static String escaping(String data) {
		String toReturn = data;
		toReturn = toReturn.replace("\\", "\\\\");
		toReturn = toReturn.replace("\"", "\\\"");
		toReturn = toReturn.replace("\b", "\\\b");
		toReturn = toReturn.replace("\r", "\\\r");
		toReturn = toReturn.replace("\n", "\\\n");
		toReturn = toReturn.replace("\t", "\\\t");
		return toReturn;
	}
	
	public static String unescaping(String data) {
		String toReturn = data;
		toReturn = toReturn.replace("\\\\", "\\");
		toReturn = toReturn.replace("\\\"", "\"");
		toReturn = toReturn.replace("\\\b", "\b");
		toReturn = toReturn.replace("\\\r", "\r");
		toReturn = toReturn.replace("\\\n", "\n");
		toReturn = toReturn.replace("\\\t", "\t");
		return toReturn;
	}
}
