package gabywald.global.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Gabriel Chandesris (2014)
 */
public abstract class JSONifiable {
	
	private Map<String, JSONValue> mapKeyValues;
	
	protected JSONifiable() {
		this.mapKeyValues = new HashMap<String, JSONValue>();
	}
	
	protected void add(String key, JSONValue value) 
		{ this.mapKeyValues.put(key, value); }
	
	public JSONObject toJSON() {
		this.setKeyValues();
		JSONObject localJSONobject	= new JSONObject();
		Iterator<String> iterator	= this.mapKeyValues.keySet().iterator();
		while (iterator.hasNext()) {
			String key		= iterator.next();
			JSONValue val	= this.mapKeyValues.get(key);
			localJSONobject.put(key, val);
		} // END "while (iterator.hasNext())"
		return localJSONobject;
	}
	
	public String toRecord() {
		String toReturn = new String( "" );
		toReturn += this.toJSON().toString();
		return toReturn;
	}
	
	protected abstract void setKeyValues();
	
	public String JSONstringification() {
		this.setKeyValues();
		return this.toRecord();
	}
	
	public JSONObject JSONjsonification() {
		this.setKeyValues();
		return this.toJSON();
	}
	
	public static List<JSONValue> generateList(List<? extends JSONifiable> list) {
		List<JSONValue> jsonListToReturn = new ArrayList<JSONValue>();
		Iterator<? extends JSONifiable> iterator = list.iterator();
		while (iterator.hasNext()) 
			{ jsonListToReturn.add(iterator.next().toJSON()); }
		return jsonListToReturn;
	}
	
	public static JSONArray generateArray(List<? extends JSONifiable> list) {
		List<JSONValue> jsonListToReturn = new ArrayList<JSONValue>();
		Iterator<? extends JSONifiable> iterator = list.iterator();
		while (iterator.hasNext()) 
			{ jsonListToReturn.add(iterator.next().toJSON()); }
		return new JSONArray(jsonListToReturn);
	}
	
	public static JSONArray generateStrArray(List<String> listStrings) {
		List<JSONValue> jsonListToReturn = new ArrayList<JSONValue>();
		Iterator<String> iterator = listStrings.iterator();
		while (iterator.hasNext()) 
			{ jsonListToReturn.add( JSONValue.instanciate( iterator.next() ) ); }
		return new JSONArray(jsonListToReturn);
	}
}
