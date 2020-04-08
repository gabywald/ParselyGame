package gabywald.global.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Gabriel Chandesris (2014)
 */
public class JSONArray extends JSONValue {
	private List<JSONValue> values;
	
	public JSONArray() {
		super(JSON_VALUE_TYPE.ARRAY);
		this.values = new ArrayList<JSONValue>();
	}
	
	/**
	 * Construct a JSONArray from a source JSON text.
	 * @param source (String) A string that begins with <code>[</code>&nbsp;<small>(left bracket)</small> and ends with <code>]</code>&nbsp;<small>(right bracket)</small>.
	 * @throws JSONException If there is a syntax error.
	 */
	public JSONArray(String source) throws JSONException 
		{ this(new JSONTokenizer(source)); }
	
	/**
	 * Construct a JSONArray from a JSONTokenizer.
	 * @param token (JSONTokenizer) A JSONTokenizer. 
	 * @throws JSONException If there is a syntax error.
	 */
	public JSONArray(JSONTokenizer token) throws JSONException {
		this();
		if (token.nextClean() != '[') 
			{ throw token.syntaxError("A JSONArray text must start with '['"); }
		if (token.nextClean() != ']') {
			token.back();
			for ( ; ; ) {
				if (token.nextClean() == ',') {
					token.back();
					this.values.add(JSONObject.NULL);
				} else {
					token.back();
					this.values.add(token.nextValue());
				}
				switch (token.nextClean()) {
				case ',':
					if (token.nextClean() == ']') 
						{ return; }
					token.back();
					break;
				case ']':
					return;
				default:
					throw token.syntaxError("Expected a ',' or ']'");
				}
			} // END "for ( ; ; )"
		} // END "if (token.nextClean() != ']')"
	}
	
	public JSONArray(List<? extends JSONValue> array) {
		this();
		for (int i = 0 ; i < array.size() ; ++i) 
			{ this.add(array.get(i)); }
	}
	
	public String toString() {
		String toReturn = new String( "" );
		
		toReturn += "[";
		for (int i = 0 ; i < this.values.size() ; i++) {
			if ( i > 0)	{ toReturn += ", "; }
			JSONValue currVal = this.values.get(i);
			toReturn += currVal.toString();
		} // END "while( iterator.hasNext())"
		toReturn += "]";
		
		return toReturn;
	}
	
	public void add(JSONValue value) 
		{ this.values.add(value); }
	
	public void add(int value) 
		{ this.values.add(new JSONNumber(value)); }
	
	public void add(long value) 
		{ this.values.add(new JSONNumber(value)); }

	public void add(double value) 
		{ this.values.add(new JSONNumber(value)); }

	public void add(boolean value) 
		{ this.values.add(new JSONBoolean(value)); }
	
	public void add(String value) 
		{ this.values.add(new JSONString(value)); }
	
	public List<JSONValue> getValues() {
		return Collections.unmodifiableList( this.values );
	}
}
