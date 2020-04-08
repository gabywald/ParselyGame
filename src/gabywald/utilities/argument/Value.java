package gabywald.utilities.argument;

/**
 * 
 * @author Gabriel Chandesris (2013)
 * @deprecated real use ?
 */
public class Value {
	
	public enum ArgumentType 
		{ String, Byte, Short, Int, Long, Float, Double, Boolean, Char }
	
	private ArgumentType argType;
	private String argName;
	private String stringValue;
	private byte byteValue;
	private short shortValue;
	private int intValue;
	private long longValue;
	private float floatValue;
	private double doubleValue;
	private boolean booleanValue;
	private char charValue;
	
	private void init(	String name, 
						ArgumentType type, 
						String strValue, 
						byte bytValue, 
						short shoValue, 
						int intValue, 
						long lonValue, 
						float floValue, 
						double douValue, 
						boolean booValue, 
						char chaValue) {
		this.argName		= name;
		this.argType		= type;
		this.stringValue	= strValue;
		this.byteValue		= bytValue;
		this.shortValue		= shoValue;
		this.intValue		= intValue;
		this.longValue		= lonValue;
		this.floatValue		= floValue;
		this.doubleValue	= douValue;
		this.booleanValue	= booValue;
		this.charValue		= chaValue;
	}
	
	public Value (String name, String value) 
		{ this.init(name, ArgumentType.String, value, 
					(byte)0, (short)0, (int)0, 0L, 0.0f, 0.0d, false, '\u0000'); }
	
	public Value (String name, byte value) 
		{ this.init(name, ArgumentType.Byte, null, 
					value, (short)0, (int)0, 0L, 0.0f, 0.0d, false, '\u0000'); }
	
	public Value (String name, short value) 
		{ this.init(name, ArgumentType.Short, null, 
					(byte)0, value, (int)0, 0L, 0.0f, 0.0d, false, '\u0000'); }
	
	public Value (String name, int value) 
		{ this.init(name, ArgumentType.Int, null, 
					(byte)0, (short)0, value, 0L, 0.0f, 0.0d, false, '\u0000'); }
	
	public Value (String name, long value) 
		{ this.init(name, ArgumentType.Long, null, 
					(byte)0, (short)0, (int)0, value, 0.0f, 0.0d, false, '\u0000'); }
	
	public Value (String name, float value) 
		{ this.init(name, ArgumentType.Float, null, 
					(byte)0, (short)0, (int)0, 0L, value, 0.0d, false, '\u0000'); }
	
	public Value (String name, double value) 
		{ this.init(name, ArgumentType.Double, null, 
					(byte)0, (short)0, (int)0, 0L, 0.0f, value, false, '\u0000'); }
	
	public Value (String name, boolean value) 
		{ this.init(name, ArgumentType.Boolean, null, 
					(byte)0, (short)0, (int)0, 0L, 0.0f, 0.0d, value, '\u0000'); }
	
	public Value (String name, char value) 
		{ this.init(name, ArgumentType.Char, null, 
					(byte)0, (short)0, (int)0, 0L, 0.0f, 0.0d, false, value); }

	public ArgumentType getType()	{ return this.argType; }
	public String getName()			{ return this.argName; }
	public String getStringValue()	{ return this.stringValue; }
	public byte getByteValue()		{ return this.byteValue; }
	public short getShortValue()	{ return this.shortValue; }
	public int getIntValue()		{ return this.intValue; }
	public long getLongValue()		{ return this.longValue; }
	public float getFloatValue()	{ return this.floatValue; }
	public double getDoubleValue()	{ return this.doubleValue; }
	public boolean isBooleanValue()	{ return this.booleanValue; }
	public char getCharValue()		{ return this.charValue; }
	
}
