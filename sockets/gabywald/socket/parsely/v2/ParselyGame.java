package gabywald.socket.parsely.v2;

import java.io.BufferedReader;

import gabywald.global.json.JSONObject;
import gabywald.socket.model.ClientObserver;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

import java.io.InputStreamReader;
import java.io.IOException;

public class ParselyGame {
	
	private ClientParsely client = null;

	public ParselyGame(String host, Integer port) {
		
		// ***** Server Part !!
		DaemonParsely dp	= new DaemonParsely(port.intValue());
		dp.start();
		Logger.printlnLog(LoggerLevel.LL_DEBUG, dp.getOutput());
		
		// ***** Client Part !!
		ClientParsely cp	= new ClientParsely(host, port);
		ClientObserver co	= new ClientObserver();
		cp.addObserver(co);
		cp.start();
		
		this.client = cp;
		
//		cp.input("commande1");
//		this.client.input("commande2");

	}
	
	public void execution() {
//		ParselyGameStarter instance	= ParselyGameStarter.getInstance();
//		String toShow				= instance.getContentToShow();
//		Logger.printlnLog(LoggerLevel.LL_FORUSER, toShow);
		
		boolean exit = false;
		do {
//			JSONObject jsonObj = new JSONObject();
//			try {
//				jsonObj = new JSONObject(this.client.getOutput());
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			Logger.printlnLog(LoggerLevel.LL_FORUSER, jsonObj.toString() + "*****");
//			Logger.printLog(LoggerLevel.LL_FORUSER, ">"); // '\b'
			
			/** Read a line ! */
			String readedLine				= null;
			try {
				InputStreamReader reader	= new InputStreamReader(System.in);
				BufferedReader input		= new BufferedReader(reader);
				readedLine					= input.readLine();
			} catch (IOException e) { readedLine = new String(""); }
			
			if (readedLine.equals("")) { ; }
			else {
				
				JSONObject toSend = new JSONObject();
				toSend.put("query", readedLine);
				this.client.input(toSend.toString());
				// TODO watch it is correctly passed
				
				// exit = (readedLine.equals("quit") || readedLine.equals("shutdown"));
			}
		} while( ! exit);
		Logger.printlnLog(LoggerLevel.LL_FORUSER, "Good bye !");
	}
	
}
