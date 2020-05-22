package gabywald.socket.parsely.v2;

import gabywald.global.json.JSONException;
import gabywald.global.json.JSONObject;
import gabywald.socket.model.GenericObserver;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

import java.util.Observable;

/**
 * 
 * @author Gabriel Chandesris (2013, 2015, 2020)
 */
public class ClientParselyObserver extends GenericObserver {
	
	// TODO input / output via socket is not synchronized !!
	
	@Override
	public void update(Observable observable, Object object) {
		ClientParsely client = (ClientParsely)observable;
		// Logger.printlnLog(LoggerLevel.LL_FORUSER, "CLIENTSIDE:" + client.getOutput());
		JSONObject jsonObj	= null;
		String content2show	= null;
		try {
			jsonObj			= new JSONObject(client.getOutput());
			content2show	= jsonObj.get("toclient").getString();
		} catch (JSONException e) {
			Logger.printlnLog(LoggerLevel.LL_WARNING, e.getMessage() + "\n" + client.getOutput());
			// e.printStackTrace();
		}
		
		if (content2show == null) {
			content2show = "No Message !";
		} else {
			Logger.printlnLog(LoggerLevel.LL_DEBUG, "*****" + content2show + "*****");
			content2show = content2show.replaceAll(ParselyGameStarter.STR_RETURN, "\n")
										.replaceAll(ParselyGameStarter.STR_TABULATION, "\t");
			Logger.printlnLog(LoggerLevel.LL_DEBUG, "*****" + content2show + "*****");
			Logger.printlnLog(LoggerLevel.LL_FORUSER, content2show);
		}
		
		Logger.printLog(LoggerLevel.LL_FORUSER, ">");
	}
}
