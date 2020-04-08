package gabywald.global.json;

public class JSONNumber extends JSONValue {
	private Number value;

	public JSONNumber(Double value) {
		super(JSON_VALUE_TYPE.NUMBER);
		this.value	= value;
	}
	
	public JSONNumber(Long value) {
		super(JSON_VALUE_TYPE.NUMBER);
		this.value	= value;
	}
	
	public JSONNumber(int value) {
		super(JSON_VALUE_TYPE.NUMBER);
		this.value	= value;
	}
	
	public boolean isDouble()	{ return (this.value instanceof Double); }
	public boolean isLong()		{ return (this.value instanceof Long); }
	public boolean isInteger()	{ return (this.value instanceof Integer); }
	
	public double getValueAsDouble() throws JSONException {
		if (this.value instanceof Double) 
			{ return this.value.doubleValue(); }
		else 
			{ throw new JSONException("Not a Double !"); }
	}
	
	public long getValueAsLong() throws JSONException {
		if (this.value instanceof Long) 
			{ return this.value.longValue(); }
		else 
			{ throw new JSONException("Not a Long !"); }
	}
	
	public int getValueAsInteger() throws JSONException {
		if (this.value instanceof Integer) 
			{ return this.value.intValue(); }
		else 
			{ throw new JSONException("Not a Integer !"); }
	}
	
	public String toString() { 
		if (this.value instanceof Long) 
			{ return "" + this.value.longValue(); }
		if (this.value instanceof Integer) 
			{ return "" + this.value.intValue(); }
//		if (this.value instanceof Double) 
			{ return "" + this.value.doubleValue(); }
	}
}
