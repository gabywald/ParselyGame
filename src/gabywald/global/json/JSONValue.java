package gabywald.global.json;

import java.util.List;

/**
 * 
 * @author Gabriel Chandesris (2014)
 */
public abstract class JSONValue {
	
	/**
	* It is sometimes more convenient and less ambiguous to have a
	* <code>NULL</code> object than to use Java's <code>null</code> value.
	* <code>JSONObject.NULL.equals(null)</code> returns <code>true</code>.
	* <code>JSONObject.NULL.toString()</code> returns <code>"null"</code>.
	*/
	public static final JSONValue NULL = new Null();
	
	public enum JSON_VALUE_TYPE 
		{ STRING, NUMBER, OBJECT, ARRAY, TRUE, FALSE, NULL }
	
	private JSON_VALUE_TYPE type;
	
	protected JSONValue(JSON_VALUE_TYPE type) 
		{ this.type = type; }
	
	public JSON_VALUE_TYPE getType()
		{ return this.type; }
	
	public boolean isString() 
	{ return ( (this.type == JSONValue.JSON_VALUE_TYPE.STRING)
				&& (this instanceof JSONString) ); }
	
	public boolean isNumber() 
	{ return ( (this.type == JSONValue.JSON_VALUE_TYPE.NUMBER)
				&& (this instanceof JSONNumber) ); }
	
	public boolean isObject() 
	{ return ( (this.type == JSONValue.JSON_VALUE_TYPE.OBJECT)
				&& (this instanceof JSONObject) ); }
	
	public boolean isArray() 
	{ return ( (this.type == JSONValue.JSON_VALUE_TYPE.ARRAY)
				&& (this instanceof JSONArray) ); }
	
	public boolean isTrue() 
	{ return ( (this.type == JSONValue.JSON_VALUE_TYPE.TRUE)
				&& (this instanceof JSONBoolean) ); }
	
	public boolean isFalse() 
	{ return ( (this.type == JSONValue.JSON_VALUE_TYPE.FALSE)
				&& (this instanceof JSONBoolean) ); }
	
	public boolean isBoolean() 
	{ return ( (this.isTrue()) || (this.isFalse()) ); }
	
//	public boolean isNull() 
//	{ return (JSONValue.NULL.equals(this)); }

	public double getDouble() throws JSONException {
		if (this.isNumber()) 
			{ return ((JSONNumber)this).getValueAsDouble(); } 
		else { throw new JSONException("getDouble : Not a JSONNumber !"); }
	}
	
	public long getLong() throws JSONException {
		if (this.isNumber()) 
			{ return ((JSONNumber)this).getValueAsLong(); } 
		else { throw new JSONException("getLong : Not a JSONNumber !"); }
	}
	
	public int getInteger() throws JSONException {
		if (this.isNumber()) 
			{ return ((JSONNumber)this).getValueAsInteger(); } 
		else { throw new JSONException("getInteger : Not a JSONNumber !"); }
	}
	
	public boolean getBoolean() throws JSONException {
		if (this.isBoolean()) 
			{ return ((JSONBoolean)this).getValue(); } 
		else { throw new JSONException("getBoolean : Not a JSONBoolean !"); }
	}
	
	public String getString() throws JSONException {
		if (this.isString()) 
			{ return ((JSONString)this).getValue(); } 
		else { throw new JSONException("getString : Not a JSONString !"); }
	}
	
	public JSONObject getObject() throws JSONException {
		if (this.isObject()) 
			{ return ((JSONObject)this); } 
		else { throw new JSONException("getObject : Not a JSONObject !"); }
	}
	
	public JSONArray getArray() throws JSONException {
		if (this.isArray()) 
			{ return ((JSONArray)this); } 
		else { throw new JSONException("getArray : Not a JSONArray !"); }
	}
	
	public abstract String toString();
	public String toJSON()	{ return this.toString(); }
	
	public static JSONValue instanciate(double value) 
		{ return new JSONNumber( value ); }
	
	public static JSONValue instanciate(long value) 
		{ return new JSONNumber( value ); }
	
	public static JSONValue instanciate(int value) 
		{ return new JSONNumber( value ); }
	
	public static JSONValue instanciate(String value) 
		{ return new JSONString( value ); }
	
	public static JSONValue instanciate(char value) 
		{ return new JSONString( "" + value + "" ); }
	
	public static JSONValue instanciate(boolean value) 
		{ return new JSONBoolean( value ); }
	
	public static JSONValue instanciate(List<JSONValue> value) 
		{ return new JSONArray( value ); }
	
	/**
	 * JSONObject.NULL is equivalent to the value that JavaScript calls null,
	 * while Java's null is equivalent to the value that JavaScript calls
	 * undefined.
	 */
	private static final class Null extends JSONValue {
		/** Default constructor. */
		public Null() 
			{ super(JSON_VALUE_TYPE.NULL); }

		/**
		 * There is only intended to be a single instance of the NULL object,
		 * so the clone method returns itself.
		 *
		 * @return NULL.
		 */
		@Override
		protected final JSONValue clone() 
			{ return this; }

//		/**
//		 * A Null object is equal to the null value and to itself.
//		 * @param object
//		 * An object to test for nullness.
//		 * @return true if the object parameter is the JSONObject.NULL object or
//		 * null.
//		 */
//		public boolean equals(JSONValue toCompare) 
//			{ return ( (toCompare == null) || (toCompare == this) ); }

		/**
		 * Get the "null" string value.
		 * @return (String) The string "null".
		 */
		public String toString()	{ return "null"; }
	}
}
