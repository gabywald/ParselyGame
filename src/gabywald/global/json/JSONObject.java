package gabywald.global.json;

import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Gabriel Chandesris (2014)
 */
public class JSONObject extends JSONValue {

	private Map<String, JSONValue> map;

	/** Default constructor. */
	public JSONObject() {
		super(JSON_VALUE_TYPE.OBJECT);
		this.map = new HashMap<String, JSONValue>();
	}

	/**
	 * Construct a JSONObject from a source JSON text string. This is the most commonly used JSONObject constructor.
	 * @param source (Strintg) A string beginning with <code>{</code>&nbsp;<small>(left brace)</small> and ending with <code>}</code>&nbsp;<small>(right brace)</small>.
	 * @exception JSONException If there is a syntax error in the source string or a duplicated key.
	 */
	public JSONObject(String source) throws JSONException 
		{ this(new JSONTokenizer(source)); }

	/**
	 * Construct a JSONObject from a JSONTokenizer.
	 * @param token (JSONTokenizer) A JSONTokenizer object containing the source string.
	 * @throws JSONException If there is a syntax error in the source string or a duplicated key.
	 */
	public JSONObject(JSONTokenizer token) 
			throws JSONException {
		this();
		char c;
		String key;
		if (token.nextClean() != '{') 
			{ throw token.syntaxError("A JSONObject text must begin with '{'"); }
		for ( ; ; ) {
			c = token.nextClean();
			switch (c) {
			case 0:
				throw token.syntaxError("A JSONObject text must end with '}'");
			case '}':
				return;
			default:
				token.back();
				key = token.nextValue().toString();
			}
			// The key is followed by ':'.
			c = token.nextClean();
			if (c != ':') 
				{ throw token.syntaxError("Expected a ':' after a key"); }
			// this.map.put(key, token.nextValue());
			Logger.printlnLog(LoggerLevel.LL_VERBOSE, "key: {" + key + "}");
			// String keyInMap = JSONObject.treatKey(key);
			this.put(key, token.nextValue());
			// Pairs are separated by ','.
			switch (token.nextClean()) {
			//case ';':
			case ',':
				if (token.nextClean() == '}') 
					{ return; }
				token.back();
				break;
			case '}':
				return;
			default:
				throw token.syntaxError("Expected a ',' or '}'");
			}
		} // END "for ( ; ; )"
	}

	public String toString() {
		String toReturn = new String( "" );

		toReturn += "{";

		boolean isFirst = true;
		Iterator<String> iterator = this.map.keySet().iterator();
		while( iterator.hasNext()) {
			if ( ! isFirst) 
				{ toReturn += ", "; }
			else { isFirst = false; }
			String currKey		= iterator.next();
			JSONValue currVal	= this.map.get(currKey);
			// toReturn += "" + currKey + ":" + currVal.toString() + "";
			toReturn += "\"" + currKey + "\":" + currVal.toString() + "";
		} // END "while( iterator.hasNext())"
		toReturn += "}";

		return toReturn;
	}
	
	public boolean has(String key) 
		{ return this.map.containsKey(key); }
	
	public JSONValue get(String key) 
		{ return this.map.get(key); }
		// { return this.get(key); }
	
	public Set<String> getKeySet() 
		{ return this.map.keySet(); }

	public void put(String key, JSONValue value) 
		{ this.map.put(JSONObject.treatKey(key), value); }

	public void put(String key, int value) 
		{ this.map.put(JSONObject.treatKey(key), new JSONNumber(value) ); }
	
	public void put(String key, long value) 
		{ this.map.put(JSONObject.treatKey(key), new JSONNumber(value) ); }
	
	public void put(String key, double value) 
		{ this.map.put(JSONObject.treatKey(key), new JSONNumber(value) ); }
	
	public void put(String key, boolean value) 
		{ this.map.put(JSONObject.treatKey(key), new JSONBoolean(value) ); }
	
	public void put(String key, String value) 
		{ this.map.put(JSONObject.treatKey(key), new JSONString(value) ); }

	private static String treatKey(String key) {
		String toApply = key.substring( (key.startsWith("\"")?1:0), 
										key.length() - (key.endsWith("\"")?1:0));
		Logger.printlnLog(LoggerLevel.LL_VERBOSE, "{" + key + "} => {" + toApply + "}");
		return toApply;
	}
}


