package gabywald.global.json;

import gabywald.global.exceptions.GenericException;

/**
 * 
 * @author Gabriel Chandesris (2014)
 */
public class JSONException extends GenericException {
	
	public JSONException(String request) 
		{ super(request); }

	public JSONException(Throwable cause) 
		{ super(cause); }

}
