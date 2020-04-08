package gabywald.global.json;

/**
 * 
 * @author Gabriel Chandesris (2014)
 */
public class JSONBoolean extends JSONValue {
	
	public static final JSONBoolean TRUE	= new JSONBoolean(true);
	public static final JSONBoolean FALSE	= new JSONBoolean(false);
	
	private boolean value;

	public JSONBoolean() {
		super(JSON_VALUE_TYPE.FALSE);
		this.value = false;
	}
	
	public JSONBoolean(boolean value) {
		super(value?JSON_VALUE_TYPE.TRUE:JSON_VALUE_TYPE.FALSE);
		this.value = value;
	}
	
	public boolean getValue() { return this.value; }
	
	public String toString() 
		{ return ((this.value)?"true":"false"); }
}
